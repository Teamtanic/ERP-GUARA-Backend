package com.guarajunior.rp.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.guarajunior.rp.exception.CompanyServiceException;
import com.guarajunior.rp.mapper.ProjectMapper;
import com.guarajunior.rp.model.CompanyRelationship;
import com.guarajunior.rp.model.Offering;
import com.guarajunior.rp.model.Project;
import com.guarajunior.rp.model.ProjectUserRelation;
import com.guarajunior.rp.model.User;
import com.guarajunior.rp.model.dto.project.ProjectDTO;
import com.guarajunior.rp.model.dto.project.ProjectResponseDTO;
import com.guarajunior.rp.model.dto.project.UserRoleDTO;
import com.guarajunior.rp.repository.CompanyRelationshipRepository;
import com.guarajunior.rp.repository.OfferingRepository;
import com.guarajunior.rp.repository.ProjectRepository;
import com.guarajunior.rp.repository.ProjectUserRelationRepository;
import com.guarajunior.rp.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private CompanyRelationshipRepository companyRelationshipRepository;
	@Autowired
	private OfferingRepository offeringRepository;
	@Autowired
	private ProjectUserRelationRepository projectUserRelationRepository;
	@Autowired
	private UserRepository userRepository;
	
	public Page<ProjectResponseDTO> getAllProjects(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
		Page<Project> projectPage = projectRepository.findAll(pageable);
		return projectMapper.pageToResponsePageDTO(projectPage);
	}
	
	@Transactional
	public ProjectResponseDTO createProject(ProjectDTO projectDTO) {
		try {
			List<UUID> companyRelationshipsIds = projectDTO.getCompanyRelationshipIds();
	        List<CompanyRelationship> companyRelationships = companyRelationshipRepository.findAllById(companyRelationshipsIds);
	        if (companyRelationships.size() != companyRelationshipsIds.size()) {
	            throw new RuntimeException("Uma ou mais CompanyRelationships não foram encontradas");
	        }
			
			Offering offering = offeringRepository.findById(projectDTO.getOfferingId())
					.orElseThrow(() -> new RuntimeException("Offering não encontrado"));
			
			Project projectToCreate = projectMapper.toEntity(projectDTO);
			projectToCreate.setOffering(offering);
			projectToCreate.setCompanyRelationships(companyRelationships);

			projectRepository.save(projectToCreate);
			
			List<UserRoleDTO> users = projectDTO.getUsers();
			List<ProjectUserRelation> usersInProject = new ArrayList<>();
			for (UserRoleDTO userRoleDTO : users) {
				User user = userRepository.findById(userRoleDTO.getUserId())
						.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
				
				ProjectUserRelation userInProject = new ProjectUserRelation();
				userInProject.setProject(projectToCreate);
				userInProject.setUser(user);
				userInProject.setRole(userRoleDTO.getRole());
				projectUserRelationRepository.save(userInProject);
				usersInProject.add(userInProject);
			}
			
			projectToCreate.setProjectUserRelations(usersInProject);
			
			return projectMapper.toResponseDTO(projectToCreate);
		} catch (Exception e) {
			throw new CompanyServiceException("Erro ao criar projeto: " + e.getMessage());
		}
	}
	
	public ProjectResponseDTO getProjectById(UUID projectId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
		
		return projectMapper.toResponseDTO(project);
	}
	
	public ProjectResponseDTO updateProject(UUID id, Map<String, Object> fields) {
		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
		
		fields.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(Project.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, project, value);
		});
		
		projectRepository.save(project);
		
		return projectMapper.toResponseDTO(project);
	}
	
	public void deactivateProject(UUID projectId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
		
		project.setActive(false);
		projectRepository.save(project);
	}
}

package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.project.UserRoleDTO;
import com.guarajunior.guararp.api.dto.project.request.ProjectCreateRequest;
import com.guarajunior.guararp.api.dto.project.response.ProjectResponse;
import com.guarajunior.guararp.domain.mapper.ProjectMapper;
import com.guarajunior.guararp.infra.model.*;
import com.guarajunior.guararp.infra.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public Page<ProjectResponse> getAllProjects(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projectPage = projectRepository.findAll(pageable);
        return projectMapper.pageToResponsePageDTO(projectPage);
    }

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest projectCreateRequest) {
        List<UUID> companyRelationshipsIds = projectCreateRequest.getCompanyRelationshipIds();
        List<CompanyRelationship> companyRelationships = companyRelationshipRepository.findAllById(companyRelationshipsIds);
        if (companyRelationships.size() != companyRelationshipsIds.size()) {
            throw new RuntimeException("Uma ou mais CompanyRelationships não foram encontradas");
        }

        Offering offering = offeringRepository.findById(projectCreateRequest.getOfferingId()).orElseThrow(() -> new RuntimeException("Offering não encontrado"));

        Project projectToCreate = projectMapper.toEntity(projectCreateRequest);
        projectToCreate.setOffering(offering);
        projectToCreate.setCompanyRelationships(companyRelationships);

        projectRepository.save(projectToCreate);

        List<UserRoleDTO> users = projectCreateRequest.getUsers();
        List<ProjectUserRelation> usersInProject = new ArrayList<>();
        for (UserRoleDTO userRoleDTO : users) {
            User user = userRepository.findById(userRoleDTO.getUserId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            ProjectUserRelation userInProject = new ProjectUserRelation();
            userInProject.setProject(projectToCreate);
            userInProject.setUser(user);
            userInProject.setRole(userRoleDTO.getRole());
            projectUserRelationRepository.save(userInProject);
            usersInProject.add(userInProject);
        }

        projectToCreate.setProjectUserRelations(usersInProject);

        return projectMapper.toResponseDTO(projectToCreate);
    }

    public ProjectResponse getProjectById(UUID projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        return projectMapper.toResponseDTO(project);
    }

    public ProjectResponse updateProject(UUID id, Map<String, Object> fields) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Project.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, project, value);
        });

        projectRepository.save(project);

        return projectMapper.toResponseDTO(project);
    }

    public void deactivateProject(UUID projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        project.setActive(false);
        projectRepository.save(project);
    }
}

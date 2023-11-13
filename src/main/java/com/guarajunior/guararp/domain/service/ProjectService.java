package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.project.UserRoleDTO;
import com.guarajunior.guararp.api.dto.project.request.ProjectCreateRequest;
import com.guarajunior.guararp.api.dto.project.response.ProjectResponse;
import com.guarajunior.guararp.domain.mapper.ProjectMapper;
import com.guarajunior.guararp.infra.model.*;
import com.guarajunior.guararp.infra.repository.*;
import jakarta.persistence.EntityNotFoundException;
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
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final CompanyRelationshipRepository companyRelationshipRepository;
    private final OfferingRepository offeringRepository;
    private final ProjectUserRelationRepository projectUserRelationRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, CompanyRelationshipRepository companyRelationshipRepository, OfferingRepository offeringRepository, ProjectUserRelationRepository projectUserRelationRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.companyRelationshipRepository = companyRelationshipRepository;
        this.offeringRepository = offeringRepository;
        this.projectUserRelationRepository = projectUserRelationRepository;
        this.userRepository = userRepository;
    }

    public Page<ProjectResponse> getAllProjects(Boolean active, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projectPage = projectRepository.findAllByActive(active, pageable);
        return projectMapper.pageToResponsePageDTO(projectPage);
    }

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest projectCreateRequest) {
        // Verifica a existência de CompanyRelationships
        List<CompanyRelationship> companyRelationships = getCompanyRelationships(projectCreateRequest.getCompanyRelationshipIds());

        // Obtém a Offering
        Offering offering = offeringRepository.findById(projectCreateRequest.getOfferingId()).orElseThrow(() -> new RuntimeException("Offering não encontrado"));

        // Mapeia os dados da requisição para a entidade Project
        Project projectToCreate = projectMapper.toEntity(projectCreateRequest);
        projectToCreate.setActive(true);
        projectToCreate.setOffering(offering);
        projectToCreate.setCompanyRelationships(companyRelationships);

        // Salva o projeto
        projectRepository.save(projectToCreate);

        // Cria e associa os usuários ao projeto
        List<ProjectUserRelation> usersInProject = createProjectUserRelations(projectToCreate, projectCreateRequest.getUsers());

        projectToCreate.setProjectUserRelations(usersInProject);

        return projectMapper.toResponseDTO(projectToCreate);
    }

    private List<CompanyRelationship> getCompanyRelationships(List<UUID> companyRelationshipIds) {
        List<CompanyRelationship> companyRelationships = companyRelationshipRepository.findAllById(companyRelationshipIds);

        if (companyRelationships.size() != companyRelationshipIds.size()) {
            throw new RuntimeException("Uma ou mais CompanyRelationships não foram encontradas");
        }

        return companyRelationships;
    }

    private List<ProjectUserRelation> createProjectUserRelations(Project project, List<UserRoleDTO> users) {
        List<ProjectUserRelation> usersInProject = new ArrayList<>();
        for (UserRoleDTO userRoleDTO : users) {
            User user = userRepository.findById(userRoleDTO.getUserId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            ProjectUserRelation userInProject = new ProjectUserRelation();
            userInProject.setProject(project);
            userInProject.setUser(user);
            userInProject.setRole(userRoleDTO.getRole());
            projectUserRelationRepository.save(userInProject);
            usersInProject.add(userInProject);
        }
        return usersInProject;
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

        return projectMapper.toResponseDTO(projectRepository.save(project));
    }

    public void deactivateProject(UUID projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        project.setActive(false);

        projectRepository.save(project);
    }
}

package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.project.UserRoleDTO;
import com.guarajunior.guararp.api.dto.project.request.ProjectCreateRequest;
import com.guarajunior.guararp.api.dto.project.request.ProjectUpdateRequest;
import com.guarajunior.guararp.api.dto.project.response.ProjectResponse;
import com.guarajunior.guararp.domain.mapper.ProjectMapper;
import com.guarajunior.guararp.infra.model.*;
import com.guarajunior.guararp.infra.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ModelMapper mapper;
    private final CompanyRelationshipRepository companyRelationshipRepository;
    private final OfferingRepository offeringRepository;
    private final ProjectUserRelationRepository projectUserRelationRepository;
    private final UserRepository userRepository;

    public Page<ProjectResponse> getAllProjects(Boolean active, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projectPage = projectRepository.findAllByActive(active, pageable);
        return projectMapper.pageToResponsePageDTO(projectPage);
    }

    public Page<ProjectResponse> getAllCompanyProjects(UUID idCompany, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projectPage = projectRepository.findAllByCompanyId(idCompany, pageable);
        return projectMapper.pageToResponsePageDTO(projectPage);
    }

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest projectCreateRequest) {
        Project projectToCreate = projectMapper.toEntity(projectCreateRequest);

        Set<Offering> offerings = new HashSet<>();
        projectCreateRequest.getOfferingIds().forEach(uuid -> offerings.add(offeringRepository.findById(uuid).orElseThrow(() -> new RuntimeException(String.format("Offering %s não encontrado", uuid)))));
        projectToCreate.setOfferings(offerings);

        List<CompanyRelationship> companyRelationships = getCompanyRelationships(projectCreateRequest.getCompanyRelationshipIds(), projectToCreate);
        projectToCreate.setCompanyRelationships(companyRelationships);

        projectRepository.save(projectToCreate);

        List<ProjectUserRelation> usersInProject = createProjectUserRelations(projectToCreate, projectCreateRequest.getUsers());
        projectToCreate.setProjectUserRelations(usersInProject);

        return projectMapper.toResponseDTO(projectToCreate);
    }

    private List<CompanyRelationship> getCompanyRelationships(List<UUID> companyRelationshipIds, Project project) {
        List<CompanyRelationship> companyRelationships = companyRelationshipRepository.findAllById(companyRelationshipIds);

        if (companyRelationships.size() != companyRelationshipIds.size()) {
            throw new RuntimeException("Uma ou mais CompanyRelationships não foram encontradas");
        }

        companyRelationships.forEach(companyRelationship -> companyRelationship.getProjects().add(project));

        return companyRelationships;
    }

    private List<ProjectUserRelation> createProjectUserRelations(Project project, List<UserRoleDTO> users) {
        List<ProjectUserRelation> usersInProject = new ArrayList<>();
        for (UserRoleDTO userRoleDTO : users) {
            User user = userRepository.findById(userRoleDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            ProjectUserRelation userInProject = new ProjectUserRelation();
            ProjectUserRelation.ProjectUserKey userKey = new ProjectUserRelation.ProjectUserKey(project.getId(), user.getId());
            userInProject.setId(userKey);
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

    public ProjectResponse updateProject(UUID id, ProjectUpdateRequest projectUpdateRequest) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        mapper.map(projectUpdateRequest, project);

        return projectMapper.toResponseDTO(projectRepository.save(project));
    }

    public ProjectResponse addProjectOfferings(UUID id, List<UUID> offeringIds) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        Set<Offering> offerings = new HashSet<>();
        offeringIds.forEach(uuid -> offerings.add(offeringRepository.findById(uuid).orElseThrow(() -> new RuntimeException(String.format("Offering %s não encontrado", uuid)))));

        project.getOfferings().addAll(offerings);

        return projectMapper.toResponseDTO(projectRepository.save(project));
    }

    public ProjectResponse removeProjectOfferings(UUID id, List<UUID> offeringIds) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        offeringIds.forEach(uuid -> project.getOfferings().removeIf(offering -> offering.getId().equals(uuid)));

        return projectMapper.toResponseDTO(projectRepository.save(project));
    }

    public void deactivateProject(UUID projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        project.setActive(false);

        projectRepository.save(project);
    }
}

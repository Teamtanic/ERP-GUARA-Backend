package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.Project;
import com.guarajunior.guararp.api.dto.project.request.ProjectCreateRequest;
import com.guarajunior.guararp.api.dto.project.response.ProjectResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {
	private final ModelMapper modelMapper;
	
	public ProjectMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Project toEntity(ProjectCreateRequest projectCreateRequest) {
		return modelMapper.map(projectCreateRequest, Project.class);
	}
	
	public ProjectCreateRequest toDTO(Project project) {
		return modelMapper.map(project, ProjectCreateRequest.class);
	}
	
	public ProjectResponse toResponseDTO(Project project) {
		return modelMapper.map(project, ProjectResponse.class);
	}
	
	public Page<ProjectResponse> pageToResponsePageDTO(Page<Project> entityPage){
		List<ProjectResponse> entityList = entityPage
												.getContent()
												.stream()
												.map(this::toResponseDTO)
												.collect(Collectors.toList());
		
		return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
	}
}
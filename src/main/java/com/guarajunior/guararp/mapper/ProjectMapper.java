package com.guarajunior.guararp.mapper;

import com.guarajunior.guararp.model.Project;
import com.guarajunior.guararp.model.dto.project.ProjectDTO;
import com.guarajunior.guararp.model.dto.project.ProjectResponseDTO;
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
	
	public Project toEntity(ProjectDTO projectDTO) {
		return modelMapper.map(projectDTO, Project.class);
	}
	
	public ProjectDTO toDTO(Project project) {
		return modelMapper.map(project, ProjectDTO.class);
	}
	
	public ProjectResponseDTO toResponseDTO(Project project) {
		return modelMapper.map(project, ProjectResponseDTO.class);
	}
	
	public Page<ProjectResponseDTO> pageToResponsePageDTO(Page<Project> entityPage){
		List<ProjectResponseDTO> entityList = entityPage
												.getContent()
												.stream()
												.map(this::toResponseDTO)
												.collect(Collectors.toList());
		
		return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
	}
}

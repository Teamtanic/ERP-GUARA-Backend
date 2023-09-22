package com.guarajunior.rp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.Project;
import com.guarajunior.rp.model.dto.project.ProjectDTO;
import com.guarajunior.rp.model.dto.project.ProjectResponseDTO;

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

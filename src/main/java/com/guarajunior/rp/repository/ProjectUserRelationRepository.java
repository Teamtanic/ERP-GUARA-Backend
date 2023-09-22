package com.guarajunior.rp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guarajunior.rp.model.ProjectUserRelation;

public interface ProjectUserRelationRepository extends JpaRepository<ProjectUserRelation, ProjectUserRelation.ProjectUserKey> {

}

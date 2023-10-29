package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.ProjectUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUserRelationRepository extends JpaRepository<ProjectUserRelation, ProjectUserRelation.ProjectUserKey> {

}

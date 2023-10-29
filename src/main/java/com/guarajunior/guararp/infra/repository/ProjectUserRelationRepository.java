package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.ProjectUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUserRelationRepository extends JpaRepository<ProjectUserRelation, ProjectUserRelation.ProjectUserKey> {

}

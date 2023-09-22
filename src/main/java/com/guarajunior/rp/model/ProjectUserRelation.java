package com.guarajunior.rp.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "project_has_user")
public class ProjectUserRelation {

    @EmbeddedId
    private ProjectUserKey id;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "id_project")
    private Project project;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "role")
    private String role;

    @Data
    @Embeddable
    public static class ProjectUserKey implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "id_project")
        private UUID projectId;

        @Column(name = "id_user")
        private UUID userId;
    }
}

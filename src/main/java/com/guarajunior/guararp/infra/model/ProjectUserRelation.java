package com.guarajunior.guararp.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "project_has_user")
public class ProjectUserRelation {

    @EmbeddedId
    private ProjectUserKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("projectId")
    @JoinColumn(name = "id_project")
    @JsonIgnore()
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "id_user")
    @ToString.Exclude
    private User user;

    @Column(name = "role")
    private String role;

    @Data
    @Embeddable
    @NoArgsConstructor
    public static class ProjectUserKey implements Serializable {
		@Column(name = "id_project")
        private UUID projectId;

        @Column(name = "id_user")
        private UUID userId;

        public ProjectUserKey(UUID projectId, UUID userId) {
            this.projectId = projectId;
            this.userId = userId;
        }
    }
}

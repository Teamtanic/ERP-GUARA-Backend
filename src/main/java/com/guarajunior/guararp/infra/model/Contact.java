package com.guarajunior.guararp.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Contact {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String email;
    private String telephone;
    private String cell_phone;

    @ManyToOne
    @JoinColumn(name = "id_company", referencedColumnName = "id")
    @JsonIgnore
    private Company company;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @JsonIgnore
    private User user;
}

package com.guarajunior.guararp.infra.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
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
	private Company company;
	@OneToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	private User user;
}

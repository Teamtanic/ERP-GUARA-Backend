package com.guarajunior.rp.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

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

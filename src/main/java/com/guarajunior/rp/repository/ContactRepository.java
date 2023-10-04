package com.guarajunior.rp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guarajunior.rp.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
	Contact findByEmail(String email);
}

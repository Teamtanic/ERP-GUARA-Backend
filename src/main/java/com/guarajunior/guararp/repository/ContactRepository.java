package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
	Contact findByEmail(String email);
}

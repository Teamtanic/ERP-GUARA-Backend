package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.Contact;
import com.guarajunior.guararp.infra.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	@Query("SELECT u FROM User u WHERE u.active = true")
	Page<User> findAll(Pageable pageable);
	Optional<User> findByLogin(String login);
	Optional<User> findByContact(Contact contact);
}

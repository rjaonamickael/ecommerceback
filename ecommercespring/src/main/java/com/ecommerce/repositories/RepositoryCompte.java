package com.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.entities.Compte;

public interface RepositoryCompte extends JpaRepository<Compte, Long> {
	@Query("select u from Compte u where u.email like :x")   // Attendiont 'User' doit être identique au nom de l'entité et non la table
	public Optional<Compte> findCompteByEmail(@Param("x")String email);
}

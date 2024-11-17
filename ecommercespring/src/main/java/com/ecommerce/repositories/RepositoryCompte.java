package com.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.Compte;

public interface RepositoryCompte extends JpaRepository<Compte, Long> {

}

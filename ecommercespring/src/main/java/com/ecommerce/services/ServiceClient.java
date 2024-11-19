package com.ecommerce.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.DTOClient;
import com.ecommerce.entities.Categorie;
import com.ecommerce.entities.Client;
import com.ecommerce.repositories.RepositoryAdresse;
import com.ecommerce.repositories.RepositoryClient;
import com.ecommerce.repositories.RepositoryCompte;

@Service
public class ServiceClient {
	
	@Autowired
	private RepositoryClient repositoryClient;
	
	@Autowired
	private RepositoryAdresse repositoryAdresse;
	
	@Autowired
	private RepositoryCompte repositoryCompte;
	
	
	public ResponseEntity<DTOClient> getClient(Long id) {
		Client client = repositoryClient.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Client non trouvé"));

		return ResponseEntity.status(HttpStatus.OK).body(DTOClient.toDTOClient(client));
	}


	public ResponseEntity<DTOClient> updateClient(Long id, Client client) {
		client.setId(id);
		repositoryClient.save(client);

		return ResponseEntity.status(HttpStatus.OK).body(DTOClient.toDTOClient(client));
	}
	
}

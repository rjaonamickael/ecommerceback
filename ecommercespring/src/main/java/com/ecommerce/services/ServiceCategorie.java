package com.ecommerce.services;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.Categorie;
import com.ecommerce.repositories.RepositoryCategorie;
import com.ecommerce.utils.FonctionsUtiles;

@Service
public class ServiceCategorie {
	@Autowired
	private RepositoryCategorie repositoryCategorie;

	@Autowired
	private FonctionsUtiles functions;

	private final String TYPE_MESSAGE = "message";
	
	
	public ResponseEntity<List<Categorie>> getAllCategories() {
		List<Categorie> categories = repositoryCategorie.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(categories);
	}

	public ResponseEntity<Categorie> getCategorie(Long id) {
		Categorie categorie = repositoryCategorie.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Categorie non trouvé"));

		return ResponseEntity.status(HttpStatus.OK).body(categorie);
	}

	public ResponseEntity<Categorie> addCategorie(Categorie categorie) {
		repositoryCategorie.save(categorie);

		return ResponseEntity.status(HttpStatus.CREATED).body(categorie);
	}

	public ResponseEntity<Map<String, String>> deleteCategorie(Long id) {
		repositoryCategorie.deleteById(id);
		;

		return ResponseEntity.status(HttpStatus.OK).body(functions.reponse(TYPE_MESSAGE, "Success"));
	}

	public ResponseEntity<Categorie> updateCategorie(Long id,Categorie categorie) {
		categorie.setId(id);
		repositoryCategorie.save(categorie);

		return ResponseEntity.status(HttpStatus.OK).body(categorie);
	}

}

package com.ecommerce.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.Categorie;
import com.ecommerce.entities.Compte;
import com.ecommerce.entities.Produit;
import com.ecommerce.exceptions.EmailNonDisponibleException;
import com.ecommerce.repositories.RepositoryCategorie;
import com.ecommerce.repositories.RepositoryCompte;
import com.ecommerce.repositories.RepositoryProduit;
import com.ecommerce.requests.RequestAddProduit;
import com.ecommerce.utils.FonctionsUtiles;
import com.ecommerce.utils.TypeCompte;

@Service
public class ServiceAdmin {
	@Autowired
	private RepositoryCategorie repositoryCategorie;

	@Autowired
	private RepositoryProduit repositoryProduit;
	
	@Autowired
	private RepositoryCompte repositoryCompte;
	
	@Autowired
	private ServiceMailing serviceMailing;

	@Autowired
	private FonctionsUtiles functions;

	private final String TYPE_MESSAGE = "message";
	
	
	// GESTION CATEGORIE
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
	
	
	// GESTION PRODUIT
	public ResponseEntity<List<Produit>> getAllProduits() {
		List<Produit> produit = repositoryProduit.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(produit);
	}

	public ResponseEntity<Produit> getProduit(Long id) {
		Produit produit = repositoryProduit.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Produit non trouvé"));

		return ResponseEntity.status(HttpStatus.OK).body(produit);
	}

	public ResponseEntity<Produit> addProduit(RequestAddProduit request) {
		Produit produit = request.getProduit();
		
		Categorie categorie = repositoryCategorie.findById(request.getId_categorie())
				.orElseThrow(() -> new NoSuchElementException("Categorie non trouvé"));
		
		produit.setCategorie(categorie);
		
		repositoryProduit.save(produit);

		return ResponseEntity.status(HttpStatus.CREATED).body(produit);
	}

	public ResponseEntity<Map<String, String>> deleteProduit(Long id) {
		repositoryProduit.deleteById(id);
		;

		return ResponseEntity.status(HttpStatus.OK).body(functions.reponse(TYPE_MESSAGE, "Success"));
	}

	public ResponseEntity<Produit> updateProduit(Long id,RequestAddProduit request) {
		Produit produit = request.getProduit();
		
		Categorie categorie = repositoryCategorie.findById(request.getId_categorie())
				.orElseThrow(() -> new NoSuchElementException("Categorie non trouvé"));
		
		produit.setCategorie(categorie);
		produit.setId(id);
		
		repositoryProduit.save(produit);

		return ResponseEntity.status(HttpStatus.OK).body(produit);
	}

	
	
}

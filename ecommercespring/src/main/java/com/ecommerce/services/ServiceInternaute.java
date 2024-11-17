package com.ecommerce.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.Categorie;
import com.ecommerce.entities.Client;
import com.ecommerce.entities.Compte;
import com.ecommerce.entities.Produit;
import com.ecommerce.repositories.RepositoryCategorie;
import com.ecommerce.repositories.RepositoryClient;
import com.ecommerce.repositories.RepositoryCompte;
import com.ecommerce.repositories.RepositoryProduit;
import com.ecommerce.requests.RequestRegister;
import com.ecommerce.utils.FonctionsUtiles;
import com.ecommerce.exceptions.EmailNonDisponibleException;

@Service
public class ServiceInternaute {
	
	@Autowired
	private RepositoryCategorie repositoryCategorie;

	@Autowired
	private RepositoryProduit repositoryProduit;
	
	@Autowired
	private RepositoryCompte repositoryCompte;

	@Autowired
	private RepositoryClient repositoryClient;
	
	@Autowired
	private FonctionsUtiles functions;

	private final String TYPE_MESSAGE = "message";
	
	@Autowired
	private ServiceMailing serviceMailing;
	

	
	public ResponseEntity< Map<Long, List<Produit> > > getAllProduitsByName(String nomProduit) {
		List<Categorie> categories = repositoryCategorie.findAll();
		List<Produit> produits = repositoryProduit.findByNomContaining(nomProduit);

		// Création d'une LinkedHashMap pour classer les produits produit par catégorie
		Map<Long, List<Produit>> produitsParCategorie = new LinkedHashMap<>();

		// Organisation des produits par catégorie
		for (Categorie categorie : categories) {
			// Filtrer les produits appartenant à la catégorie en cours
			List<Produit> produitsDansCategorie = produits.stream()
					.filter(produit -> produit.getCategorie().getId() == categorie.getId()).collect(Collectors.toList());

			// Ajouter la catégorie et ses produits triés dans la Map
			produitsParCategorie.put(categorie.getId(), produitsDansCategorie);
		}

		return ResponseEntity.status(HttpStatus.OK).body(produitsParCategorie);
	}

	
	public ResponseEntity< Client > register(RequestRegister request){
		Compte compte = request.getCompte();
		
		// Vérification si l'émail a déjà été utilisé par un autre utilisateur
		if(isEmailused(compte.getEmail())) {
			throw new EmailNonDisponibleException("Email non disponible");
		}
		// Enregistrement du compte
		repositoryCompte.save(compte); 
		
		//Association du compte au client
		Client client = request.getClient();
		client.setCompte(compte);
		
		// Enregistrement du client
		repositoryClient.save(client);
		
		// Envoie de mail de confirmation
		serviceMailing.confirmationInscription(compte.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(client);
	}

	public ResponseEntity<Map<String, String>> connect(String email,String password){
		
		return ResponseEntity.status(HttpStatus.OK).body(functions.reponse(TYPE_MESSAGE, "succes"));
	}
	
	private boolean isEmailused(String email) {
		if(repositoryCompte.findCompteByEmail(email)!=null) {
			return true;
		}
		else {
			return false;
		}
	}

}
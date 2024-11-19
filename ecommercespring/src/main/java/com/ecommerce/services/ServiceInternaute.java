package com.ecommerce.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.DTOPanier;
import com.ecommerce.entities.Categorie;
import com.ecommerce.entities.Client;
import com.ecommerce.entities.Compte;
import com.ecommerce.entities.Panier;
import com.ecommerce.entities.Produit;
import com.ecommerce.repositories.RepositoryCategorie;
import com.ecommerce.repositories.RepositoryClient;
import com.ecommerce.repositories.RepositoryCompte;
import com.ecommerce.repositories.RepositoryPanier;
import com.ecommerce.repositories.RepositoryProduit;
import com.ecommerce.repositories.RepositoryProduitPanier;
import com.ecommerce.requests_responses.RequestConnect;
import com.ecommerce.requests_responses.RequestRegister;
import com.ecommerce.utils.FonctionsUtiles;
import com.ecommerce.utils.TypeCompte;
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
	private RepositoryPanier repositoryPanier;
	
	@Autowired
	private RepositoryProduitPanier repositoryProduitPanier;
	
	@Autowired
	private FonctionsUtiles functions;
	
	@Autowired
	private ServiceMailing serviceMailing;
	

	public ResponseEntity< Map<Long, List<Produit>> > getAllProduitsByName(String nomProduit) {
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
	
	@Transactional
	public ResponseEntity< Client > registerClient(RequestRegister request){
		Compte compte = request.getCompte();
		Client client = request.getClient();
		
		// Vérification si l'émail a déjà été utilisé par un autre utilisateur
		if(isEmailused(compte.getEmail())) {
			throw new EmailNonDisponibleException("Email non disponible");
		}
		// Enregistrement du compte
		repositoryCompte.save(compte); 
		
		//Association du compte au client
		client.setCompte(compte);
		
		// Enregistrement du client
		repositoryClient.save(client);
		
		// Création d'un nouveau panier prêt à recevoir des produits du client
		Panier panier = new Panier();
		panier.setClient(client);
		repositoryPanier.save(panier);	
		
	    // Envoi d'un e-mail de confirmation de manière asynchrone
	    serviceMailing.confirmationInscription(compte.getEmail());
	    
		return ResponseEntity.status(HttpStatus.CREATED).body(client);
	}
	
	@Transactional
	public ResponseEntity<Compte> registerAdmin(Compte compte) {
		// TODO Auto-generated method stub
		// Vérification si l'émail a déjà été utilisé par un autre utilisateur
		if(isEmailused(compte.getEmail())) {
			throw new EmailNonDisponibleException("Email non disponible");
		}
		compte.setType(TypeCompte.ADMINISTRATEUR);
		// Enregistrement du compte
		repositoryCompte.save(compte); 
		
		// Envoie de mail de confirmation de manière asynchrone
		serviceMailing.confirmationInscription(compte.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(compte);
	}
	
	@SuppressWarnings("unused")
	@Transactional
	public ResponseEntity<?> connect(RequestConnect request){
		String emailRequest = request.getCompte().getEmail();
		String passwordRequest = request.getCompte().getPassword();
		
		
		Compte compte = repositoryCompte.findCompteByEmail(emailRequest);
		Client client = compte.getClient();
		Panier panier = request.getPanier();
		
		// Vérification de l'existence du mail
		if (compte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            						.body(functions.response_message("Compte inexistant"));
        }
		
		// Vérification du mot de passe
		if(!isPasswordCorrect(compte,passwordRequest)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
									.body(functions.response_message("Mot de passe incorrect"));
		}		
		
		// Mise à jour du panier du client
		gestionPanier(client,panier);
		panier = client.getPaniers().getLast();
		
		
		
		if(compte.getType() == TypeCompte.ADMINISTRATEUR) {
			return ResponseEntity.status(HttpStatus.OK).body(functions.response_message("succes"));
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(DTOPanier.toDTOPanier(panier));
		}
	}
	
	
	
	
	public boolean isEmailused(String email) {
		if(repositoryCompte.findCompteByEmail(email)!=null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isPasswordCorrect(Compte compte, String password) {
		String correctPassword = compte.getPassword();
		
		if(correctPassword.equals(password)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void gestionPanier(Client client, Panier panier) {
		Panier panierClient = client.getPaniers().getLast();
		
		// Ajout des Produits du panier web dans le panier du client
		if(panier != null) {
			panier.getProduitPanier().forEach(p -> {
				p.setPanier(panierClient);
				repositoryProduitPanier.save(p);
				//panierClient.getProduitPanier().add(p);
				});
		}
	
		
	}
}

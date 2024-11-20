package com.ecommerce.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.DTOClient;
import com.ecommerce.dto.DTOCommande;
import com.ecommerce.entities.Categorie;
import com.ecommerce.entities.Client;
import com.ecommerce.entities.Commande;
import com.ecommerce.entities.Panier;
import com.ecommerce.repositories.RepositoryAdresse;
import com.ecommerce.repositories.RepositoryClient;
import com.ecommerce.repositories.RepositoryCommande;
import com.ecommerce.repositories.RepositoryCompte;
import com.ecommerce.repositories.RepositoryPanier;
import com.ecommerce.repositories.RepositoryProduitPanier;
import com.ecommerce.requests.RequestCommande;

@Service
public class ServiceClient {
	
	@Autowired
	private RepositoryClient repositoryClient;
	
	@Autowired
	private RepositoryPanier repositoryPanier;
	
	@Autowired
	private RepositoryCommande repositoryCommande;
	
	@Autowired
	private RepositoryProduitPanier repositoryProduitPanier;
	
	
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
	
	public ResponseEntity<DTOCommande> buy(RequestCommande request) {
		Panier panierWeb = request.getPanier();
		Commande commande = request.getCommande();
		
		Panier panierBd = repositoryPanier.findById(panierWeb.getId())
											.orElseThrow(() -> new NoSuchElementException("Panier non trouvé"));
		
		// Mise à jour du panier dans la base
		miseAJourPanier(panierBd,panierWeb);
		
		// Liaison de la commande et le panier du client
		commande.setPanier(panierWeb);
		repositoryCommande.save(commande);
		
		// Création d'un nouveau panier prêt à recevoir des produits magasiner par le client
		Panier nouveauPanier = new Panier();
		nouveauPanier.setClient(panierBd.getClient());
		repositoryPanier.save(nouveauPanier);

		return ResponseEntity.status(HttpStatus.OK).body(DTOCommande.toDTOCommande(commande));
	}
	
	public void miseAJourPanier(Panier panierBd, Panier panierWeb) {
		// Suppression des produits du panier du client
		panierBd.getProduitPanier().forEach(p -> repositoryProduitPanier.deleteById(p.getId()));
		
		// Synchronisation du vidage du panier dans la base
		panierBd.getProduitPanier().clear();
		repositoryPanier.save(panierBd);
		
		
		// Remplacement des produits du panier du client par ceux du panier web
		panierWeb.getProduitPanier().forEach(p -> {
												p.setPanier(panierWeb);
												repositoryProduitPanier.save(p);});
		
	}
	
}

package com.ecommerce.requests;

import com.ecommerce.entities.Commande;
import com.ecommerce.entities.Panier;

public class RequestCommande {
	private Commande commande;
	private Panier panier;
	
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	public Panier getPanier() {
		return panier;
	}
	public void setPanier(Panier panier) {
		this.panier = panier;
	}
}

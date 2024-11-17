package com.ecommerce.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProduitPanier implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private int quantite;
	
	@ManyToOne
    @JoinColumn(name = "id_produit", referencedColumnName = "id")
	@JsonBackReference("Produit-ProduitPanier")
	private Produit	produit;
	
	@ManyToOne
    @JoinColumn(name = "id_panier", referencedColumnName = "id")
	@JsonBackReference("Panier-ProduitPanier")     	/// Quand il y a 2 ou plusieurs JsonBackReference dans une 
	private Panier	panier;							/// seule classe, il faut étiquetté la référence

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public ProduitPanier(Long id, int quantite, Produit produit, Panier panier) {
		super();
		this.id = id;
		this.quantite = quantite;
		this.produit = produit;
		this.panier = panier;
	}

	public ProduitPanier() {
		super();
	}
	
	
}

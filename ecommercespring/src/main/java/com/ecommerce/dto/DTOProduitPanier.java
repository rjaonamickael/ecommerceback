package com.ecommerce.dto;

import com.ecommerce.entities.ProduitPanier;

public class DTOProduitPanier {
	
	private Long id;
	private int quantite;
	private Long id_produit;
	
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
	public Long getId_produit() {
		return id_produit;
	}
	public void setId_produit(Long id_produit) {
		this.id_produit = id_produit;
	}
	
	
	public static DTOProduitPanier toDTOProduitPanier(ProduitPanier produitPanier) {
		DTOProduitPanier dto = new DTOProduitPanier();
		
		dto.setId(produitPanier.getId());
		dto.setQuantite(produitPanier.getQuantite());
		dto.setId_produit(produitPanier.getProduit().getId());
		
		
		return dto;
	}
	
	
	
}

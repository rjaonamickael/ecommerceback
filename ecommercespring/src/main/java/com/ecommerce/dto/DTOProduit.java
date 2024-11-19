package com.ecommerce.dto;

import com.ecommerce.entities.Produit;

/*
 *  Les DTO (Data Transfer Objects) AVANTAGES : Flexibilité et maintenabilité
 * 	elle sépare complètement la structure de vos entités de la représentation exposée aux 
 * 	consommateurs de votre API. 
 * 	Cela permet également de mieux gérer les performances en limitant la quantité de données sérialisées.
 * 
 */


public class DTOProduit {
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private DTOCategorie categorie;
    
    
    // Getter et setter
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrix() {
		return prix;
	}
	public void setPrix(Double prix) {
		this.prix = prix;
	}
	public DTOCategorie getCategorie() {
		return categorie;
	}
	public void setCategorie(DTOCategorie categorie) {
		this.categorie = categorie;
	}
    
	public static  DTOProduit toDTOProduit(Produit produit) {
	    DTOProduit dto = new DTOProduit();
	    dto.setId(produit.getId());
	    dto.setNom(produit.getNom());
	    dto.setDescription(produit.getDescription());
	    dto.setPrix(produit.getPrix());
	    
	    DTOCategorie categorieDTO = new DTOCategorie();
        dto.setCategorie(categorieDTO.toDTOCategorie(produit.getCategorie()));
	    
	    return dto;
	}
    
    
    
}

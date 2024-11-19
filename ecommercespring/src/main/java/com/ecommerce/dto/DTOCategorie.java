package com.ecommerce.dto;

import com.ecommerce.entities.Categorie;

/*
 *  Les DTO (Data Transfer Objects) AVANTAGES : Flexibilité et maintenabilité
 * 	elle sépare complètement la structure de vos entités de la représentation exposée aux 
 * 	consommateurs de votre API. 
 * 	Cela permet également de mieux gérer les performances en limitant la quantité de données sérialisées.
 * 
 */


public class DTOCategorie {
    private Long id;
    private String nom;
    private String description;
    
    
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
    
    
    
    public static  DTOCategorie toDTOCategorie(Categorie categorie) {
        DTOCategorie dto = new DTOCategorie();
        dto.setId(categorie.getId());
        dto.setNom(categorie.getNom());
        dto.setDescription(categorie.getDescription());

        return dto;
    }

}


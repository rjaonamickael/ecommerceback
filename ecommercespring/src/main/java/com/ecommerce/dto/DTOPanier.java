package com.ecommerce.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.entities.Panier;

/*
 *  Les DTO (Data Transfer Objects) AVANTAGES : Flexibilité et maintenabilité
 * 	elle sépare complètement la structure de vos entités de la représentation exposée aux 
 * 	consommateurs de votre API. 
 * 	Cela permet également de mieux gérer les performances en limitant la quantité de données sérialisées.
 * 
 */


public class DTOPanier {
	
	private Long id;
	private List<DTOProduitPanier> produitPaniers;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<DTOProduitPanier> getProduitPaniers() {
		return produitPaniers;
	}
	public void setProduitPaniers(List<DTOProduitPanier> produitPaniers) {
		this.produitPaniers = produitPaniers;
	}
	
	public static DTOPanier toDTOPanier(Panier panier) {
		DTOPanier dto = new DTOPanier();
		
		dto.setId(panier.getId());
		dto.setProduitPaniers(panier.getProduitPanier().stream()
                .map(DTOProduitPanier::toDTOProduitPanier)
                .collect(Collectors.toList()));
		
		return dto;
	}
	
}

package com.ecommerce.dto;

import com.ecommerce.entities.ProduitPanier;

/*
 *  Les DTO (Data Transfer Objects) AVANTAGES : Flexibilité et maintenabilité
 * 	elle sépare complètement la structure de vos entités de la représentation exposée aux 
 * 	consommateurs de votre API. 
 * 	Cela permet également de mieux gérer les performances en limitant la quantité de données sérialisées.
 * 
 */


public record DTOProduitPanier(Long id, int quantite, String nom_produit) {

    public static DTOProduitPanier toDTOProduitPanier(ProduitPanier produitPanier) {
        return new DTOProduitPanier(
            produitPanier.getId(),
            produitPanier.getQuantite(),
            produitPanier.getProduit().getNom()
        );
    }
}

package com.ecommerce.dto;

import com.ecommerce.entities.Client;


public record DTOClient(
				Long id, 
				String nom, 
				String prenom) {
	
	public static DTOClient fromClient(Client client) {
		return new DTOClient(
				client.getId(),
				client.getNom(),
				client.getPrenom()
				);
	}
}

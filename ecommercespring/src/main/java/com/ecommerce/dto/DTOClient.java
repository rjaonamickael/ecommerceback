package com.ecommerce.dto;

import java.util.Date;
import java.util.List;

import com.ecommerce.entities.Adresse;
import com.ecommerce.entities.Client;
import com.ecommerce.entities.Compte;
import com.fasterxml.jackson.annotation.JsonFormat;


public class DTOClient {
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String nom;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String prenom;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String phone;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-mm-dd")
	private Date date_naissance;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private List<DTOAdresse> adresses;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Compte compte;
	
	public DTOClient() {
		super();
	}

	public static DTOClient toDTOClient(Client client) {
		DTOClient dto = new DTOClient();
		dto.id = client.getId();
		dto.nom = client.getNom();
		dto.prenom = client.getPrenom();
		dto.phone = client.getPhone();
		dto.date_naissance = client.getDate_naissance();
		dto.adresses = client.getAdresses().stream().map(DTOAdresse::toDTOAdresse).toList();
		dto.compte = client.getCompte();
		
		return dto;
	}
}

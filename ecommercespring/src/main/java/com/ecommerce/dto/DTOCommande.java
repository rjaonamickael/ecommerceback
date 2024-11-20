package com.ecommerce.dto;

import java.util.Date;

import com.ecommerce.entities.Commande;
import com.fasterxml.jackson.annotation.JsonFormat;

public class DTOCommande {
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String numero_carte;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")
	private Date date_exp;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private int cc;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private DTOPanier panier;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}



	public String getNumero_carte() {
		return numero_carte;
	}



	public void setNumero_carte(String numero_carte) {
		this.numero_carte = numero_carte;
	}



	public Date getDate_exp() {
		return date_exp;
	}



	public void setDate_exp(Date date_exp) {
		this.date_exp = date_exp;
	}



	public int getCc() {
		return cc;
	}



	public void setCc(int cc) {
		this.cc = cc;
	}



	public DTOPanier getPanier() {
		return panier;
	}



	public void setPanier(DTOPanier panier) {
		this.panier = panier;
	}



	public static DTOCommande toDTOCommande(Commande commande) {
		DTOCommande dtoCommande = new DTOCommande();
		dtoCommande.setId(commande.getId());
		dtoCommande.setNumero_carte(commande.getNumero_carte());
		dtoCommande.setDate_exp(commande.getDate_exp());
		dtoCommande.setCc(commande.getCc());
		dtoCommande.setPanier(DTOPanier.toDTOPanier(commande.getPanier()));
		return dtoCommande;
	}
}

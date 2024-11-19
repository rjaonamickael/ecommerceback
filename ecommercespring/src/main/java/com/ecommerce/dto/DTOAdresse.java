package com.ecommerce.dto;

import com.ecommerce.entities.Adresse;
import com.ecommerce.utils.TypeAdresse;

public record DTOAdresse(
		Long id,
		TypeAdresse typeAdresse,
		String ville,
		String province,
		String pays,
		String codePostal) {
	
	public static DTOAdresse toDTOAdresse(Adresse adresse) {
		return new DTOAdresse(
					adresse.getId(), 
					adresse.getTypeAdresse(), 
					adresse.getVille(), 
					adresse.getProvince(), 
					adresse.getPays(), 
					adresse.getCodePostal());
	}

}

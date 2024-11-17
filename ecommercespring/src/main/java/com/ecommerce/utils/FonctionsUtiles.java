package com.ecommerce.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.repositories.RepositoryCompte;

@Component
public class FonctionsUtiles {
	@Autowired
	private RepositoryCompte repositoryCompte;

	public Map<String, String> reponse(String type,String message){
		Map<String, String> reponse = new HashMap<>();
		reponse.put(type, message);
        
        return reponse;
	}
	
	
	public boolean isEmailused(String email) {
		if(repositoryCompte.findCompteByEmail(email)!=null) {
			return true;
		}
		else {
			return false;
		}
	}
	
}

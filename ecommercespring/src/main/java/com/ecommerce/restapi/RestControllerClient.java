package com.ecommerce.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.DTOClient;
import com.ecommerce.dto.DTOCommande;
import com.ecommerce.entities.Categorie;
import com.ecommerce.entities.Client;
import com.ecommerce.entities.Commande;
import com.ecommerce.entities.Panier;
import com.ecommerce.requests.RequestCommande;
import com.ecommerce.services.ServiceClient;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/client")
public class RestControllerClient {
	
	@Autowired
	private ServiceClient serviceClient;
	
	
	@GetMapping("/gestion-compte/{id}")
    public ResponseEntity<DTOClient> getClient(@PathVariable Long id) {
        
        return serviceClient.getClient(id);
    }
	
	@Transactional
	@PutMapping("/gestion-compte/{id}")
    public ResponseEntity<DTOClient> updateClient(@PathVariable Long id,@RequestBody Client client) {
        
        return serviceClient.updateClient(id,client);
    }
	
	@Transactional
	@PostMapping("/achat")
    public ResponseEntity<DTOCommande> buyPanier(@RequestBody RequestCommande request) {
		
		return serviceClient.buy(request);
	}
}

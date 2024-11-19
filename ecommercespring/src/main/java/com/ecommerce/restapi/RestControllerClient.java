package com.ecommerce.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.DTOClient;
import com.ecommerce.entities.Categorie;
import com.ecommerce.entities.Client;
import com.ecommerce.services.ServiceClient;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/client")
public class RestControllerClient {
	
	@Autowired
	private ServiceClient serviceClient;
	
	
	@GetMapping("/gestion-compte/{id}")
    public ResponseEntity<DTOClient> getCategorie(@PathVariable Long id) {
        
        return serviceClient.getClient(id);
    }
	
	@PutMapping("/gestion-compte/{id}")
    public ResponseEntity<DTOClient> updateCategorie(@PathVariable Long id,@RequestBody Client client) {
        
        return serviceClient.updateClient(id,client);
    }
}

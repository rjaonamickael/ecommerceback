package com.ecommerce.restapi;

import com.ecommerce.entities.Categorie;
import com.ecommerce.services.ServiceAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/catalogue")
public class CatalogueController {
	
	@Autowired
    private ServiceAdmin serviceAdmin;

	// END MAP Pour les Catégories
	@GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getAllCategories() {
        
        return serviceAdmin.getAllCategories();
    }
	
	@GetMapping("/categorie/{id}")
    public ResponseEntity<Categorie> getCategorie(@PathVariable Long id) {
        
        return serviceAdmin.getCategorie(id);
    }
	
	@PostMapping("/categorie")
    public ResponseEntity<Categorie> addCategorie(@RequestBody Categorie categorie) {
        
        return serviceAdmin.addCategorie(categorie);
    }
	
	@DeleteMapping("/categorie/{id}")
    public ResponseEntity<Map<String, String>> deleteCategorie(@PathVariable Long id) {
        
        return serviceAdmin.deleteCategorie(id);
    }
	
	@PutMapping("/categorie/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id,@RequestBody Categorie categorie) {
        
        return serviceAdmin.updateCategorie(id,categorie);
    }

}

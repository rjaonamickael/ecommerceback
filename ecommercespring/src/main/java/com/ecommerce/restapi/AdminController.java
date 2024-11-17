package com.ecommerce.restapi;

import com.ecommerce.entities.Categorie;
import com.ecommerce.entities.Produit;
import com.ecommerce.requests.RequestAddProduit;
import com.ecommerce.services.ServiceCategorie;
import com.ecommerce.services.ServiceProduit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/catalogue")
public class AdminController {
	
	@Autowired
    private ServiceProduit serviceProduit;
	
	@Autowired
    private ServiceCategorie serviceCategorie;

	// END MAP Pour les Catégories
	@GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getAllCategories() {
        
        return serviceCategorie.getAllCategories();
    }
	
	@GetMapping("/categorie/{id}")
    public ResponseEntity<Categorie> getCategorie(@PathVariable Long id) {
        
        return serviceCategorie.getCategorie(id);
    }
	
	@PostMapping("/categorie")
    public ResponseEntity<Categorie> addCategorie(@RequestBody Categorie categorie) {
        
        return serviceCategorie.addCategorie(categorie);
    }
	
	@DeleteMapping("/categorie/{id}")
    public ResponseEntity<Map<String, String>> deleteCategorie(@PathVariable Long id) {
        
        return serviceCategorie.deleteCategorie(id);
    }
	
	@PutMapping("/categorie/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id,@RequestBody Categorie categorie) {
        
        return serviceCategorie.updateCategorie(id,categorie);
    }

	
	
	
	// END MAP Pour les Produits
	@GetMapping("/produits")
    public ResponseEntity<List<Produit>> getAllProduits() {
        
        return serviceProduit.getAllProduits();
    }
	
	
	@GetMapping("/produit/{id}")
    public ResponseEntity<Produit> getProduit(@PathVariable Long id) {
        
        return serviceProduit.getProduit(id);
    }
	
	@PostMapping("/produit")
    public ResponseEntity<Produit> addProduit(@RequestBody RequestAddProduit request) {
        
        return serviceProduit.addProduit(request);
    }
	
	@DeleteMapping("/produit/{id}")
    public ResponseEntity<Map<String, String>> deleteProduit(@PathVariable Long id) {
        
        return serviceProduit.deleteProduit(id);
    }
	
	@PutMapping("/produit/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id,@RequestBody RequestAddProduit request) {
        
        return serviceProduit.updateProduit(id,request);
    }


}

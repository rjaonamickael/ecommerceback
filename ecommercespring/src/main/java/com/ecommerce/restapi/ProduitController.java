package com.ecommerce.restapi;

import com.ecommerce.entities.Produit;
import com.ecommerce.repositories.RepositoryProduit;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final RepositoryProduit produitRepository;

    public ProduitController(RepositoryProduit produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping
    public List<Produit> getAllProducts() {
        return produitRepository.findAll();
    }

    @GetMapping("/search")
    public List<Produit> searchProduits(@RequestParam String keyword) {
        return produitRepository.findByNomContaining(keyword);
    }
}

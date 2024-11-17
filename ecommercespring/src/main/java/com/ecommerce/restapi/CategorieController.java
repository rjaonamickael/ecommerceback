package com.ecommerce.restapi;

import com.ecommerce.entities.Categorie;
import com.ecommerce.repositories.RepositoryCategorie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    private final RepositoryCategorie categorieRepository;

    public CategorieController(RepositoryCategorie categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    // Ajouter une nouvelle catégorie
    @PostMapping("/ajouter")
    public ResponseEntity<Categorie> ajouterCategorie(@RequestBody Categorie categorie) {
        Categorie nouvelleCategorie = categorieRepository.save(categorie);
        return new ResponseEntity<>(nouvelleCategorie, HttpStatus.CREATED);
    }

    // Obtenir toutes les catégories
    @GetMapping
    public ResponseEntity<List<Categorie>> getCategories() {
        List<Categorie> categories = categorieRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Obtenir une catégorie par ID
    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorie(@PathVariable Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return categorie.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Mettre à jour une catégorie
    @PutMapping("/{id}/modifier")
    public ResponseEntity<Categorie> modifierCategorie(@PathVariable Long id, @RequestBody Categorie categorie) {
        Optional<Categorie> categorieExistant = categorieRepository.findById(id);
        if (categorieExistant.isPresent()) {
            Categorie c = categorieExistant.get();
            c.setNom(categorie.getNom());
            c.setDescription(categorie.getDescription());
            categorieRepository.save(c);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer une catégorie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCategorie(@PathVariable Long id) {
        if (categorieRepository.existsById(id)) {
            categorieRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

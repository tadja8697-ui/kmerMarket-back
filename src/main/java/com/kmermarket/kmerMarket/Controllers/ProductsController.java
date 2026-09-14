package com.kmermarket.kmerMarket.Controllers;

import com.kmermarket.kmerMarket.Entities.Products;
import com.kmermarket.kmerMarket.Repositories.ProductsRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*")
public class ProductsController {

    @Autowired
    private ProductsRepo productsRepo;

    @PostMapping("/add")
    public Products addProducts(@Valid @RequestBody Products products){
        return productsRepo.save(products);
    }

    @GetMapping("/getAllProducts")
    public List<Products> getProducts(){
        return productsRepo.findAll();
    }

    @GetMapping("/getProducts/{userId}")
    public List<Products> getProductsByUserId(@PathVariable Long userId){
        return productsRepo.findByUserId(userId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Products> updateProducts(@PathVariable Long id,@Valid @RequestBody Products updatedProduct) {
        return productsRepo.findById(id)
                .map(existing -> {
                    existing.setName(updatedProduct.getName());
                    existing.setDesc(updatedProduct.getDesc());
                    existing.setPrice(updatedProduct.getPrice());
                    existing.setImage(updatedProduct.getImage());
                    existing.setCategory(updatedProduct.getCategory());
                    existing.setUser(updatedProduct.getUser());
                    Products saved = productsRepo.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProducts(@PathVariable Long id) {
        if (!productsRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productsRepo.deleteById(id);
        return ResponseEntity.ok("Produit supprimé avec succès");
    }

}

package com.vendorplatform.backend.controller;

import com.vendorplatform.backend.model.Product;
import com.vendorplatform.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired private ProductService productService;

    @GetMapping
    public List<Product> getAll() { return productService.getAll(); }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) { return productService.getById(id); }

    @GetMapping("/vendor/{vendorId}")
    public List<Product> getByVendor(@PathVariable Long vendorId) {
        return productService.getByVendor(vendorId);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String q) { return productService.search(q); }

    @GetMapping("/category/{cat}")
    public List<Product> byCategory(@PathVariable String cat) { return productService.getByCategory(cat); }

    @PostMapping
    public Product create(@RequestBody Product product) { return productService.save(product); }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
package com.vendorplatform.backend.service;

import com.vendorplatform.backend.model.Product;
import com.vendorplatform.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Product save(Product p) { return productRepo.save(p); }
    public List<Product> getAll() { return productRepo.findAll(); }
    public Product getById(Long id) { return productRepo.findById(id).orElseThrow(); }
    public List<Product> getByVendor(Long vendorId) { return productRepo.findByVendorId(vendorId); }
    public List<Product> search(String keyword) { return productRepo.findByNameContainingIgnoreCase(keyword); }
    public List<Product> getByCategory(String cat) { return productRepo.findByCategoryIgnoreCase(cat); }
    public void delete(Long id) { productRepo.deleteById(id); }
    public Product update(Long id, Product updated) {
        Product p = getById(id);
        p.setName(updated.getName());
        p.setPrice(updated.getPrice());
        p.setStock(updated.getStock());
        p.setDescription(updated.getDescription());
        p.setCategory(updated.getCategory());
        return productRepo.save(p);
    }
}

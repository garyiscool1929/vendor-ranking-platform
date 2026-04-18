package com.vendorplatform.backend.controller;



import com.vendorplatform.backend.model.Vendor;
import com.vendorplatform.backend.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired private VendorRepository vendorRepo;

    @GetMapping
    public List<Vendor> getAll() { return vendorRepo.findAll(); }

    @GetMapping("/{id}")
    public Vendor getById(@PathVariable Long id) { return vendorRepo.findById(id).orElseThrow(); }

    @PostMapping
    public Vendor create(@RequestBody Vendor vendor) { return vendorRepo.save(vendor); }

    @PutMapping("/{id}/approve")
    public Vendor approve(@PathVariable Long id) {
        Vendor v = vendorRepo.findById(id).orElseThrow();
        v.setStatus(Vendor.Status.APPROVED);
        return vendorRepo.save(v);
    }

    @PutMapping("/{id}/ban")
    public Vendor ban(@PathVariable Long id) {
        Vendor v = vendorRepo.findById(id).orElseThrow();
        v.setStatus(Vendor.Status.BANNED);
        return vendorRepo.save(v);
    }
}

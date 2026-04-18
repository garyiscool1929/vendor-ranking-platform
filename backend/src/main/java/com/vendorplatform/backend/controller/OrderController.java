package com.vendorplatform.backend.controller;

import com.vendorplatform.backend.model.Order;
import com.vendorplatform.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired private OrderRepository orderRepo;

    @GetMapping("/buyer/{buyerId}")
    public List<Order> getByBuyer(@PathVariable Long buyerId) { return orderRepo.findByBuyerId(buyerId); }

    @GetMapping("/vendor/{vendorId}")
    public List<Order> getByVendor(@PathVariable Long vendorId) { return orderRepo.findByVendorId(vendorId); }

    @PostMapping
    public Order createOrder(@RequestBody Order order) { return orderRepo.save(order); }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam String status) {
        Order o = orderRepo.findById(id).orElseThrow();
        o.setStatus(Order.Status.valueOf(status));
        return orderRepo.save(o);
    }
}
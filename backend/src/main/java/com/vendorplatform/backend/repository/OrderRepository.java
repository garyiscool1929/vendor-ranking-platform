package com.vendorplatform.backend.repository;

import com.vendorplatform.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyerId(Long buyerId);
    List<Order> findByVendorId(Long vendorId);
    long countByVendorIdAndStatus(Long vendorId, Order.Status status);
}
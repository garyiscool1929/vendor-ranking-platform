package com.vendorplatform.backend.repository;

import com.vendorplatform.backend.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findByStatusOrderByScoreDesc(Vendor.Status status);
    Optional<Vendor> findByUserId(Long userId);
}

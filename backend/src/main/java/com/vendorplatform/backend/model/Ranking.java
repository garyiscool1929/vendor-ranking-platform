package com.vendorplatform.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "rankings")
@Data
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "vendor_id", unique = true)
    private Vendor vendor;

    private Double avgRating = 0.0;
    private Double salesVolume = 0.0;
    private Double fulfillmentRate = 0.0;
    private Double responseTimeScore = 0.0;
    private Double finalScore = 0.0;
    private Integer rankPosition = 0;
    private LocalDateTime updatedAt = LocalDateTime.now();
}

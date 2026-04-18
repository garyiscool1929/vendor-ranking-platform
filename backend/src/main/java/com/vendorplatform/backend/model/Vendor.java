package com.vendorplatform.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendors")
@Data
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String storeName;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private Double score = 0.0;
    private Integer rankPosition = 0;
    private Double responseTimeHours = 24.0;
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status { PENDING, APPROVED, BANNED }
}

package com.vendorplatform.backend.repository;

import com.vendorplatform.backend.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Optional<Ranking> findByVendorId(Long vendorId);
    List<Ranking> findAllByOrderByFinalScoreDesc();
}

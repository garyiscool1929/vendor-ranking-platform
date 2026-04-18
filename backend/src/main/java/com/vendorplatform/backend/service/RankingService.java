package com.vendorplatform.backend.service;

import com.vendorplatform.backend.model.*;
import com.vendorplatform.backend.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RankingService {

    private final RankingRepository rankingRepo;
    private final VendorRepository vendorRepo;
    private final ReviewRepository reviewRepo;
    private final OrderRepository orderRepo;

    public RankingService(RankingRepository rankingRepo, VendorRepository vendorRepo,
                          ReviewRepository reviewRepo, OrderRepository orderRepo) {
        this.rankingRepo = rankingRepo;
        this.vendorRepo = vendorRepo;
        this.reviewRepo = reviewRepo;
        this.orderRepo = orderRepo;
    }

    // Main algorithm: Score = (AvgRating × 0.4) + (SalesVolume × 0.3) + (FulfillmentRate × 0.2) + (ResponseTime × 0.1)
    public void recalculateRankings() {
        List<Vendor> vendors = vendorRepo.findByStatusOrderByScoreDesc(Vendor.Status.APPROVED);

        for (Vendor vendor : vendors) {
            Ranking ranking = rankingRepo.findByVendorId(vendor.getId())
                    .orElse(new Ranking());
            ranking.setVendor(vendor);

            // 1. Average Rating (out of 5)
            Double avgRating = reviewRepo.findAvgRatingByVendorId(vendor.getId());
            double normalizedRating = (avgRating != null ? avgRating : 0) / 5.0 * 10;

            // 2. Sales Volume (count of delivered orders)
            long delivered = orderRepo.countByVendorIdAndStatus(vendor.getId(), Order.Status.DELIVERED);
            double normalizedSales = Math.min(delivered / 100.0 * 10, 10); // cap at 10

            // 3. Fulfillment Rate (delivered / total orders)
            long totalOrders = orderRepo.findByVendorId(vendor.getId()).size();
            double fulfillmentRate = totalOrders > 0 ? (double) delivered / totalOrders : 0;
            double normalizedFulfillment = fulfillmentRate * 10;

            // 4. Response Time Score (lower is better, normalized inversely)
            double responseHours = vendor.getResponseTimeHours();
            double responseScore = Math.max(0, 10 - responseHours / 24.0 * 10);

            // Final Score Formula
            double finalScore = (normalizedRating * 0.4)
                    + (normalizedSales * 0.3)
                    + (normalizedFulfillment * 0.2)
                    + (responseScore * 0.1);

            ranking.setAvgRating(avgRating != null ? avgRating : 0);
            ranking.setSalesVolume((double) delivered);
            ranking.setFulfillmentRate(fulfillmentRate);
            ranking.setResponseTimeScore(responseScore);
            ranking.setFinalScore(Math.round(finalScore * 100.0) / 100.0);

            rankingRepo.save(ranking);
            vendor.setScore(ranking.getFinalScore());
            vendorRepo.save(vendor);
        }

        // Assign rank positions
        List<Ranking> sorted = rankingRepo.findAllByOrderByFinalScoreDesc();
        for (int i = 0; i < sorted.size(); i++) {
            sorted.get(i).setRankPosition(i + 1);
            rankingRepo.save(sorted.get(i));
        }
    }

    public List<Ranking> getLeaderboard() {
        return rankingRepo.findAllByOrderByFinalScoreDesc();
    }
}

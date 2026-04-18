package com.vendorplatform.backend.controller;

import com.vendorplatform.backend.model.Review;
import com.vendorplatform.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired private ReviewRepository reviewRepo;

    @GetMapping("/vendor/{vendorId}")
    public List<Review> getReviews(@PathVariable Long vendorId) {
        return reviewRepo.findByVendorId(vendorId);
    }

    @PostMapping
    public Review addReview(@RequestBody Review review) { return reviewRepo.save(review); }
}

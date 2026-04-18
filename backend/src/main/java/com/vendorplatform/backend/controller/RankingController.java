package com.vendorplatform.backend.controller;

import com.vendorplatform.backend.model.Ranking;
import com.vendorplatform.backend.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    @Autowired private RankingService rankingService;

    @GetMapping
    public List<Ranking> getLeaderboard() { return rankingService.getLeaderboard(); }

    @PostMapping("/recalculate")
    public String recalculate() {
        rankingService.recalculateRankings();
        return "Rankings updated!";
    }
}

package com.BackEnd.BackEndHealthHabbits.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BackEnd.BackEndHealthHabbits.dto.RankDTO;
import com.BackEnd.BackEndHealthHabbits.services.RankService;

@RestController
@RequestMapping("/api/rank")
public class RankController {

    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping
    public ResponseEntity<List<RankDTO>> getRank() {
        List<RankDTO> ranking = rankService.getAllUsersByRank();
        return ResponseEntity.ok(ranking);
    }
}

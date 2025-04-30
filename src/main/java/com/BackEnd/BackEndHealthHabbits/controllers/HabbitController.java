package com.BackEnd.BackEndHealthHabbits.controllers;

import com.BackEnd.BackEndHealthHabbits.dto.ConfirmHabbitRequestDTO;
import com.BackEnd.BackEndHealthHabbits.dto.HabbitHistoryDTO;
import com.BackEnd.BackEndHealthHabbits.services.HabbitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habbits")
public class HabbitController {

    private final HabbitService habbitService;

    public HabbitController(HabbitService habbitService) {
        this.habbitService = habbitService;
    }

    @PreAuthorize("hasAuthority('UPDATE_APP')")
    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(@RequestBody ConfirmHabbitRequestDTO req) {
        habbitService.confirmHabbit(req);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('VIEW_APP')")
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<HabbitHistoryDTO>> history(@PathVariable Long userId) {
        return ResponseEntity.ok(habbitService.getHistory(userId));
    }
}
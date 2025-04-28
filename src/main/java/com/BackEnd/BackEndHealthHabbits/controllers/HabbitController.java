package com.BackEnd.BackEndHealthHabbits.controllers;

import com.BackEnd.BackEndHealthHabbits.dto.ConfirmHabbitRequestDTO;
import com.BackEnd.BackEndHealthHabbits.repositories.RankRepository;
import com.BackEnd.BackEndHealthHabbits.services.HabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habbits")
public class HabbitController {

    private final HabbitService habbitService;

    public HabbitController(HabbitService habbitService) {
        this.habbitService = habbitService;
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(@RequestBody ConfirmHabbitRequestDTO req) {
        habbitService.confirmHabbit(req);
        return ResponseEntity.ok().build();
    }
}
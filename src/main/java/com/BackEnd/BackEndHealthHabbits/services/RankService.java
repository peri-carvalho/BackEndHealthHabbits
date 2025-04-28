package com.BackEnd.BackEndHealthHabbits.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BackEnd.BackEndHealthHabbits.dto.RankDTO;
import com.BackEnd.BackEndHealthHabbits.entities.Rank;
import com.BackEnd.BackEndHealthHabbits.repositories.RankRepository;

@Service
public class RankService {

    private final RankRepository rankRepo;

    public RankService(RankRepository rankRepo) {
        this.rankRepo = rankRepo;
    }

    @Transactional(readOnly = true)
    public List<RankDTO> getAllUsersByRank() {
        return rankRepo.findAllByOrderByPointValueDesc()
                .stream()
                .map(r -> new RankDTO(
                        r.getUser().getId(),
                        r.getUser().getName(),
                        r.getPointValue()
                ))
                .collect(Collectors.toList());
    }
}
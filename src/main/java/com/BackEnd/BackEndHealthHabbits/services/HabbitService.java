package com.BackEnd.BackEndHealthHabbits.services;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BackEnd.BackEndHealthHabbits.dto.ConfirmHabbitRequestDTO;
import com.BackEnd.BackEndHealthHabbits.entities.Habbit;
import com.BackEnd.BackEndHealthHabbits.entities.Rank;
import com.BackEnd.BackEndHealthHabbits.entities.User;
import com.BackEnd.BackEndHealthHabbits.repositories.HabbitRepository;
import com.BackEnd.BackEndHealthHabbits.repositories.RankRepository;
import com.BackEnd.BackEndHealthHabbits.repositories.UserRepository;

@Service
public class HabbitService {

    private final UserRepository userRepo;
    private final HabbitRepository habbitRepo;
    private final RankRepository rankRepo;

    public HabbitService(UserRepository userRepo,
                         HabbitRepository habbitRepo,
                         RankRepository rankRepo) {
        this.userRepo = userRepo;
        this.habbitRepo = habbitRepo;
        this.rankRepo = rankRepo;
    }

    @Transactional
    public void confirmHabbit(ConfirmHabbitRequestDTO req) {
        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + req.getUserId()));

        habbitRepo.findTopByUserAndNameOrderByPerformedAtDesc(user, req.getName())
                .ifPresent(last -> {
                    Duration sinceLast = Duration.between(last.getPerformedAt(), Instant.now());
                    if (sinceLast.toHours() < 24) {
                        throw new IllegalStateException(
                                "Você só pode confirmar o hábito \""
                                        + req.getName()
                                        + "\" uma vez a cada 24 horas. Faltam "
                                        + (24 - sinceLast.toHours())
                                        + "h para poder confirmar novamente."
                        );
                    }
                });

        Habbit habbit = new Habbit();
        habbit.setUser(user);
        habbit.setName(req.getName());
        habbit.setDescription(req.getDescription());
        habbit.setCategory(req.getCategory());
        habbit.setRecommendedQuantity(req.getRecommendedQuantity());
        habbit.setRecommendedDuration(req.getRecommendedDuration());
        habbit.setPointValue(req.getPointValue());
        habbitRepo.save(habbit);

        updateRank(user, req.getPointValue());
    }

    private void updateRank(User user, Integer earnedPoints) {
        Rank rank = rankRepo.findByUser(user)
                .orElseGet(() -> {
                    Rank r = new Rank();
                    r.setUser(user);
                    r.setPointValue(0);
                    return r;
                });
        rank.setPointValue(rank.getPointValue() + earnedPoints);
        rankRepo.save(rank);
    }
}
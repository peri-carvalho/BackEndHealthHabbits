package com.BackEnd.BackEndHealthHabbits.services;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.BackEnd.BackEndHealthHabbits.dto.ConfirmHabbitRequestDTO;
import com.BackEnd.BackEndHealthHabbits.dto.HabbitHistoryDTO;
import com.BackEnd.BackEndHealthHabbits.entities.HabbitDefinition;
import com.BackEnd.BackEndHealthHabbits.repositories.HabbitDefinitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BackEnd.BackEndHealthHabbits.entities.Habbit;
import com.BackEnd.BackEndHealthHabbits.entities.Rank;
import com.BackEnd.BackEndHealthHabbits.entities.User;
import com.BackEnd.BackEndHealthHabbits.repositories.HabbitRepository;
import com.BackEnd.BackEndHealthHabbits.repositories.RankRepository;
import com.BackEnd.BackEndHealthHabbits.repositories.UserRepository;

@Service
public class HabbitService {
    private final UserRepository              userRepo;
    private final HabbitDefinitionRepository defRepo;
    private final HabbitRepository            habbitRepo;
    private final RankRepository              rankRepo;
    private final AchievementService          achievementService;

    public HabbitService(UserRepository userRepo,
                         HabbitDefinitionRepository defRepo,
                         HabbitRepository habbitRepo,
                         RankRepository rankRepo,
                         AchievementService achievementService) {
        this.userRepo   = userRepo;
        this.defRepo    = defRepo;
        this.habbitRepo = habbitRepo;
        this.rankRepo   = rankRepo;
        this.achievementService  = achievementService;
    }

    @Transactional
    public void confirmHabbit(ConfirmHabbitRequestDTO req) {
        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        HabbitDefinition def = defRepo.findById(req.getHabbitId())
                .orElseThrow(() -> new IllegalArgumentException("Hábito não definido"));

        habbitRepo.findTopByUserIdAndDefinitionIdOrderByPerformedAtDesc(user.getId(), def.getId())
                .ifPresent(last -> {
                    Duration since = Duration.between(last.getPerformedAt(), Instant.now());
                    if (since.toHours() < 24) {
                        throw new IllegalStateException(
                                "Você só pode repetir este hábito após 24h. Faltam "
                                        + (24 - since.toHours()) + "h."
                        );
                    }
                });

        Habbit exec = new Habbit();
        exec.setUser(user);
        exec.setDefinition(def);
        habbitRepo.save(exec);

        Rank rank = rankRepo.findByUser(user)
                .orElseGet(() -> {
                    Rank r = new Rank();
                    r.setUser(user);
                    r.setPointValue(0);
                    return r;
                });
        rank.setPointValue(rank.getPointValue() + def.getPointValue());
        rankRepo.save(rank);

        achievementService.awardAchievementsForUser(user.getId());
    }

    @Transactional(readOnly = true)
    public List<HabbitHistoryDTO> getHistory(Long userId) {
        return habbitRepo.findByUserIdOrderByPerformedAtDesc(userId)
                .stream()
                .map(h -> new HabbitHistoryDTO(
                        h.getDefinition().getName(),
                        h.getPerformedAt(),
                        h.getDefinition().getPointValue()
                ))
                .collect(Collectors.toList());
    }
}
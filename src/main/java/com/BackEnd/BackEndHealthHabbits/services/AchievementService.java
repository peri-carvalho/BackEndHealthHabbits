package com.BackEnd.BackEndHealthHabbits.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BackEnd.BackEndHealthHabbits.repositories.AchievementRepository;

@Service
public class AchievementService {

    private final AchievementRepository uaRepo;

    public AchievementService(AchievementRepository uaRepo) {
        this.uaRepo = uaRepo;
    }

    @Transactional
    public void awardAchievementsForUser(Long userId) {
        uaRepo.insertNewAchievements(userId);
        uaRepo.addAchievementPoints(userId);
    }
}

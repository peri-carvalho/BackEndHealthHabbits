package com.BackEnd.BackEndHealthHabbits.repositories;

import com.BackEnd.BackEndHealthHabbits.entities.AchievementDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementDefinitionRepository extends JpaRepository<AchievementDefinition, Long> {
}

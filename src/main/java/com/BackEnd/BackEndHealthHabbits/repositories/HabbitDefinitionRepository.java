package com.BackEnd.BackEndHealthHabbits.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.BackEnd.BackEndHealthHabbits.entities.HabbitDefinition;

public interface HabbitDefinitionRepository extends JpaRepository<HabbitDefinition, Long> {
}

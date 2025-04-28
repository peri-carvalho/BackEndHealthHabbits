package com.BackEnd.BackEndHealthHabbits.repositories;

import com.BackEnd.BackEndHealthHabbits.entities.Habbit;
import com.BackEnd.BackEndHealthHabbits.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabbitRepository extends JpaRepository<Habbit,Long> {
    Optional<Habbit> findTopByUserAndNameOrderByPerformedAtDesc(User user, String name);
}

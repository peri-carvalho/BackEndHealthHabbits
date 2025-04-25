package com.BackEnd.BackEndHealthHabbits.repositories;

import com.BackEnd.BackEndHealthHabbits.entities.Habbit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabbitRepository extends JpaRepository<Habbit,Long> {
}

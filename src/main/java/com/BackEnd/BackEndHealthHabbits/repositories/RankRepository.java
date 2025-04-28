package com.BackEnd.BackEndHealthHabbits.repositories;

import com.BackEnd.BackEndHealthHabbits.entities.Rank;
import com.BackEnd.BackEndHealthHabbits.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {
    Optional<Rank> findByUser(User user);
    List<Rank> findAllByOrderByPointValueDesc();
}

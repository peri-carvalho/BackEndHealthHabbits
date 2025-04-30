package com.BackEnd.BackEndHealthHabbits.repositories;

import com.BackEnd.BackEndHealthHabbits.entities.Habbit;
import com.BackEnd.BackEndHealthHabbits.entities.HabbitDefinition;
import com.BackEnd.BackEndHealthHabbits.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HabbitRepository extends JpaRepository<Habbit,Long> {

    @Query(value = """
            SELECT *
             FROM tb_habbits
             WHERE user_id = :userid
             AND id = :habbitId
             ORDER BY performed_at DESC
             LIMIT 1
            """, nativeQuery = true)
    Optional<Habbit> findTopByHabbitIdByPerformedAtDesc( @Param("user") Long userid,
                                                         @Param("habbitId") Long habbitId
    );

    // pega a última execução daquele hábito (definition) pelo usuário
    Optional<Habbit> findTopByUserIdAndDefinitionIdOrderByPerformedAtDesc(Long userId, Long definitionId);

    // histórico completo do usuário, mais recentes primeiro
    List<Habbit> findByUserIdOrderByPerformedAtDesc(Long userId);

    Integer countByDefinitionAndUser(Long habbitId, Long userId);

}

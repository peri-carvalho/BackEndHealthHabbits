package com.BackEnd.BackEndHealthHabbits.repositories;

import com.BackEnd.BackEndHealthHabbits.entities.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Modifying
    @Query(value = """
        INSERT INTO tb_user_achievement (user_id, achievement_def_id, earned_at)
        SELECT uh.user_id,
               ad.id,
               NOW()
          FROM (
               SELECT user_id,
                      hd.category AS category,
                      COUNT(*) AS cnt
                 FROM tb_user_habbit uh
                 JOIN tb_habbit_def hd ON hd.id = uh.definition_id
                WHERE uh.user_id = :userId
                GROUP BY user_id, hd.category
               ) AS uh
          JOIN tb_achievement_def AS ad
            ON ad.required_quantity = uh.cnt
           AND ad.category = uh.category
          LEFT JOIN tb_user_achievement AS ua
            ON ua.user_id = uh.user_id
           AND ua.achievement_def_id = ad.id
         WHERE ua.id IS NULL
        """, nativeQuery = true)
    void insertNewAchievements(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
        UPDATE tb_rank AS r
           SET point_value = r.point_value
                           + COALESCE((
                               SELECT SUM(ad.point_value)
                                 FROM tb_achievement_def AS ad
                                 JOIN tb_user_achievement AS ua
                                   ON ua.achievement_def_id = ad.id
                                WHERE ua.user_id = :userId
                                  AND ua.earned_at >= NOW() - INTERVAL '1 MINUTE'
                             ), 0)
         WHERE r.user_id = :userId
        """, nativeQuery = true)
    void addAchievementPoints(@Param("userId") Long userId);
}

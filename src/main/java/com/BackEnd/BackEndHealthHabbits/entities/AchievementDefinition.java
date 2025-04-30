package com.BackEnd.BackEndHealthHabbits.entities;

import com.BackEnd.BackEndHealthHabbits.entities.enums.Category;
import com.BackEnd.BackEndHealthHabbits.entities.enums.Rarity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_achievement_def")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AchievementDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Rarity rarity;

    @Column(name = "required_quantity", nullable = false)
    private Integer required_quantity;

    @Column(name = "point_value", nullable = false)
    private Integer pointValue;
}

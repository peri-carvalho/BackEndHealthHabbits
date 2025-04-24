package com.BackEnd.BackEndHealthHabbits.entities;

import com.BackEnd.BackEndHealthHabbits.entities.enums.Category;
import com.BackEnd.BackEndHealthHabbits.entities.enums.Rarity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_achievement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Category category;

    @Column
    private Rarity rarity;

    @Column
    private String description;

    @Column
    private Integer required_quantity;

}

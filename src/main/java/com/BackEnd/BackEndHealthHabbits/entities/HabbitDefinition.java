package com.BackEnd.BackEndHealthHabbits.entities;

import com.BackEnd.BackEndHealthHabbits.entities.enums.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_habbit_def")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HabbitDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Category category;

    @Column
    private Integer recommendedQuantity;

    @Column
    private Integer recommendedDuration;

    @Column
    private Integer pointValue;

}

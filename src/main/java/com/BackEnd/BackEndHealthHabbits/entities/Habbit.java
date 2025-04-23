package com.BackEnd.BackEndHealthHabbits.entities;

import com.BackEnd.BackEndHealthHabbits.entities.enums.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_habbits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Habbit {

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
    private Integer recommended_quantity;

    @Column
    private Integer recommended_duration;

    @Column
    private Integer point_value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

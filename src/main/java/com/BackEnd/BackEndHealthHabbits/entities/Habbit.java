package com.BackEnd.BackEndHealthHabbits.entities;

import com.BackEnd.BackEndHealthHabbits.entities.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

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
    private Integer recommendedQuantity;

    @Column
    private Integer recommendedDuration;

    @Column
    private Integer pointValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable= false)
    private User user;

    @CreationTimestamp
    @Column(name = "performed_at", updatable = false, nullable = false)
    private Instant performedAt;
}

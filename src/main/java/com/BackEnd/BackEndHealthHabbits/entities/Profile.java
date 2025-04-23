package com.BackEnd.BackEndHealthHabbits.entities;

import jakarta.persistence.*;
import lombok.Data;
import com.BackEnd.BackEndHealthHabbits.entities.enums.Rule;

import java.util.EnumSet;
import java.util.Set;

@Data
@Entity(name = "tb_profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection(targetClass = Rule.class)
    @CollectionTable(name = "tb_profile_rules",
            joinColumns = @JoinColumn(name = "profile_id"))
    @Enumerated(EnumType.STRING)
    private Set<Rule> rule = EnumSet.noneOf(Rule.class);
}

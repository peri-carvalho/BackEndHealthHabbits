package com.BackEnd.BackEndHealthHabbits.dto;

import com.BackEnd.BackEndHealthHabbits.entities.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmHabbitRequestDTO {
    private Long userId;
    private String name;
    private String description;
    private Category category;
    private Integer recommendedQuantity;
    private Integer recommendedDuration;
    private Integer pointValue;
}

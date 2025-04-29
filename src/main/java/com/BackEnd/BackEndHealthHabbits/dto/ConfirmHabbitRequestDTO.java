package com.BackEnd.BackEndHealthHabbits.dto;

import com.BackEnd.BackEndHealthHabbits.entities.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmHabbitRequestDTO {
    private Long userId;
    private Long habbitId;
}

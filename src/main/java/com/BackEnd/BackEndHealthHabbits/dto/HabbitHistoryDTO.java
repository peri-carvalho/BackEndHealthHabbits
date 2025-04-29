package com.BackEnd.BackEndHealthHabbits.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class HabbitHistoryDTO {
    private String habitName;
    private Instant performedAt;
    private Integer pointValue;
}

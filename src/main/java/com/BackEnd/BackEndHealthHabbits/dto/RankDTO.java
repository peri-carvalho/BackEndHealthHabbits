package com.BackEnd.BackEndHealthHabbits.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankDTO {
    private Long userId;
    private String userName;
    private Integer pointValue;
}
package com.example.groupservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class InteractionDto {

    private Long id;
    private Long postingId;
    private String comment;
    private Long userId;
    private boolean liked;
    private boolean followed;
}

package com.example.groupservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupAndUserDto {
    private Long id;
    private Long groupId;
    private Long userId;
}

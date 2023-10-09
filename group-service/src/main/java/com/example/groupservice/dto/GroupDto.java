package com.example.groupservice.dto;

import com.example.groupservice.entity.Posting;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GroupDto {

    private Long id;

    private String name;

    private String description;


}

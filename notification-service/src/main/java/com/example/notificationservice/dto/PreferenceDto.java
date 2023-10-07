package com.example.notificationservice.dto;

import com.example.notificationservice.entity.Topics;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class PreferenceDto {
    private long id;
    private long userId;
    private String email;
    private String topics;
}

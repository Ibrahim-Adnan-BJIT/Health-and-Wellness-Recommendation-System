package com.example.userservices.DTO.request;

import lombok.*;

import java.time.LocalTime;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyScheduleDTO {
    private LocalTime wakeTime; // Use LocalTime to store time
    private LocalTime bedTime; // Use LocalTime to store time
}

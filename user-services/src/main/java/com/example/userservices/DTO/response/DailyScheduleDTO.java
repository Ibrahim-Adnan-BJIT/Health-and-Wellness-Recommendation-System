package com.example.userservices.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyScheduleDTO {
    private LocalTime wakeTime;
    private LocalTime bedTime;
}

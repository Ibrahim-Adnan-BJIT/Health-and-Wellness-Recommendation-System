package com.example.userservices.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyScheduleDTO {
    @NotNull(message = "WakeTime should not be null")
    private LocalTime wakeTime; // Use LocalTime to store time

    @NotNull(message = "BedTime should not be null")
    private LocalTime bedTime; // Use LocalTime to store time
}

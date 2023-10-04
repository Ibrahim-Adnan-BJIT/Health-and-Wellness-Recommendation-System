package com.example.progress.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalHealthProgressResponseDTO {
    private String totalRecords;
    private String bloodPressureMessage;
    private String diabetesLevelMessage;
    private String motivationMessage;
    private String sleepMessage;
}

package com.example.progress.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthProxyResponseDTO {
    private List<MentalHealthProxyDTO> mentalHealthProxyDTOS;
    private List<PhysicalHealthProxyDTO> physicalHealthProxyDTOS;
}

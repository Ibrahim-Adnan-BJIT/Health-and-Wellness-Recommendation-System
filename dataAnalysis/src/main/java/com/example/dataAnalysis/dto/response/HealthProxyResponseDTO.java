package com.example.dataAnalysis.dto.response;

import com.example.dataAnalysis.dto.request.MentalHealthProxyDTO;
import com.example.dataAnalysis.dto.request.PhysicalHealthProxyDTO;
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

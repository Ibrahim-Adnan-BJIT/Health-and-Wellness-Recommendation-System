package com.example.dataAnalysis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressHistoryDTO {
    private MentalHistoryDTO mentalHistoryDTO;
    private PhysicalHealthHistoryDTO physicalHealthHistoryDTO;

}

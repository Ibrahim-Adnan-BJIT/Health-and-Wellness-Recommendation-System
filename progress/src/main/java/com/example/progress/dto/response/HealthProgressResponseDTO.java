package com.example.progress.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthProgressResponseDTO {
	private long userId;
	private MentalHealthProgressResponseDTO mentalHealthProgressResponseDTO;
	private PhysicalHealthProgressResponseDTO physicalHealthProgressResponseDTO;
}

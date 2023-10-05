package com.example.userservices.DTO.request;

import com.example.userservices.model.DailySchedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotEmpty(message = "Address should not be empty")
    private String address;

    @NotEmpty(message = "Mobile should not be empty")
    private String mobile;

    @NotEmpty(message = "Nationality should not be empty")
    private String nationality;

    @NotEmpty(message = "HomeDistrict should not be empty")
    private String homeDistrict;

    @Valid
    private HealthDetailsDTO healthDetails;
}

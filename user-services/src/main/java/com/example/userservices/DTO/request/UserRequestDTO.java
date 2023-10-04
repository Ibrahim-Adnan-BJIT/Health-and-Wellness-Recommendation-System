package com.example.userservices.DTO.request;

import com.example.userservices.model.DailySchedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String address;

    private String mobile;

    private String nationality;

    private String homeDistrict;

    private HealthDetailsDTO healthDetails;
}

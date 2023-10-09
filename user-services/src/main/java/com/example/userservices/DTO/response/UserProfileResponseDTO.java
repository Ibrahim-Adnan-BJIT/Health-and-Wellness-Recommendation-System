package com.example.userservices.DTO.response;

import com.example.userservices.model.Enum.BloodGroup;
import com.example.userservices.model.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDTO {
    private long id;
    private String username;
    private String email;
    private String address;
    private long age;
    private String gender;
    private String mobile;
    private String nationality;
    private String homeDistrict;
    private Double weight;
    private Double height;
    private Double bmi;
    private Double bmr;
    private String bloodGroup;
}

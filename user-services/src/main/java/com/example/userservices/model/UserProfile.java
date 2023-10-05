package com.example.userservices.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    private long userId;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "homeDistrict")
    private String homeDistrict;
}

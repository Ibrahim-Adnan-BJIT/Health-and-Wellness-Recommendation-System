package com.example.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "preference")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Setter
@Getter
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Topics topics;


}

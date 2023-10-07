package com.example.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String venue;
    private String speaker;
    @Enumerated(EnumType.STRING)
    private Topics topics;

}

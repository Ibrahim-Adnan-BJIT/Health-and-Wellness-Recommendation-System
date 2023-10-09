package com.example.groupservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interaction")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Long userId;
    private boolean liked;
    private boolean followed;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posting posting;


}

package com.example.groupservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "groupanduser")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class GroupUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long groupId;
    private Long userId;

}

package com.example.groupservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grouping")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Grouping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String description;

    @OneToMany(mappedBy = "grouping", cascade = CascadeType.ALL)
    private List<Posting> postings = new ArrayList<>();



}

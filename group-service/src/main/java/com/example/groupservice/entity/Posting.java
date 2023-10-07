package com.example.groupservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posting")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String postDescription;

    private String groupName;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Grouping grouping;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    private List<Interaction> interactions = new ArrayList<>();


}

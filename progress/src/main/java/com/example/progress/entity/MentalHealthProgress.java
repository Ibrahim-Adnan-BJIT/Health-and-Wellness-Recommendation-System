package com.example.progress.entity;

import com.example.progress.external.enums.Mode;
import com.example.progress.external.enums.StressLevel;
import com.example.progress.external.enums.LifeSatisfaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentalHealthProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private long userId;

    private long age;

    private boolean depression;
    private boolean anxiety;
    private boolean panicDisorder;
    private boolean bipolarDisorder;
    private boolean schizophrenia;

    @Enumerated(EnumType.STRING)
    private Mode mode;

    @Enumerated(EnumType.STRING)
    private StressLevel stressLevel;

    @Enumerated(EnumType.STRING)
    private LifeSatisfaction lifeSatisfaction;

    private LocalDate date;
}

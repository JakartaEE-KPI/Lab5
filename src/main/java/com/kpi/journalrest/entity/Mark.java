package com.kpi.journalrest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "mark")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "is_present")
    Boolean presence;

    @Column(name = "point")
    Integer point;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;
}
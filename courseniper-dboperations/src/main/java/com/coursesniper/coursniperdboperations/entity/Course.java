package com.coursesniper.coursniperdboperations.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "section_name", nullable = false, length = 100)
    private String sectionName;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
     @Column (name="faculty", nullable=false, length= 100)
     private String faculty;

    @Column(name = "available_seats")
    private Integer availableSeats;

    @OneToMany(mappedBy = "course")
    private Set<TargetCourse> targetCourses;
}

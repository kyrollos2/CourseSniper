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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"term", "section_name"})
})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    private int id;
    
    @Column(name = "term", nullable = false, length = 100)
    private String term;

    @Column(name = "section_name", nullable = false, length = 100)
    private String sectionName;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "faculty", length = 255)
    private String faculty;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @OneToMany(mappedBy = "course")
    private Set<TargetCourse> targetCourses;
}

package com.coursesniper.coursniperdboperations.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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

@Table(name = "courses",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"term", "section_name"}, name = "unique_term_section")
       },
       indexes = {
           @Index(name = "idx_courses_title", columnList = "title"),
           @Index(name = "idx_courses_available_seats", columnList = "available_seats"),
           @Index(name = "idx_courses_term_section_name", columnList = "term, section_name")
       }
)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    private int id;
    
    @Column(name = "term", nullable = false, length = 100)
    private String term;
    @Column(name="status", nullable=false, length=10)
    private String status;
    
    @Column(name = "section_name", nullable = false, length = 100)
    private String sectionName;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;
    @Column(name="dates", length=20)
    private String dates;
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name="end_date", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column (name="course_location", length=255)
    private String location;
    @Column(name = "faculty", length = 255)
    private String faculty;
    @Column(name="available", length=10)
    private String availability;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;
    @Column (name="course_credits")
    private Integer credits;
    @OneToMany(mappedBy = "course")
    private Set<TargetCourse> targetCourses;
}

package com.coursesniper.coursesniperdboperations.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="target_courses",
    indexes={
        @Index(name="idx__target_courses_student_id", columnList="student_id"),
        @Index(name="idx_target_courses_id",columnList="course_id")
    })
public class TargetCourse {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="student_id",nullable=false)
    private Student student;
    @ManyToOne
    @JoinColumn(name="course_id",nullable=false)
    private int courseId;



}

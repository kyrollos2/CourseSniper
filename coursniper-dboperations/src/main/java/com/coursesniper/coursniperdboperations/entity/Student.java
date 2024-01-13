package com.coursesniper.coursniperdboperations.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name="students")

public class Student {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column(name="student_id")
    private int studentId;
    @Column(name="first_name", nullable=false,length=100)
    private String firstName;
    @Column(name="last_name",nullable=false,length=100)
    private String lastName;
    @Column (name="email",nullable=false,unique=true, length=100)
    private String email;
    @Column (name="password",nullable=false,unique=true, length=100)
    private String password;
    @ManyToOne
    @JoinColumn(name="desired_section_name",referencedColumnName="section_name")
    private Course desiredCourse;
    @OneToMany(mappedBy="student")
    private Set<TargetCourse> targetCourses;

}
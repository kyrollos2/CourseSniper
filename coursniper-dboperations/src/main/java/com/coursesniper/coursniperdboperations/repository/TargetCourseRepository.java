package com.coursesniper.coursniperdboperations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursesniper.coursniperdboperations.entity.TargetCourse;

@Repository
public interface TargetCourseRepository extends JpaRepository<TargetCourse, Integer> {
}

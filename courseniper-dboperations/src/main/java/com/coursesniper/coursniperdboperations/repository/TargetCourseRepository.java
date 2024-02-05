package com.coursesniper.coursniperdboperations.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursesniper.coursniperdboperations.entity.TargetCourse;

@Repository
public interface TargetCourseRepository extends JpaRepository<TargetCourse, Integer> {
    List<TargetCourse> findByStudentStudentId(Integer studentId);

    List<TargetCourse> findByCourseId(Integer courseId);

    Optional<TargetCourse> findByStudentStudentIdAndCourseId(Integer studentId, Integer courseId);

    // Additional method to find all target courses for a specific course
    List<TargetCourse> findAllByCourseId(Integer courseId);
}

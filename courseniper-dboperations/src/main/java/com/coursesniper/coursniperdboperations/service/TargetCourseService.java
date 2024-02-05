package com.coursesniper.coursniperdboperations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.TargetCourse;
import com.coursesniper.coursniperdboperations.repository.TargetCourseRepository;

@Service
public class TargetCourseService {

    private final TargetCourseRepository targetCourseRepository;

    @Autowired
    public TargetCourseService(TargetCourseRepository targetCourseRepository) {
        this.targetCourseRepository = targetCourseRepository;
    }

    public List<TargetCourse> findAllByStudentId(Integer studentId) {
        return targetCourseRepository.findByStudentStudentId(studentId);
    }

    public Optional<TargetCourse> addTargetCourse(TargetCourse targetCourse) {
        Optional<TargetCourse> existingTargetCourse = targetCourseRepository.findByStudentStudentIdAndCourseId(
                targetCourse.getStudent().getStudentId(), targetCourse.getCourse().getId());
        if (existingTargetCourse.isEmpty()) {
            return Optional.of(targetCourseRepository.save(targetCourse));
        }
        return Optional.empty(); // Return empty if the target course already exists
    }

    public void removeTargetCourse(Integer targetCourseId) {
        targetCourseRepository.deleteById(targetCourseId);
    }

    public Optional<TargetCourse> findByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        return targetCourseRepository.findByStudentStudentIdAndCourseId(studentId, courseId);
    }

    public List<TargetCourse> findAllByCourseId(Integer courseId) {
        return targetCourseRepository.findAllByCourseId(courseId);
    }
}

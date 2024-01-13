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

    public List<TargetCourse> findAllTargetCourses() {
        return targetCourseRepository.findAll();
    }

    public Optional<TargetCourse> findTargetCourseById(int id) {
        return targetCourseRepository.findById(id);
    }

    public TargetCourse saveTargetCourse(TargetCourse targetCourse) {
        return targetCourseRepository.save(targetCourse);
    }

    public Optional<TargetCourse> updateTargetCourse(int targetCourseId, TargetCourse targetCourseDetails) {
        return targetCourseRepository.findById(targetCourseId)
            .map(existingTargetCourse -> {
                existingTargetCourse.setStudent(targetCourseDetails.getStudent());
                existingTargetCourse.setCourse(targetCourseDetails.getCourse());
                return targetCourseRepository.save(existingTargetCourse);
            });
    }

    public boolean deleteTargetCourse(int targetCourseId) {
        return targetCourseRepository.findById(targetCourseId)
            .map(targetCourse -> {
                targetCourseRepository.delete(targetCourse);
                return true;
            }).orElse(false);
    }

   
    
}

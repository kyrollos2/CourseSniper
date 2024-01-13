package com.coursesniper.coursniperdboperations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.Course;
import com.coursesniper.coursniperdboperations.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> findCourseById(int id) {
        return courseRepository.findById(id);
    }

    public Course saveCourse(Course course) {
        
        return courseRepository.save(course);
    }

    public Optional<Course> updateCourse(int courseId, Course courseDetails) {
        return courseRepository.findById(courseId)
            .map(existingCourse -> {
                
                existingCourse.setSectionName(courseDetails.getSectionName());
                existingCourse.setTitle(courseDetails.getTitle());
                existingCourse.setStartDate(courseDetails.getStartDate());
                existingCourse.setAvailableSeats(courseDetails.getAvailableSeats());
                return courseRepository.save(existingCourse);
            });
    }

    public boolean deleteCourse(int courseId) {
        return courseRepository.findById(courseId)
            .map(course -> {
                courseRepository.delete(course);
                return true;
            }).orElse(false);
    }
}

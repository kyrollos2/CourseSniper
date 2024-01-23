package com.coursesniper.coursniperdboperations.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.Course;
import com.coursesniper.coursniperdboperations.repository.CourseRepository;

import jakarta.transaction.Transactional;

@Service

public class CourseService {
    @Autowired
    private final CourseRepository courseRepository;

    
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAllCourses() { //fetch all courses
        return courseRepository.findAll();
    }

    public Optional<Course> findCourseById(int id) { //fetch course by id
        return courseRepository.findById(id);
    }
   
   // fetch course by title but allow for short spellings
    public List<Course> findCoursesByTitleLike(String search) { 
            return courseRepository.findByTitleContainingIgnoreCase(search);
    }

    //Save a single course
    public Course saveCourse(Course course) {
        
        return courseRepository.save(course);
    }
   //save a series of courses, useful for the data dump
    @Transactional
    public List<Course> saveAllCourses(List<Course> courses) {
        return courseRepository.saveAll(courses);
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

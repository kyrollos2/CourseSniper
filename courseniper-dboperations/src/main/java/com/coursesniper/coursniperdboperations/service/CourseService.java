package com.coursesniper.coursniperdboperations.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.Course;
import com.coursesniper.coursniperdboperations.repository.CourseRepository;

import jakarta.transaction.Transactional;

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

    public List<Course> findCoursesByTitleLike(String search) {
        return courseRepository.findByTitleContainingIgnoreCase(search);
    }

    public Course saveCourse(Course course) {
        return saveOrUpdateCourse(course);
    }

    @Transactional
    public List<Course> saveAllCourses(List<Course> courses) {
        List<Course> savedCourses = new ArrayList<>();
        for (Course course : courses) {
            savedCourses.add(saveOrUpdateCourse(course));
        }
        return savedCourses;
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

    private Course saveOrUpdateCourse(Course course) {
        Optional<Course> existingCourse = courseRepository.findBySectionNameAndStartDate(
                course.getSectionName(), course.getStartDate());

        if (existingCourse.isPresent()) {
            Course updatedCourse = existingCourse.get();
            // update the necessary fields of updatedCourse from course
            // TODO: Set the fields that need to be updated from course to updatedCourse here
            return courseRepository.save(updatedCourse);
        } else {
            return courseRepository.save(course);
        }
    }
}
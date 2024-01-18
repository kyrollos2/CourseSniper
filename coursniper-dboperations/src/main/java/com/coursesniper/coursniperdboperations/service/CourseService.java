package com.coursesniper.coursniperdboperations.service;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.Course;
import com.coursesniper.coursniperdboperations.exception.ApiRequestException;
import com.coursesniper.coursniperdboperations.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service

public class CourseService {
    @Autowired
    private final CourseRepository courseRepository;

    
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
        
        return courseRepository.save(course);
    }

    public  void processJsonFile(String filePath) {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    try {
        Map<Integer, Map<String, Object>> coursesMap = objectMapper.readValue(
            new File(filePath), new TypeReference<Map<Integer, Map<String, Object>>>() {});

        List<Course> courses = new ArrayList<>();
        for (Map<String, Object> courseData : coursesMap.values()) {
            Course course = new Course();

            course.setSectionName((String) courseData.get("section_name"));
            course.setTitle((String) courseData.get("title"));
            String startDateString = (String) courseData.get("start_date");
            try {
                Date startDate = dateFormat.parse(startDateString);
                course.setStartDate(startDate);
            } catch (ParseException e) {
                throw new ApiRequestException("Invalid date format in JSON file for course: " + courseData.get("section_name"));
            }
            try {
                course.setAvailableSeats(Integer.parseInt((String) courseData.get("available_seats")));
            } catch (NumberFormatException e) {
                throw new ApiRequestException("Invalid number format for available seats in JSON file for course: " + courseData.get("section_name"));
            }

            courses.add(course);
        }

        courseRepository.saveAll(courses);

    } catch (IOException e) {
        throw new ApiRequestException("Error reading from JSON file: " + filePath, e);
    } catch (Exception e) {
        throw new ApiRequestException("Unexpected error processing JSON file: " + filePath, e);
    }
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

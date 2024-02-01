package com.coursesniper.coursniperdboperations.service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.Course;
import com.coursesniper.coursniperdboperations.exception.ApiRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CourseFileProcessorService {

    private final CourseService courseService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CourseFileProcessorService(CourseService courseService, ObjectMapper objectMapper) {
        this.courseService = courseService;
        this.objectMapper = objectMapper;
    }

    public void processJsonData(String jsonDataFilePath) {
        try {
            List<Course> courses = convertJsonToCourses(jsonDataFilePath);
            courseService.saveAllCourses(courses); // Save all courses at once
        } catch (JsonProcessingException e) {
            throw new ApiRequestException("Error parsing JSON data", e);
        } catch (DataIntegrityViolationException e) {
            throw new ApiRequestException("Data integrity violation - possible duplicate data", e);
        } catch (IOException e) {
            throw new ApiRequestException("Error reading JSON data", e);
        }
    }

    private List<Course> convertJsonToCourses(String jsonDataFilePath) throws IOException {
        File jsonDataFile = new File(jsonDataFilePath);
        Map<String, Map<String, Object>> coursesMap = objectMapper.readValue(jsonDataFile,
                new TypeReference<Map<String, Map<String, Object>>>() {});

        List<Course> courses = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : coursesMap.entrySet()) {
            Map<String, Object> courseData = entry.getValue();
            Course course = new Course();
            populateCourseData(course, courseData);
            courses.add(course);
        }

        return courses;
    }

    private void populateCourseData(Course course, Map<String, Object> courseData) {
        course.setSectionName((String) courseData.get("section_name"));
        course.setTitle((String) courseData.get("title"));
        
        String startDateString = (String) courseData.get("start_date");
        Date startDate = new Date(startDateString); // Using your existing date parsing logic
        course.setStartDate(startDate);
    
        String faculty = (String) courseData.get("faculty");
        int maxFacultyLength = 255;
        if (faculty != null && faculty.length() > maxFacultyLength) {
            faculty = faculty.substring(0, maxFacultyLength);
        }
        course.setFaculty(faculty);
    
        int availableSeats = Integer.parseInt((String) courseData.get("available_seats"));
        course.setAvailableSeats(availableSeats);
    }
}

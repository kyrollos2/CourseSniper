package com.coursesniper.coursniperdboperations.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.Course;
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
   
    public void processJsonData(String jsonDataFilePath) throws IOException {
        List<Course> courses = convertJsonToCourses(jsonDataFilePath);
        for (Course course : courses) {
            try {
                // Attempt to save each course individually
                courseService.saveCourse(course); // Assuming saveCourse() handles individual Course objects
            } catch (DataIntegrityViolationException e) {
                // Log the error or handle it as needed, then continue to the next record
                System.err.println("Skipping existing or conflicting record: " + course.getTitle());
            } catch (Exception e) {
                // Handle any other exceptions and continue
                System.err.println("An error occurred while saving course: " + course.getTitle() + ", Error: " + e.getMessage());
            }
        }
    }

    private List<Course> convertJsonToCourses(String jsonDataFilePath) throws IOException {
        File jsonDataFile = new File(jsonDataFilePath);
        List<Course> courses = new ArrayList<>();

        // Read the JSON file into a map structure
        Map<String, Object> coursesMap = objectMapper.readValue(jsonDataFile,
                new TypeReference<Map<String, Object>>() {});

        // Iterate over the map entries
        for (Map.Entry<String, Object> entry : coursesMap.entrySet()) {
            // The value is another map structure for the course data
            Map<String, Object> courseData = (Map<String, Object>) entry.getValue();

            // Create a new Course object and set its properties
            Course course = new Course();
            course.setTerm((String) courseData.get("term"));
            course.setSectionName((String) courseData.get("section_name"));
            course.setTitle((String) courseData.get("title"));

            String startDateString = (String) courseData.get("start_date");
            Date startDate = parseDate(startDateString);
            course.setStartDate(startDate);

            course.setFaculty((String) courseData.get("faculty"));

            String availableSeatsString = (String) courseData.get("available_seats");
            int availableSeats = Integer.parseInt(availableSeatsString);
            course.setAvailableSeats(availableSeats);

            // Add the course to the list
            courses.add(course);
        }

        return courses;
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error parsing date: " + dateString, e);
        }
    }
}

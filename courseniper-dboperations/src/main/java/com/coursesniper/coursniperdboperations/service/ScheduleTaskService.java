
package com.coursesniper.coursniperdboperations.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduleTaskService {

    private final CourseFileProcessorService courseFileProcessorService;

    @Autowired
    public ScheduleTaskService(CourseFileProcessorService courseFileProcessorService) {
        this.courseFileProcessorService = courseFileProcessorService;
    }

    @Scheduled(fixedRate = 900000) // Adjust the rate as needed
    public void executeTask() {
        String jsonFilePath = "scripts/courses.json"; // Ensure the path is correct

        try {
            courseFileProcessorService.processJsonData(jsonFilePath);
        } catch (IOException e) {
            // Log the IOException
            System.err.println("IOException while processing JSON data: " + e.getMessage());
        }
        // Catch any other exceptions as necessary
    }
}

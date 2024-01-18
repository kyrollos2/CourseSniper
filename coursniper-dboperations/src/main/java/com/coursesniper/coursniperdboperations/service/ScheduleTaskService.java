package com.coursesniper.coursniperdboperations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Service
@EnableScheduling
public class ScheduleTaskService {
    

 @Autowired
    private PythonRunnerService pythonExecutorService;

    @Autowired
    private CourseService courseService;
    

    @Scheduled(fixedRate =300000 ) // Parameter in seconds to schedule execution
    public void executePythonScriptAndProcessData() {
        
        // Define the path of script to execute, and where to write new file
        String pythonScriptPath = "coursniper-dboperations/src/main/scripts/modularWebScraping.py";
        String jsonFilePath = "coursniper-dboperations/data/courses.json";

        // Execute
        pythonExecutorService.executePythonScript(pythonScriptPath);

        courseService.processJsonFile(jsonFilePath);

        // Additional logic (e.g., checking course availability and notifying students) goes here
    }
}

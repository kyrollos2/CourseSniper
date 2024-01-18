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
    

    @Scheduled(fixedRate =300000 ) 
    public void executePythonScriptAndProcessData() {
        
        String pythonScriptPath = "coursniper-dboperations/src/main/scripts/modularWebScraping.py";
        String jsonFilePath = "coursniper-dboperations/data/courses.json";

        
        pythonExecutorService.executePythonScript(pythonScriptPath);

        courseService.processJsonFile(jsonFilePath);

        
    }
}

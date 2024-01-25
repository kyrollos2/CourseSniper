
package com.coursesniper.coursniperdboperations.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
@EnableScheduling
public class ScheduleTaskService {

    @Autowired
    private CourseFileProcessorService courseFileProcessorService;
/*This automates the running of the json dump, and will automate the excution
of the python script when i have the docker image of it */

    @Scheduled(fixedRate = 90000000) 
    public void executePythonScriptAndProcessData() throws JsonProcessingException, ParseException {
        String jsonFilePath = "courseniper-dboperations/src/main/scripts/courses.json";
        
        courseFileProcessorService.processJsonData(jsonFilePath);
    }
}

package com.coursesniper.coursniperdboperations.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;
@Service
public class PythonRunnerService {



    public void executePythonScript(String scriptPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Execution of Python script failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing Python script: " + scriptPath, e);
        }
    }
}


    




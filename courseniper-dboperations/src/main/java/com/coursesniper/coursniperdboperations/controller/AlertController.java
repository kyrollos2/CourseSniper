package com.coursesniper.coursniperdboperations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coursesniper.coursniperdboperations.entity.Alert;
import com.coursesniper.coursniperdboperations.service.AlertService;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public ResponseEntity<Alert> createAlert(@RequestBody Alert alert) {
        Alert createdAlert = alertService.createAlert(alert);
        return ResponseEntity.ok(createdAlert);
    }

    

    

    @PutMapping("/{alertId}/status/{status}")
    public ResponseEntity<Alert> updateAlertStatus(@PathVariable int alertId, @PathVariable Alert.AlertStatus status) {
        Alert updatedAlert = alertService.updateAlertStatus(alertId, status);
        return ResponseEntity.ok(updatedAlert);
    }
}

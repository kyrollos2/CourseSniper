package com.coursesniper.coursniperdboperations.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursesniper.coursniperdboperations.entity.Alert;
import com.coursesniper.coursniperdboperations.repository.AlertRepository;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Alert createAlert(Alert alert) {
        alert.setCreated(new Date());
        return alertRepository.save(alert);
    }

    

    

    public Alert updateAlertStatus(int alertId, Alert.AlertStatus status) {
        return alertRepository.findById(alertId).map(alert -> {
            alert.setStatus(status);
            if (status == Alert.AlertStatus.SENT) {
                alert.setSent(new Date());
            }
            return alertRepository.save(alert);
        }).orElseThrow(() -> new RuntimeException("Alert not found with id " + alertId));
    }
}

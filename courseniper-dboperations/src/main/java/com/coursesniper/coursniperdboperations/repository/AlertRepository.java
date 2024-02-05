package com.coursesniper.coursniperdboperations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coursesniper.coursniperdboperations.entity.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {
   
    List<Alert> findByStatus(Alert.AlertStatus status);
}

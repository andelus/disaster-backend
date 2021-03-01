package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.entities.RescueHelper;


public interface EmailService {

    void sendPasswordResetEmail(RescueHelper userDto, String token);
}

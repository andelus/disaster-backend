package com.example.backend_disaster_project.disasterbackend.controllers;

import com.example.backend_disaster_project.disasterbackend.entities.*;
import com.example.backend_disaster_project.disasterbackend.repositories.RescueHelperRepository;
import com.example.backend_disaster_project.disasterbackend.service.EmailService;
import com.example.backend_disaster_project.disasterbackend.service.InvalidTokenException;
import com.example.backend_disaster_project.disasterbackend.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ForgotPasswordController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUserDetailsService authService;

    @Autowired
    private RescueHelperRepository userRepo;

    @PostMapping(path = "/request-password-reset")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody @Valid RequestPasswordResetModel passwordResetRequestModel, JwtRequest authenticationRequest) {
        // get user details
        RescueHelper userDto = userRepo.findByEmail(passwordResetRequestModel.getEmail());
        // get token

        String token = authService.getRequestPasswordToken(userDto);

        // save token
        authService.saveRequestPasswordToken(token, userDto);
        System.out.println(token);
        // send email
        emailService.sendPasswordResetEmail(userDto, token);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/password-reset")
    public ResponseEntity resetPassword(@RequestBody @Valid ResetPasswordModel passwordResetModel) throws InvalidTokenException {

        authService.resetPassword(passwordResetModel.getToken(),
                passwordResetModel.getPassword());
        return ResponseEntity.ok(passwordResetModel.getToken());
    }


}


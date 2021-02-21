package com.example.backend_disaster_project.disasterbackend.controllers;

import com.example.backend_disaster_project.disasterbackend.entities.JwtRequest;
import com.example.backend_disaster_project.disasterbackend.entities.RequestPasswordResetModel;
import com.example.backend_disaster_project.disasterbackend.entities.ResetPasswordModel;
import com.example.backend_disaster_project.disasterbackend.entities.VictimDB;
import com.example.backend_disaster_project.disasterbackend.repositories.VictimRepository;
import com.example.backend_disaster_project.disasterbackend.service.EmailService;
import com.example.backend_disaster_project.disasterbackend.service.InvalidTokenException;
import com.example.backend_disaster_project.disasterbackend.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class ForgotPasswordController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUserDetailsService authService;

    @Autowired
    private VictimRepository userRepo;

    @PostMapping(path = "/request-password-reset")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody @Valid RequestPasswordResetModel passwordResetRequestModel, JwtRequest authenticationRequest) {
        // get user details
        VictimDB userDto = authService.getUserByEmail(passwordResetRequestModel.getEmail());
        // get token
        String token = authService.getRequestPasswordToken(userDto,authenticationRequest);
        // save token
        authService.saveRequestPasswordToken(token, userDto);
        // send email
        emailService.sendPasswordResetEmail(userDto, token);

//        log.info("RequestPasswordReset -- request.pwd.rst -- userId={}", userDto.getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/password-reset")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordModel passwordResetModel) throws InvalidTokenException {

        authService.resetPassword(passwordResetModel.getToken(),
                passwordResetModel.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

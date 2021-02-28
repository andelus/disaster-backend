package com.example.backend_disaster_project.disasterbackend.controllers;


import com.example.backend_disaster_project.disasterbackend.entities.RescueHelper;
import com.example.backend_disaster_project.disasterbackend.repositories.RescueHelperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins="http://localhost:3000")
public class RescueHelperController {
    @Autowired
    private RescueHelperRepository rescueHelperRepository;

    @PostMapping("/rescueHelper")
    public RescueHelper createSOS(@RequestBody RescueHelper rescueHelper) {
        return rescueHelperRepository.save(rescueHelper);
    }


}

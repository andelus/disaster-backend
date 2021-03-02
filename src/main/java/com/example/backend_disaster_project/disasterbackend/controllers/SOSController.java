package com.example.backend_disaster_project.disasterbackend.controllers;


import com.example.backend_disaster_project.disasterbackend.entities.SOS;
import com.example.backend_disaster_project.disasterbackend.repositories.SosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:3000")
public class SOSController {

    @Autowired
    private SosRepository sosRepository;

    @GetMapping("/sos")
    public Iterable<SOS> getSOS() {
        return this.sosRepository.findAll();
    }

    @PostMapping("/sos")
    public SOS createSOS(@RequestBody SOS sos) {
        return sosRepository.save(sos);
    }

    @DeleteMapping("/deleteSos")
    public void delete() {
        sosRepository.deleteAll();
    }

    @DeleteMapping("/sos/delete/{latitude}")
    public void delete(@PathVariable Double latitude) {
        SOS sos = sosRepository.findByLatitude(latitude);
        sosRepository.delete(sos);
    }
    @PutMapping("/sos/{sosId}")
    public ResponseEntity<Boolean> setTimer(@PathVariable long sosId, @RequestBody int Timer) throws Exception {
        SOS sos = sosRepository.findById(sosId).get();
        sos.setTimer(Timer);
        sosRepository.save(sos);
        return ResponseEntity.ok(true);
    }
    @GetMapping("/sos/timer/{sosId}")
    public ResponseEntity<Integer> getSOS(@PathVariable long sosId){
        return ResponseEntity.ok(this.sosRepository.findById(sosId).get().getTimer());
    }

}

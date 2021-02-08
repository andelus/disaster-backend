package com.example.backend_disaster_project.disasterbackend.controllers;


import com.example.backend_disaster_project.disasterbackend.entities.SOS;
import com.example.backend_disaster_project.disasterbackend.repositories.SosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins="http://localhost:3000")
public class SOSController {

	@Autowired
	private SosRepository sosRepository;
	
	@GetMapping("sos")
	public Iterable<SOS> getSOS(){
		return this.sosRepository.findAll();
	}

	@PostMapping("/sos")
	public SOS createSOS(@RequestBody SOS sos) {
		return sosRepository.save(sos);
	}

	@GetMapping("/sos/{id}")
	public ResponseEntity<SOS> getSosById(@PathVariable long id){
		SOS sos = sosRepository.findById(id).orElseThrow(null);
		return  ResponseEntity.ok(sos);
	}

	@PutMapping("/sos/{id}")
	public ResponseEntity<SOS> updateSosMessage(@PathVariable Long id, @RequestBody SOS sosDetails){
		SOS sos = sosRepository.findById(id).orElseThrow(null);
		sos.setMessageToSOS(sosDetails.getMessageToSOS());

		SOS updatedSOS = sosRepository.save(sos);
		return ResponseEntity.ok(updatedSOS);
	}
	
}

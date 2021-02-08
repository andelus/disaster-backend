package com.example.backend_disaster_project.disasterbackend.controllers;


import com.example.backend_disaster_project.disasterbackend.entities.Victim;
import com.example.backend_disaster_project.disasterbackend.repositories.VictimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/")
@CrossOrigin(origins="http://localhost:3000")
public class VictimController {

	@Autowired
	private VictimRepository victimRepository;

	@GetMapping("/victims")
	public Iterable<Victim> getVictims() {
		return this.victimRepository.findAll();
	}
	
	@PostMapping("/victims")
	public Victim createVictim(@RequestBody Victim victim) {
		return this.victimRepository.save(victim);
	}

	@GetMapping("/victims/{id}")
	public ResponseEntity<Victim> getVictimById(@PathVariable long id){
		Victim victim = victimRepository.findById(id).orElseThrow(null);
		return  ResponseEntity.ok(victim);
	}
	@PutMapping("/victims/{id}")
	public ResponseEntity<Victim> updateVictimMessage(@PathVariable Long id, @RequestBody Victim victimDetails){
		Victim victim = victimRepository.findById(id).orElseThrow(null);
		victim.setMessageToVictim(victimDetails.getMessageToVictim());

		Victim updatedVictim = victimRepository.save(victim);
		return ResponseEntity.ok(updatedVictim);
	}


}





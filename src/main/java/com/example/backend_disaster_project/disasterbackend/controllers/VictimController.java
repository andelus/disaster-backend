package com.example.backend_disaster_project.disasterbackend.controllers;


import com.example.backend_disaster_project.disasterbackend.entities.Victim;
import com.example.backend_disaster_project.disasterbackend.entities.VictimDB;
import com.example.backend_disaster_project.disasterbackend.repositories.VictimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@GetMapping("/message/{username}")
	public String getMessageByUsername(@PathVariable String username){
		Victim victim = victimRepository.findByUsername(username);
		return  victim.getMessageToVictim();
	}
	@PutMapping("/victims/{username}")
	public ResponseEntity<Victim> updateVictimMessage(@PathVariable String username, @RequestBody Victim victimDetails){
		Victim victim = victimRepository.findByUsername(username);
		victim.setMessageToVictim(victimDetails.getMessageToVictim());

		Victim updatedVictim = victimRepository.save(victim);
		return ResponseEntity.ok(updatedVictim);
	}
	@PutMapping("/victimsMessage/{username}")
	public ResponseEntity<Victim> updateFromVictimMessage(@PathVariable String username, @RequestBody Victim victimDetails){
		Victim victim = victimRepository.findByUsername(username);
		victim.setMessage(victimDetails.getMessage());

		Victim updatedVictim = victimRepository.save(victim);
		return ResponseEntity.ok(updatedVictim);
	}

	@DeleteMapping("/delete/{username}")
	public void delete(@PathVariable String username){
		Victim victim = victimRepository.findByUsername(username);
		victimRepository.delete(victim);
	}
	@PostMapping("/registerVictim")
	public ResponseEntity<?> saveVictim(@RequestBody Victim user) throws Exception {
		if(victimRepository.existsByUsername(user.getUsername())==true){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message");
		}
		return ResponseEntity.ok(victimRepository.save(user));
	}

	@PutMapping("/disaster/{username}")
	public ResponseEntity<Victim> updateDisaster(@PathVariable String username, @RequestBody Victim victimDetails){
		Victim victim = victimRepository.findByUsername(username);
		victim.setDisaster(victimDetails.getDisaster());

		Victim updatedVictim = victimRepository.save(victim);
		return ResponseEntity.ok(updatedVictim);
	}



}





package com.example.backend_disaster_project.disasterbackend.controllers;


import com.example.backend_disaster_project.disasterbackend.config.JwtTokenUtil;
import com.example.backend_disaster_project.disasterbackend.entities.JwtRequest;
import com.example.backend_disaster_project.disasterbackend.entities.JwtResponse;
import com.example.backend_disaster_project.disasterbackend.entities.RescueHelperDB;
import com.example.backend_disaster_project.disasterbackend.entities.VictimDB;
import com.example.backend_disaster_project.disasterbackend.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@PostMapping("/registerRescueHelper")
	public ResponseEntity<?> saveUser(@RequestBody RescueHelperDB user) throws Exception {
		return ResponseEntity.ok(userDetailsService.saveRescueHelper(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
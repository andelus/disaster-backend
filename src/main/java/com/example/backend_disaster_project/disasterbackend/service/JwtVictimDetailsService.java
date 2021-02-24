package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.entities.RescueHelper;
import com.example.backend_disaster_project.disasterbackend.entities.RescueHelperDB;
import com.example.backend_disaster_project.disasterbackend.entities.Victim;
import com.example.backend_disaster_project.disasterbackend.entities.VictimDB;
import com.example.backend_disaster_project.disasterbackend.repositories.RescueHelperRepository;
import com.example.backend_disaster_project.disasterbackend.repositories.VictimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component("quick")
public class JwtVictimDetailsService implements UserDetailsService {


	@Autowired
	private VictimRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Victim user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}


		return new User(user.getUsername(), user.getPassword(),
				new ArrayList<>());

	}

	public Victim saveVictim(VictimDB user) {
		Victim newUser = new Victim();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setAction(user.getAction());
		newUser.setAllergy(user.getAllergy());
		newUser.setBloodType(user.getBloodType());
		newUser.setDescription(user.getDescription());
		newUser.setEmail(user.getEmail());
		newUser.setCity(user.getCity());
		newUser.setDateOfBirth(user.getDateOfBirth());
		newUser.setName(user.getName());
		newUser.setMessage(user.getMessage());
		newUser.setDate(user.getDate());
		newUser.setNrStreet(user.getNrStreet());
		newUser.setStreet(user.getStreet());
		newUser.setType(user.getType());
		newUser.setTel(user.getTel());
		return userDao.save(newUser);
	}
}
package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.entities.RescueHelper;
import com.example.backend_disaster_project.disasterbackend.entities.RescueHelperDB;
import com.example.backend_disaster_project.disasterbackend.entities.Victim;
import com.example.backend_disaster_project.disasterbackend.entities.VictimDB;
import com.example.backend_disaster_project.disasterbackend.repositories.RescueHelperRepository;
import com.example.backend_disaster_project.disasterbackend.repositories.VictimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private RescueHelperRepository userDao;

	@Autowired
	private VictimRepository userDao1;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RescueHelper user = userDao.findByUsername(username);
		Victim user1 = userDao1.findByUsername(username);
		if (user == null && user1 == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		if (user != null && user1 == null){
		return new User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
		}

		return new User(user1.getUsername(), user1.getPassword(),
				new ArrayList<>());

	}


	
	public RescueHelper saveRescueHelper(RescueHelperDB user) {
		RescueHelper newUser = new RescueHelper();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setBirthday(user.getBirthday());
		newUser.setDepartment(user.getDepartment());
		newUser.setAge(user.getAge());
		newUser.setDescription(user.getDescription());
		newUser.setEmail(user.getEmail());
		newUser.setPhoneNumber(user.getPhoneNumber());
		newUser.setProfession(user.getProfession());
		newUser.setName(user.getName());
		return userDao.save(newUser);
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
		newUser.setType(user.getType());
		newUser.setTel(user.getTel());
		return userDao1.save(newUser);
	}
}
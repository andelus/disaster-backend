package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.config.JwtTokenUtil;
import com.example.backend_disaster_project.disasterbackend.entities.*;
import com.example.backend_disaster_project.disasterbackend.repositories.PasswordResetTokenRepository;
import com.example.backend_disaster_project.disasterbackend.repositories.RescueHelperRepository;
import com.example.backend_disaster_project.disasterbackend.repositories.VictimRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
	@Autowired
	private JwtTokenUtil jwtUtils;

	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RescueHelper user = userDao.findByUsername(username);
		Victim user1 = userDao1.findByUsername(username);
		if (user == null ) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		return new User(user.getUsername(), user.getPassword(),
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
		//newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
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
		return userDao1.save(newUser);
	}
	public RescueHelperDB getUserByEmail(String email) {

		RescueHelper rescueHelper = userDao.findByEmail(email);
		System.out.print(rescueHelper.getEmail());
		RescueHelperDB rescueDB = new RescueHelperDB();
		rescueDB.setEmail(rescueHelper.getEmail());
		rescueDB.setPassword(rescueHelper.getPassword());
		rescueDB.setUsername(rescueHelper.getUsername());
		rescueDB.setDescription(rescueHelper.getDescription());
		rescueDB.setEmail(rescueHelper.getEmail());
		rescueDB.setName(rescueHelper.getName());
		System.out.print(rescueDB);
		return rescueDB;
	}



	public String getRequestPasswordToken(RescueHelper userDto) {
		// generate password reset token
		return jwtUtils.generateToken1(userDto.getId() + userDto.getEmail());
	}

	// save password reset token to 'PasswordResetToken' database

	public void saveRequestPasswordToken(String token, RescueHelper userDto) {
		// map to UserEntity
		//Victim userEntity = mapper.map(userDto, Victim.class);
		RescueHelper newUser = new RescueHelper();
		newUser.setUsername(userDto.getUsername());
		newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));

		newUser.setDescription(userDto.getDescription());
		newUser.setEmail(userDto.getEmail());

		newUser.setName(userDto.getName());


		// save token to repository
		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserDetails(userDto);
		passwordTokenRepository.save(passwordResetTokenEntity);
	}

	// update password

	public void resetPassword(String token, String password) throws InvalidTokenException {
		if (jwtUtils.isTokenExpired(token)) {
			throw new InvalidTokenException("reset.password.token.expired", "token=" + token);
		}

		PasswordResetTokenEntity passwordResetTokenEntity = passwordTokenRepository.findByToken(token)
				.orElseThrow(() -> {
					try {
						throw new InvalidTokenException("invalid.token", "token=" + token);
					} catch (InvalidTokenException e) {
						e.printStackTrace();
					}
					return null;
				});

		// encode new password
		String encodedPassword = bcryptEncoder.encode(password);

		// Update User password in database
		RescueHelper userEntity = passwordResetTokenEntity.getUserDetails();
		System.out.println(userEntity);
		userEntity.setPassword(encodedPassword);
		userDao.save(userEntity);

		// Remove Password Reset token from database
		//	passwordTokenRepository.delete(passwordResetTokenEntity);
//
//		log.info("PasswordReset -- pwd.rst -- userId={}", userEntity.getUserId());
	}
}

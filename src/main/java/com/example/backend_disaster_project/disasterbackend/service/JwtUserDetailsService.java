package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.config.JwtTokenUtil;
import com.example.backend_disaster_project.disasterbackend.entities.*;
import com.example.backend_disaster_project.disasterbackend.repositories.PasswordResetTokenRepository;
import com.example.backend_disaster_project.disasterbackend.repositories.RescueHelperRepository;
import com.example.backend_disaster_project.disasterbackend.repositories.VictimRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
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

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;


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

	public void verifyEmailToken(String token) throws InvalidTokenException {
		// Find user by token
		Victim userEntity = userDao1.findUserByEmailVerificationToken(token);
		// check for token expiration
		if (jwtUtils.isTokenExpired(token))
			throw new InvalidTokenException("email.verification.token.expired", "token=" + token);

		userEntity.setEmailVerificationToken(null);

//		log.info("UserVerify -- user.verify -- userId={}", userEntity.getUserId());

		userDao1.save(userEntity);
	}


	public VictimDB getUserByEmail(String email) {
		return userDao1.findByEmail(email)
				.map(userEntity -> mapper.map(userEntity, VictimDB.class))
				.orElseThrow(() -> {
					throw new ResourceNotFoundException("email.not.found");
				});
	}


	public String getRequestPasswordToken(VictimDB userDto,JwtRequest authenticationRequest) {
		// generate password reset token
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		return jwtUtils.generateToken(userDetails);
	}

	// save password reset token to 'PasswordResetToken' database

	public void saveRequestPasswordToken(String token, VictimDB userDto) {
		// map to UserEntity
		Victim userEntity = mapper.map(userDto, Victim.class);
		// save token to repository
		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserDetails(userEntity);
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
		Victim userEntity = passwordResetTokenEntity.getUserDetails();
		userEntity.setPassword(encodedPassword);
		userDao1.save(userEntity);

		// Remove Password Reset token from database
		passwordTokenRepository.delete(passwordResetTokenEntity);
//
//		log.info("PasswordReset -- pwd.rst -- userId={}", userEntity.getUserId());
	}

//	public void updateResetVictimPassword(String token,String email) throws VictimNotFoundException {
//		Victim victim = userDao1.findByEmail(email);
//		if(victim != null){
//			victim.setResetPasswordVictimToken((token));
//			userDao1.save(victim);
//		} else{
//			throw new VictimNotFoundException("Could not find any victim with this username");
//		}
//	}
//
//	public Victim getVictim(String resetPasswordToken){
//		return userDao1.findByResetPasswordVictimToken(resetPasswordToken);
//	}
//
//	public void updateVictimPassword(Victim victim, String newPassword){
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String encodedPassword = passwordEncoder.encode(newPassword);
//		victim.setPassword(encodedPassword);
//		victim.setResetPasswordVictimToken(null);
//
//		userDao1.save(victim);
//	}
//
//	public void updateResetRescueHelperPassword(String token,String email) throws RescueHelperNotFoundException {
//		RescueHelper rescueHelper = userDao.findByEmail(email);
//		if(rescueHelper != null){
//			rescueHelper.setResetPasswordRescueHelperToken(token);
//			userDao.save(rescueHelper);
//		} else{
//			throw new RescueHelperNotFoundException("Could not find any rescue helper with this username");
//		}
//	}
//
//	public RescueHelper getRescueHelper(String resetPasswordToken){
//		return userDao.findByResetPasswordRescueHelperToken(resetPasswordToken);
//	}
//
//	public void updateRescueHelperPassword(RescueHelper rescueHelper, String newPassword){
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String encodedPassword = passwordEncoder.encode(newPassword);
//		rescueHelper.setPassword(encodedPassword);
//		rescueHelper.setResetPasswordRescueHelperToken(null);
//
//		userDao.save(rescueHelper);
//	}


}
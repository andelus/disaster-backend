package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.config.JwtTokenUtil;
import com.example.backend_disaster_project.disasterbackend.entities.*;
import com.example.backend_disaster_project.disasterbackend.repositories.PasswordResetTokenRepository;
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
    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RescueHelper user = userDao.findByUsername(username);
        if (user == null) {
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

//	public RescueHelperDB getUserByEmail(String email) {
//
//		RescueHelper rescueHelper = userDao.findByEmail(email);
//		System.out.print(rescueHelper.getEmail());
//		RescueHelperDB rescueDB = new RescueHelperDB();
//		rescueDB.setEmail(rescueHelper.getEmail());
//		rescueDB.setPassword(rescueHelper.getPassword());
//		rescueDB.setUsername(rescueHelper.getUsername());
//		rescueDB.setDescription(rescueHelper.getDescription());
//		rescueDB.setEmail(rescueHelper.getEmail());
//		rescueDB.setName(rescueHelper.getName());
//		System.out.print(rescueDB);
//		return rescueDB;
//	}


    public String getRequestPasswordToken(RescueHelper userDto) {
        return jwtUtils.generateTokenOtherOption(userDto.getId() + userDto.getEmail());
    }


    public void saveRequestPasswordToken(String token, RescueHelper userDto) {

        RescueHelper newUser = new RescueHelper();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));

        newUser.setDescription(userDto.getDescription());
        newUser.setEmail(userDto.getEmail());

        newUser.setName(userDto.getName());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(userDto);
        passwordTokenRepository.save(passwordResetTokenEntity);
    }


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


        String encodedPassword = bcryptEncoder.encode(password);

        RescueHelper userEntity = passwordResetTokenEntity.getUserDetails();
        System.out.println(userEntity);
        userEntity.setPassword(encodedPassword);
        userDao.save(userEntity);

        // Remove Password Reset token from database
        //	passwordTokenRepository.delete(passwordResetTokenEntity);

    }
}

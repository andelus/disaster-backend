package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.entities.RescueHelper;
import com.example.backend_disaster_project.disasterbackend.entities.RescueHelperDB;
import com.example.backend_disaster_project.disasterbackend.repositories.RescueHelperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component("slow")
@Primary
public class JwtRescueHelperDetailsService implements UserDetailsService {

    @Autowired
    private RescueHelperRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

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
}
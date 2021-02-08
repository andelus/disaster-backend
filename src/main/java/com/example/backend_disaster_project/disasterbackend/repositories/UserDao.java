package com.example.backend_disaster_project.disasterbackend.repositories;



import com.example.backend_disaster_project.disasterbackend.entities.DAOUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<DAOUser,Long> {
	
	DAOUser findByUsername(String username);
	
}
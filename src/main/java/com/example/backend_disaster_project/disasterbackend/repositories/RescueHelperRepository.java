package com.example.backend_disaster_project.disasterbackend.repositories;

import com.example.backend_disaster_project.disasterbackend.entities.RescueHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RescueHelperRepository extends JpaRepository<RescueHelper, Long> {

}

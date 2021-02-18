package com.example.backend_disaster_project.disasterbackend.repositories;


import com.example.backend_disaster_project.disasterbackend.entities.SOS;
import com.example.backend_disaster_project.disasterbackend.entities.Victim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SosRepository extends JpaRepository<SOS, Long> {
    SOS findByIpAddress(long ipAddress);
}

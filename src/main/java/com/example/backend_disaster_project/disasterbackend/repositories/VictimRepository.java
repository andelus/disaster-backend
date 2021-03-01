package com.example.backend_disaster_project.disasterbackend.repositories;


import com.example.backend_disaster_project.disasterbackend.entities.Victim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VictimRepository extends JpaRepository<Victim, Long> {
    Victim findByUsername(String username);

    boolean existsByUsername(String username);
}

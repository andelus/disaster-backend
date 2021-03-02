package com.example.backend_disaster_project.disasterbackend.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {

    private static final long serialVersionUID = 2035000457464505430L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "password_reset_sequence_generator")
    @SequenceGenerator(name = "password_reset_sequence_generator", sequenceName = "password_reset_sequence")
    @Column(updatable = false, nullable = false)
    private long id;

    @Column(nullable = false)
    private String token;


    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private RescueHelper userDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RescueHelper getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(RescueHelper userDetails) {
        this.userDetails = userDetails;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}


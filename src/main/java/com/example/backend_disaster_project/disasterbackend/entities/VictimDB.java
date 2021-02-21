package com.example.backend_disaster_project.disasterbackend.entities;


import java.util.Date;

public class VictimDB {

    public VictimDB(String username,String password,String city, int nrStreet, String street, String name, String email, long tel, String action,
                  String message, Date date, String messageToVictim,String bloodType,String allergy,String description,String dateOfBirth,String type) {
        super();
        this.password = password;
        this.username = username;
        this.allergy = allergy;
        this.bloodType = bloodType;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.city = city;
        this.nrStreet = nrStreet;
        this.street = street;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.action = action;
        this.message = message;
        this.date = date;
        this.messageToVictim = messageToVictim;
        this.type = type;

    }


    private String userId;
    private String username;
    private String password;
    private String type;
    private String city;
    private int nrStreet;
    private String street;
    private String name;
    private String email;
    private long tel;
    private String action;
    private String message;
    private String messageToVictim;
    private Date date;
    private String bloodType;
    private String allergy;
    private String description;
    private String dateOfBirth;
    private String emailVerificationToken;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNrStreet() {
        return nrStreet;
    }

    public void setNrStreet(int nrStreet) {
        this.nrStreet = nrStreet;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessageToVictim() {
        return messageToVictim;
    }

    public void setMessageToVictim(String messageToVictim) {
        this.messageToVictim = messageToVictim;
    }
}

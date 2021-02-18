package com.example.backend_disaster_project.disasterbackend.entities;

import javax.persistence.*;

@Entity
@Table(name = "rescueHelpers")
public class RescueHelper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public RescueHelper(String name, String email, String birthday, int age, String department, String description, long phoneNumber, String profession) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.age = age;
        this.department = department;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
    }

    private String email;
    private String birthday;
    private int age;
    private String department;
    private String description;
    private long phoneNumber;
    private String profession;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}

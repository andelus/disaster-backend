package com.example.backend_disaster_project.disasterbackend.entities;

import javax.persistence.*;

@Entity
@Table(name = "sos")

public class SOS {

    public SOS(double latitude, double longtitude, String date, String type, int timer) {
        super();
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.dateOfSos = date;
        this.type = type;
        this.timer = timer;
    }

    public SOS() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double latitude;
    private double longtitude;
    private String type;
    private String dateOfSos;



    private int timer;

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getDate() {
        return dateOfSos;
    }

    public void setDate(String date) {
        this.dateOfSos = date;
    }

}

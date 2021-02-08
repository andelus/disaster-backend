package com.example.backend_disaster_project.disasterbackend.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sos")

public class SOS {

	public SOS(double latitude, double longtitude, long ipAddress, String message, String messageToSOS, Date date) {
		super();
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.ipAddress = ipAddress;
		this.message = message;
		this.messageToSOS = messageToSOS;
		this.date = date;
	}

	public SOS() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private double latitude;
	private double longtitude;
	private long ipAddress;
	private String message;



	private String messageToSOS;
	private Date date;

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

	public long getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(long ipAddress) {
		this.ipAddress = ipAddress;
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

	public String getMessageToSOS() {
		return messageToSOS;
	}

	public void setMessageToSOS(String messageToSOS) {
		this.messageToSOS = messageToSOS;
	}
}

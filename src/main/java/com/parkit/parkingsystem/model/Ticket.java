package com.parkit.parkingsystem.model;

import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonFormat;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date inTime; // = new Date();
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date outTime; // = new Date();
    private boolean recurringUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return new ParkingSpot(this.parkingSpot.getId(), this.parkingSpot.getParkingType(), this.parkingSpot.isAvailable());
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        return new Date(this.inTime.getTime());
    	//return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        //return new Date(this.outTime.getTime());
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }
    
    public boolean getRecurringUser() {
        return recurringUser;
    }

    public void setRecurringUser(boolean recurringUser) {
        this.recurringUser = recurringUser;
    }
}

package Parkeersimulator.Models;

import java.awt.Color;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private static final int basicFee = 250;
    private static final int reservationFee = 150;
    private int totalFee;
    private boolean isPaying;
    private boolean hasToPay;

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    
    // sets fee to pay
    public void setTotalFee(int totalFee) {
    	this.totalFee = totalFee;
    }
    
    //returns total fee
    public int getTotalFee() {
    	return this.totalFee;
    }
    
    // returns basic fee
    public int getBasicFee() {
    	return basicFee;
    }
    
    public int getReservationFee() {
    	return reservationFee;
    }
    
    public boolean getIsPaying() {
        return this.isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return this.hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    /**
     * Tick increases the time until cars likes to leave.
     */
    public void tick() {
        this.minutesLeft--;
    }

    public abstract Color getColor();
}
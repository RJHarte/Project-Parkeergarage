package Parkeersimulator.Models;

import java.awt.Color;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private static final double basicFee = 2.5;
    private static final double reservationFee = 1.5;
    private double totalFee;
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
    public void setTotalFee(double totalFee) {
    	this.totalFee = totalFee;
    }
    
    //returns total fee
    public double getTotalFee() {
    	return this.totalFee;
    }
    
    // returns basic fee
    public double getBasicFee() {
    	return basicFee;
    }
    
    public double getReservationFee() {
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
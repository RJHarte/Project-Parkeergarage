package Parkeersimulator.Models;

import java.awt.Color;
import java.util.Random;

/**
 *
 */
public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.blue;

    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + (random.nextFloat() * 3*60));
    	int stayHours = stayMinutes / 60;
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    @Override
	public Color getColor(){
    	return COLOR;
    }
}

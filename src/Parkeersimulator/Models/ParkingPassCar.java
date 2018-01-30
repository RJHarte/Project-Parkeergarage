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
    	int stayMinutes = (int) (45 + (random.nextFloat() * 4*60));
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    @Override
	public Color getColor(){
    	return COLOR;
    }
}

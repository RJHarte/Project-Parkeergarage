package Parkeersimulator.Models;

import java.awt.Color;
import java.util.Random;

/**
 *
 */
public class ReservedCar extends Car {
	private static final Color COLOR=Color.orange;

    public ReservedCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + (random.nextFloat() * 3*60));
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    @Override
	public Color getColor(){
    	return COLOR;
    }
}

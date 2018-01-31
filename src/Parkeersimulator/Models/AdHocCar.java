package Parkeersimulator.Models;

import java.awt.Color;
import java.util.Random;

/**
 * Ad Hoc cars.
 */
public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;


    public AdHocCar() {
    	Random random = new Random();
    	// Stay there between 15m and 3h15m
    	int stayMinutes = (int) (15 + (random.nextFloat() * 4*60));
    	int stayHours = stayMinutes / 60;
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setTotalFee(stayHours * this.getBasicFee() + this.getBasicFee());
    }

    @Override
	public Color getColor(){
    	return COLOR;
    }

	@Override
	public int maxMinutesToWaitEntrance() {
		Random random = new Random();
		return (int) (15 + (random.nextFloat() * 45));
	}
}

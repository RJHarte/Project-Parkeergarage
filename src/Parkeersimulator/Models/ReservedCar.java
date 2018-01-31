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
    	int stayHours = stayMinutes / 60;
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setTotalFee(stayHours *  this.getBasicFee() + this.getBasicFee() + this.getReservationFee() );
    }

    @Override
	public Color getColor(){
    	return COLOR;
    }

	@Override
	public int maxMinutesToWaitEntrance() {
		Random random = new Random();
		// I'm not going to wait too long, because I have g*dv*r de g*dv*r toch gereserveerd?
		return (int) (10 + (random.nextFloat() * 20));
	}
}

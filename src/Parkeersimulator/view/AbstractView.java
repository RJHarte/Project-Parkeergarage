package Parkeersimulator.view;

import javax.swing.JPanel;

import Parkeersimulator.Models.ParkingLot;

public abstract class AbstractView extends JPanel {
	private static final long serialVersionUID = -2767764579227738552L;
	protected ParkingLot parkingLot;

	public AbstractView(ParkingLot model) {
		this.parkingLot = model;
		//model.addView(this);
	}

	public ParkingLot getParkingLot() {
		return this.parkingLot;
	}

	public void updateView() {
		repaint();
	}
}
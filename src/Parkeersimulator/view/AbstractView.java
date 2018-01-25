package Parkeersimulator.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import Parkeersimulator.Models.ParkingLot;

public abstract class AbstractView extends JPanel {
	private static final long serialVersionUID = -2767764579227738552L;
	protected ParkingLot parkingLot;

	public AbstractView(ParkingLot model) {
		this.parkingLot = model;
		this.setPreferredSize(new Dimension(500, 800));
		//model.addView(this);
	}

	public ParkingLot getParkingLot() {
		return this.parkingLot;
	}

	public void updateView() {
		System.out.println("In AbstractView.updateView");
		this.repaint();
	}
}
package Parkeersimulator.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import Parkeersimulator.Models.ParkingLot;
import Parkeersimulator.Models.Simulator;

public abstract class AbstractView extends JPanel {
	private static final long serialVersionUID = -2767764579227738552L;
	protected ParkingLot parkingLot;

	protected int width;
	protected int height;

	public AbstractView(ParkingLot model, int width, int height) {
		this.parkingLot = model;
		this.height = height;
		this.width = width;

		this.setSize(width, height);

		this.setPreferredSize(new Dimension(this.width, this.height));

		this.parkingLot.addView(this);
	}

	public ParkingLot getParkingLot() {
		return this.parkingLot;
	}

	public void updateView() {
		this.repaint();
	}
}

package Parkeersimulator.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import Parkeersimulator.Models.Simulator;

public abstract class AbstractView extends JPanel {
	private static final long serialVersionUID = -2767764579227738552L;
	protected int width;
	protected int height;
	protected Dimension size;
	protected Simulator simulator;

	public AbstractView(Simulator simulator, int width, int height) {
    	//System.out.println("hey hier AbstractView: " + simulator);

		this.simulator = simulator;
		this.height = height;
		this.width = width;

		this.size = new Dimension(width, height);

		this.setSize(width, height);

		this.setPreferredSize(new Dimension(this.width, this.height));

		//System.out.println("hoi AbstractView wil graag je parkinglot: " + this.simulator.getParkingLot());

		this.simulator.getParkingLot().addView(this);
	}

	public void updateView() {
		this.repaint();
	}

	public Simulator getSimulator() {
		return this.simulator;
	}
}

package Parkeersimulator;

import Parkeersimulator.Models.Simulator;

import java.awt.Color;

import javax.swing.JFrame;

//import Parkeersimulator.Models.AdHocCar;
//import Parkeersimulator.Models.Car;
//import Parkeersimulator.Models.CarQueue;
//import Parkeersimulator.Models.Location;
//import Parkeersimulator.Models.ParkingLot;
//import Parkeersimulator.Models.ParkingPassCar;
//import Parkeersimulator.view.AbstractView;
import Parkeersimulator.view.CarParkView;

public class ParkingSimulator {
	private JFrame screen;
	private CarParkView carParkView;
	private Simulator Simulator;

	/**
	 * Constructor for the class.
	 */
	public ParkingSimulator() {
		// Create components.
		Simulator = new Simulator();
		//carParkView = new CarParkView(xxxxxx, 800, 400);

		// Setup the screen.
		screen = new JFrame("Parking Simulator");
		screen.setSize(1000, 825);
		//screen.setResizable(false);
		screen.setLayout(null);
		screen.getContentPane().setBackground(new Color(0xee, 0xee, 0xee));
		screen.getContentPane().add(carParkView);

		carParkView.setBounds(10, 0, carParkView.getWidth(), carParkView.getHeight());

		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setVisible(true);
		
	//public static void main(String[] args) {
		//System.out.println("Simulator Initiated");
		//Simulator sim = new Simulator();
		//sim.run(0);
	}
	

		// Start the simulation.
		// model.start();
}
package Parkeersimulator;

import java.awt.Color;

import javax.swing.JFrame;

import Parkeersimulator.Models.ParkingLot;
import Parkeersimulator.Models.Simulator;
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
		ParkingLot parkingLot = new ParkingLot(3, 6, 30);

		Simulator model = new Simulator(parkingLot);

		CarParkView carParkView = new CarParkView(parkingLot/*, 800, 400*/);

		// Setup the screen.
		screen = new JFrame("Parking Simulator");
		screen.setSize(1000, 825);
		//screen.setResizable(false);
		screen.setLayout(null);
		screen.getContentPane().setBackground(new Color(0xee, 0xee, 0xee));

		screen.getContentPane().add(carParkView);

		System.out.printf("in Constructor: %d %d\n", carParkView.getWidth(), carParkView.getHeight());

		System.out.println(carParkView.getWidth());
		System.out.println(carParkView.getHeight());

		carParkView.setBounds(0, 0, carParkView.getWidth(), carParkView.getHeight());

		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setVisible(true);

		model.run(0);
	}
}
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

		CarParkView carParkView = new CarParkView(parkingLot, 800, 400);
		//carParkView.setBounds(0, 0, carParkView.getWidth(), carParkView.getHeight());

		//OccupationPieChartView occupationPieChartView = new OccupationPieChartView(parkingLot, 500, 500);

		// Setup the screen.
		screen = new JFrame("Parking Simulator");
		screen.setSize(1000, 825);
		screen.setResizable(true);
		screen.setLayout(null);
		screen.setVisible(true);
		screen.getContentPane().setBackground(new Color(0xee, 0xee, 0xee));

		screen.getContentPane().add(carParkView);
		//screen.getContentPane().add(occupationPieChartView);

		carParkView.setBounds(0, 0, carParkView.getWidth(), carParkView.getHeight());
		//occupationPieChartView.setBounds(0, carParkView.getHeight(), 250, 250);


		System.out.printf("in Constructor: %d %d\n", carParkView.getWidth(), carParkView.getHeight());

		System.out.println(carParkView.getWidth());
		System.out.println(carParkView.getHeight());

		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		model.run(0);
	}
}

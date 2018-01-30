package Parkeersimulator;

import java.awt.Color;

import javax.swing.JFrame;

import Parkeersimulator.Models.ParkingLot;
import Parkeersimulator.Models.Simulator;
import Parkeersimulator.controls.Controller;
//import Parkeersimulator.Models.AdHocCar;
//import Parkeersimulator.Models.Car;
//import Parkeersimulator.Models.CarQueue;
//import Parkeersimulator.Models.Location;
//import Parkeersimulator.Models.ParkingLot;
//import Parkeersimulator.Models.ParkingPassCar;
//import Parkeersimulator.view.AbstractView;
import Parkeersimulator.view.CarParkView;
import Parkeersimulator.view.OccupationPieChartView;
import Parkeersimulator.view.TextView;

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

		OccupationPieChartView occupationPieChartView = new OccupationPieChartView(parkingLot, 500, 500);
		
		TextView TextView = new TextView(parkingLot, 200, 100);

		// Setup the screen.
		screen = new JFrame("Parking Simulator");
		screen.setSize(1000, 825);
		screen.setResizable(true);
		screen.setLayout(null);
		screen.setVisible(true);
		screen.getContentPane().setBackground(new Color(0xee, 0xee, 0xee));

		screen.getContentPane().add(carParkView);
		screen.getContentPane().add(occupationPieChartView);
		screen.getContentPane().add(TextView);

		carParkView.setBounds(0, 0, carParkView.getWidth(), carParkView.getHeight());
		occupationPieChartView.setBounds(0, carParkView.getHeight(), 250, 250);
		TextView.setBounds(500, carParkView.getHeight(), 200, 100);

		//opmaak van de buttons
		Controller controller = new Controller(model);
		controller.makeControl();
		screen.getContentPane().add(controller.eenStap);
		screen.getContentPane().add(controller.honderdStappen);
		screen.getContentPane().add(controller.start);
		screen.getContentPane().add(controller.stop);
		screen.getContentPane().add(controller.ticksPerSecond);

		controller.eenStap.setBounds(occupationPieChartView.getWidth(), carParkView.getHeight(), 100, 25);
		controller.honderdStappen.setBounds(occupationPieChartView.getWidth(), carParkView.getHeight()+25, 100, 25);
		controller.start.setBounds(occupationPieChartView.getWidth()+100, carParkView.getHeight(), 100, 25);
		controller.stop.setBounds(occupationPieChartView.getWidth()+100, carParkView.getHeight()+25, 100, 25);
		controller.ticksPerSecond.setBounds(occupationPieChartView.getWidth(), carParkView.getHeight()+50, 200, 40);

		System.out.printf("in Constructor: %d %d\n", carParkView.getWidth(), carParkView.getHeight());

		System.out.println(carParkView.getWidth());
		System.out.println(carParkView.getHeight());

		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		model.start();
	}
}

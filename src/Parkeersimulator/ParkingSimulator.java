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
import Parkeersimulator.view.OccupationLineGraphView;
import Parkeersimulator.view.OccupationPieChartView;

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

		OccupationPieChartView occupationPieChartView = new OccupationPieChartView(parkingLot, 250, 250);
		OccupationLineGraphView occupationLineGraphView = new OccupationLineGraphView(parkingLot, 800, 400);

		// Setup the screen.
		screen = new JFrame("Parking Simulator");
		screen.setSize(1000, 825);
		screen.setResizable(true);
		screen.setLayout(null);
		screen.setVisible(true);
		screen.getContentPane().setBackground(new Color(0xee, 0xee, 0xee));

		screen.getContentPane().add(carParkView);
		screen.getContentPane().add(occupationPieChartView);
		screen.getContentPane().add(occupationLineGraphView);

		carParkView.setBounds(0, 0, carParkView.getWidth(), carParkView.getHeight());
		occupationPieChartView.setBounds(0, carParkView.getHeight(), occupationPieChartView.getWidth(), occupationPieChartView.getHeight());
		occupationLineGraphView.setBounds(0, carParkView.getHeight()+occupationPieChartView.getHeight(), occupationLineGraphView.getWidth(), occupationLineGraphView.getHeight());

		//opmaak van de buttons
		Controller controller = new Controller(model);
		controller.makeControl();
		screen.getContentPane().add(controller.eenStap);
		screen.getContentPane().add(controller.honderdStappen);
		screen.getContentPane().add(controller.start);
		screen.getContentPane().add(controller.stop);
		screen.getContentPane().add(controller.ticksPerSecond);

		controller.eenStap.setBounds(occupationPieChartView.getWidth()+20, carParkView.getHeight(), 100, 25);
		controller.honderdStappen.setBounds(occupationPieChartView.getWidth()+20, carParkView.getHeight()+25, 100, 25);
		controller.start.setBounds(occupationPieChartView.getWidth()+120, carParkView.getHeight(), 100, 25);
		controller.stop.setBounds(occupationPieChartView.getWidth()+120, carParkView.getHeight()+25, 100, 25);
		controller.ticksPerSecond.setBounds(occupationPieChartView.getWidth()+20, carParkView.getHeight()+57, 200, 40);

		System.out.printf("in Constructor: %d %d\n", occupationLineGraphView.getWidth(), occupationLineGraphView.getHeight());

		System.out.println(carParkView.getWidth());
		System.out.println(carParkView.getHeight());

		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		//model.run(0);

		parkingLot.updateView();

		model.start();
	}
}

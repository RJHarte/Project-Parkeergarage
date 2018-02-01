package Parkeersimulator;

import java.awt.Color;

import javax.swing.JFrame;

import Parkeersimulator.Models.ParkingLot;
import Parkeersimulator.Models.Simulator;
import Parkeersimulator.controls.Controller;
import Parkeersimulator.view.CarParkView;
import Parkeersimulator.view.OccupationLineGraphView;
import Parkeersimulator.view.OccupationPieChartView;
import Parkeersimulator.view.TextView;

public class ParkingSimulator {
	private JFrame screen;
	private CarParkView carParkView;
	private Simulator Simulator;
	public static ParkingSimulator parkingSimulatorOpslag;

	/**
	 * Constructor for the class.
	 */
	public ParkingSimulator() {
		// Create components.

		ParkingLot parkingLot = new ParkingLot(3, 6, 30, 90, 10);

        ParkingSimulator.parkingSimulatorOpslag = this;		

		// Setup the screen.
		screen = new JFrame("Parking Simulator");
		screen.setSize(1000, 825);
		screen.setResizable(true);
		screen.setLayout(null);
		screen.setVisible(true);
		screen.getContentPane().setBackground(new Color(0xee, 0xee, 0xee));

		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		reset();


	}
	/**
	 * 
	 */
	public void reset() {
		ParkingLot parkingLot = new ParkingLot(3, 6, 30, 90, 90);

		Simulator simulator = new Simulator(parkingLot);

		CarParkView carParkView = new CarParkView(simulator, 690, 320);

		OccupationPieChartView occupationPieChartView = new OccupationPieChartView(simulator, 100, 100);
		OccupationLineGraphView occupationLineGraphView = new OccupationLineGraphView(simulator, 800, 300);

		TextView textView = new TextView(simulator, 150, 300);
		
		screen.getContentPane().removeAll();


		screen.getContentPane().add(carParkView);
		screen.getContentPane().add(occupationPieChartView);
		screen.getContentPane().add(occupationLineGraphView);
		screen.getContentPane().add(textView);

		carParkView.setBounds(0, 0, carParkView.getWidth(), carParkView.getHeight());
		occupationPieChartView.setBounds(0, carParkView.getHeight(), occupationPieChartView.getWidth(), occupationPieChartView.getHeight());
		occupationLineGraphView.setBounds(0, carParkView.getHeight()+occupationPieChartView.getHeight(), occupationLineGraphView.getWidth(), occupationLineGraphView.getHeight());
		textView.setBounds(700, 0, textView.getWidth(), textView.getHeight());

		/**
		 * method get contentpane is used to get information of the specified objects so it can draw these later on 
		 */
		Controller controller = new Controller(simulator);
		controller.makeControl();
		screen.getContentPane().add(controller.minute);
		screen.getContentPane().add(controller.hour);
		screen.getContentPane().add(controller.day);
		screen.getContentPane().add(controller.start);
		screen.getContentPane().add(controller.stop);
		screen.getContentPane().add(controller.maxTickSpeed);
		screen.getContentPane().add(controller.normalSpeed);
		screen.getContentPane().add(controller.ticksPerSecond);
		screen.getContentPane().add(controller.reset);
		
		/**
		 *  sets the location and the size of the buttons and the slider
		 */
		controller.minute.setBounds(120, carParkView.getHeight(), 100, 25);
		controller.hour.setBounds(120, carParkView.getHeight()+25, 100, 25);
		controller.day.setBounds(120, carParkView.getHeight()+50, 100, 25);
		controller.start.setBounds(220, carParkView.getHeight(), 100, 25);
		controller.stop.setBounds(220, carParkView.getHeight(), 100, 25);
		controller.maxTickSpeed.setBounds(220, carParkView.getHeight()+25, 100, 25);
		controller.normalSpeed.setBounds(220, carParkView.getHeight()+25, 100, 25);
		controller.ticksPerSecond.setBounds(325, carParkView.getHeight(), 40, 83);
		controller.reset.setBounds(220, carParkView.getHeight()+50, 100, 25);

		/**
		 * this draws the parkingLot
		 */
		parkingLot.updateView();
	}
}

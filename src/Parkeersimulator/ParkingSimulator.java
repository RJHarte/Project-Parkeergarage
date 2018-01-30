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

	/**
	 * Constructor for the class.
	 */
	public ParkingSimulator() {
		// Create components.
		ParkingLot parkingLot = new ParkingLot(3, 6, 30, 5);

		Simulator simulator = new Simulator(parkingLot);

		CarParkView carParkView = new CarParkView(simulator, 690, 320);

		OccupationPieChartView occupationPieChartView = new OccupationPieChartView(simulator, 100, 100);
		OccupationLineGraphView occupationLineGraphView = new OccupationLineGraphView(simulator, 800, 300);
		TextView textView = new TextView(simulator, 200, 200);

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
		screen.getContentPane().add(textView);

		carParkView.setBounds(0, 0, carParkView.getWidth(), carParkView.getHeight());
		occupationPieChartView.setBounds(0, carParkView.getHeight(), occupationPieChartView.getWidth(), occupationPieChartView.getHeight());
		occupationLineGraphView.setBounds(0, carParkView.getHeight()+occupationPieChartView.getHeight(), occupationLineGraphView.getWidth(), occupationLineGraphView.getHeight());
		textView.setBounds(700, 0, textView.getWidth(), textView.getHeight());

		//opmaak van de buttons
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

		controller.minute.setBounds(occupationPieChartView.getWidth()+20, carParkView.getHeight(), 100, 25);
		controller.hour.setBounds(occupationPieChartView.getWidth()+20, carParkView.getHeight()+25, 100, 25);
		controller.day.setBounds(occupationPieChartView.getWidth()+20, carParkView.getHeight()+50, 100, 25);
		controller.start.setBounds(occupationPieChartView.getWidth()+120, carParkView.getHeight(), 100, 25);
		controller.stop.setBounds(occupationPieChartView.getWidth()+120, carParkView.getHeight()+25, 100, 25);
		controller.maxTickSpeed.setBounds(occupationPieChartView.getWidth()+220, carParkView.getHeight()+25, 100, 25);
		controller.normalSpeed.setBounds(occupationPieChartView.getWidth()+220, carParkView.getHeight(), 100, 25);
		controller.ticksPerSecond.setBounds(occupationPieChartView.getWidth()+20, carParkView.getHeight()+82, 200, 40);

		System.out.printf("in Constructor: %d %d\n", carParkView.getWidth(), carParkView.getHeight());

		System.out.println(carParkView.getWidth());
		System.out.println(carParkView.getHeight());

		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		parkingLot.updateView();
	}
}

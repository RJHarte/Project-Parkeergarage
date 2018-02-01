package Parkeersimulator.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Parkeersimulator.ParkingSimulator;
import Parkeersimulator.Models.ParkingLot;
import Parkeersimulator.Models.Simulator;
import Parkeersimulator.view.CarParkView;
import Parkeersimulator.view.OccupationLineGraphView;
import Parkeersimulator.view.OccupationPieChartView;
import Parkeersimulator.view.TextView;
import Parkeersimulator.Main;

public class Controller extends AbstractController implements ActionListener, ChangeListener {

	private static final Color LIGHT_GRAY = null;
	private static final Color GRAY = null;
	private static final Color YELLOW = null;

	public Controller(Simulator sim) {
		super(sim);
	}

	public JButton minute;
	public JButton hour;
	public JButton day;
	public JButton stop;
	public JButton start;
	public JSlider ticksPerSecond;
	public JButton maxTickSpeed;
	public JButton normalSpeed;
	public JButton reset;

	public void makeControl() {
		// Doe eem "this."
		minute = new JButton("Minute");
		minute.addActionListener(this);
		this.minute.setOpaque(true);
		
		hour = new JButton("Hour");
		hour.addActionListener(this);
		this.hour.setOpaque(true);
		
		day = new JButton("Day");
		day.addActionListener(this);
		this.day.setOpaque(true);
		
		start = new JButton("Start");
		start.addActionListener(this);
		
		stop = new JButton("Stop");
		stop.addActionListener(this);
		stop.setVisible(false);

		this.maxTickSpeed = new JButton("Turbo");
		maxTickSpeed.addActionListener(this);
		
		this.normalSpeed = new JButton("Normal");
		normalSpeed.addActionListener(this);
		
		this.reset = new JButton("Reset");
		this.reset.addActionListener(this);
		
		ticksPerSecond = new JSlider(JSlider.VERTICAL, 0, 200, 100);
		ticksPerSecond.addChangeListener(this);

		//Turn on labels at major tick marks.
		ticksPerSecond.setMajorTickSpacing(50);
		ticksPerSecond.setMinorTickSpacing(1);
		//ticksPerSecond.setPaintTicks(true);
		//ticksPerSecond.setPaintLabels(true);
	}
	
	
	
	Color DBcolor = new JButton().getBackground();

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.minute) {
			this.simulator.manualTick(1, true);
		}

		if (e.getSource() == this.hour) {
			this.simulator.manualTick(60, false);
		}
		
		if (e.getSource() == this.day) {
			this.simulator.manualTick(1440, false);
		}

		if (e.getSource() == this.start && simulator.running == false) {
			this.simulator.start();
			this.stop.setVisible(true);
			this.start.setVisible(false);
			minute.setEnabled(false);
			hour.setEnabled(false);
			day.setEnabled(false);
		}

		if (e.getSource() == this.stop && simulator.running == true) {
			minute.setEnabled(true);
			hour.setEnabled(true);
			day.setEnabled(true);
			this.simulator.stop();
			this.stop.setVisible(false);
			this.start.setVisible(true);
		}
		if (e.getSource() == this.maxTickSpeed) {
			this.simulator.newTickDuration(0);
			this.normalSpeed.setVisible(true);
			this.maxTickSpeed.setVisible(false);
		}
		if (e.getSource() == this.normalSpeed) {
			this.simulator.newTickDuration(100);
			this.normalSpeed.setVisible(false);
			this.maxTickSpeed.setVisible(true);
		}
		if (e.getSource() == this.reset) {
			this.simulator.stop();
			ParkingSimulator.parkingSimulatorOpslag.reset();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			this.simulator.newTickDuration(200-source.getValue());
		}
	}
}

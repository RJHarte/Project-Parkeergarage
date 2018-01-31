package Parkeersimulator.controls;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
		
		hour = new JButton("Hour");
		hour.addActionListener(this);
		
		day = new JButton("Day");
		day.addActionListener(this);

		start = new JButton("Start");
		start.addActionListener(this);
		
		stop = new JButton("Stop");
		stop.addActionListener(this);

		this.maxTickSpeed = new JButton("Turbo");
		maxTickSpeed.addActionListener(this);
		
		this.normalSpeed = new JButton("Normal");
		normalSpeed.addActionListener(this);
		
		this.reset = new JButton("Reset");
		this.reset.addActionListener(this);
		
		ticksPerSecond = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
		ticksPerSecond.addChangeListener(this);

		//Turn on labels at major tick marks.
		ticksPerSecond.setMajorTickSpacing(50);
		ticksPerSecond.setMinorTickSpacing(1);
		ticksPerSecond.setPaintTicks(true);
		ticksPerSecond.setPaintLabels(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == this.minute && simulator.running == false) {
			this.simulator.manualTick(1, true);
		}

		if(e.getSource() == this.hour && simulator.running == false) {
			this.simulator.manualTick(60, false);
		}
		
		if(e.getSource() == this.day && simulator.running == false) {
			this.simulator.manualTick(1440, false);
		}

		if(e.getSource() == this.start) {
			this.simulator.start();
		}

		if(e.getSource() == this.stop) {
			this.simulator.stop();
		}
		if(e.getSource() == this.maxTickSpeed) {
			this.simulator.newTickDuration(0);
		}
		if(e.getSource() == this.normalSpeed) {
			this.simulator.newTickDuration(100);
		}
		if(e.getSource() == this.reset) {
			this.simulator.stop();
			ParkingSimulator.parkingSimulatorOpslag.reset();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
	      JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
	            this.simulator.newTickDuration(source.getValue());
	            }
	}
}

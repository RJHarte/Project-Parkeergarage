package Parkeersimulator.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Parkeersimulator.Models.Simulator;

public class Controller extends AbstractController implements ActionListener, ChangeListener {

	public Controller(Simulator sim) {
		super(sim);
	}

	public JButton eenStap;
	public JButton honderdStappen;
	public JButton stop;
	public JButton start;
	public JSlider ticksPerSecond;

	public void makeControl() {
		// Doe eem "this."
		eenStap = new JButton("1 stap");
		eenStap.addActionListener(this);
		//eenStap.setBackground(Color.GRAY);
		//eenStap.setOpaque(true);

		honderdStappen = new JButton("100 stappen");
		honderdStappen.addActionListener(this);

		start = new JButton("start");
		start.addActionListener(this);

		stop = new JButton("stop");
		stop.addActionListener(this);

		ticksPerSecond = new JSlider(JSlider.HORIZONTAL, 0, 1000, 100);
		ticksPerSecond.addChangeListener(this);

		//Turn on labels at major tick marks.
		ticksPerSecond.setMajorTickSpacing(250);
		ticksPerSecond.setMinorTickSpacing(1);
		ticksPerSecond.setPaintTicks(true);
		ticksPerSecond.setPaintLabels(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == this.eenStap) {
			this.simulator.manualTick(1, true);
		}

		if(e.getSource() == this.honderdStappen) {
			this.simulator.manualTick(100,  false);
		}

		if(e.getSource() == this.start) {
			this.simulator.start();
		}

		if(e.getSource() == this.stop) {
			this.simulator.stop();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
	      JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
	            System.out.println("Test "+ source.getValue());
	            }

	}



}

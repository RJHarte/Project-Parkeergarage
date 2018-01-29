package Parkeersimulator.controls;

import Parkeersimulator.Models.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Controller extends AbstractController implements ActionListener{
	
	public Controller(Simulator sim) {
		super(sim);
	}

	public JButton eenStap;
	private JButton honderdStappen;
	private JButton stop;
	private JButton start;
	
	public void makeControl() {
		// Doe eem "this."
		eenStap = new JButton("1 stap");
		eenStap.addActionListener(this);
		eenStap.setBackground(Color.GRAY);
		eenStap.setOpaque(true);
		eenStap.setSize(50, 50);
		
		
		honderdStappen = new JButton("100 stappen");
		
		start = new JButton("start");
		
		stop = new JButton("stop");
		
		/**this.setLayout(null);
		add(eenStap);
		add(honderdStappen);
		add(start);
		add(stop);
		eenStap.setBounds(405, 10, 120, 30);
		eenStap.setBounds(555, 10, 100, 30);
		start.setBounds(40, 10, 120, 30);
		stop.setBounds(170, 10, 120, 30);
		setVisible(true);*/
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.eenStap) { 
			this.simulator.tick();
		}
	
		if(e.getSource() == this.honderdStappen) {
			for(int i = 0; i<=100; i++) {
				this.simulator.tick();
			}
		}
		
		if(e.getSource() == this.start) {
			
		}
		
		if(e.getSource() == this.stop) {
			
		}
	}
	


}

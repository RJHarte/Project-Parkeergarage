package Parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;

import Parkeersimulator.Models.Simulator;
import Parkeersimulator.Models.Car;
import Parkeersimulator.Models.Location;
import Parkeersimulator.Models.ParkingLot;
import Parkeersimulator.Models.ParkingLot.CarAmount;

public class TextView extends AbstractView {
    private static final long serialVersionUID = 1234L;

    private Dimension size;
    private Image image;
    private ParkingLot parkingLot;
    private Simulator simulator;
    
	private JLabel timeL;
	private JLabel carL;
	private JLabel adhocL;
	private JLabel reserveL;
	private JLabel abboL;
	private JLabel moneyL;
	private JLabel que1L;
	private JLabel que2L;
	private JLabel que3L;

    public TextView(Simulator simulator, int width, int height) {
        super(simulator, width, height);

        this.size = new Dimension(200, 100);
        this.simulator = simulator;
        
		timeL = new JLabel("");
		carL = new JLabel("");
		adhocL = new JLabel("");
		reserveL = new JLabel("");
		abboL = new JLabel("");
		moneyL = new JLabel("");
		que1L = new JLabel("");
		que2L = new JLabel("");
		que3L = new JLabel("");
		timeL.setPreferredSize(new Dimension(width, 13));
		carL.setPreferredSize(new Dimension(width, 13));
		adhocL.setPreferredSize(new Dimension(width, 13));
		reserveL.setPreferredSize(new Dimension(width, 13));
		abboL.setPreferredSize(new Dimension(width, 13));
		moneyL.setPreferredSize(new Dimension(width, 13));
		que1L.setPreferredSize(new Dimension(width, 13));
		que2L.setPreferredSize(new Dimension(width, 13));
		que3L.setPreferredSize(new Dimension(width, 13));
		add(timeL);
		add(carL);
		add(adhocL);
		add(reserveL);
		add(abboL);
		add(moneyL);
		add(que1L);
		add(que2L);
		add(que3L);
		updateLabels();
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    @Override
	public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }

    	 //Repaint the component, changes the label-contents
    	
    public void paintComponent(Graphics g) {
    	g.setColor(new Color(0xee, 0xee, 0xee));
    	g.fillRect(0, 0, width, height);
    	updateLabels();
    }

    public void updateLabels() {
    	int week = this.simulator.getWeek();
    	int day = this.simulator.getDay();
    	int hour = this.simulator.getHour();
    	int min = this.simulator.getMinute();
    	String time = "Week: " + week + ", Day: " + day + ", Time: " + hour + ":" + (min < 10 ? ("0" + min) : min);
    	String cars = "Cars: " + this.simulator.getParkingLot().getAmountOfCars() + " / 540";
    	String adhoc = "Ad-Hoc: " + this.simulator.getParkingLot().calculateAmountOfCars()[0].amount;
    	String reserv = "Reserved: " + this.simulator.getParkingLot().calculateAmountOfCars()[1].amount; 
    	String abbo = "Pass: " + this.simulator.getParkingLot().calculateAmountOfCars()[2].amount; 
    	String money = "Total money earned: ";
    	String que1 = "AdHoc Entrance queue: "; 
    	String que2 = "Reserve Entrance queue: ";
    	String que3 = "Passholders Entrance queue: ";
    	
    	timeL.setText(time);
    	carL.setText(cars);
    	adhocL.setText(adhoc);
    	reserveL.setText(reserv);
    	abboL.setText(abbo);	
    	moneyL.setText(money);
    	que1L.setText(que1);
    	que2L.setText(que2);
    	que3L.setText(que3);
    	
    	}


}

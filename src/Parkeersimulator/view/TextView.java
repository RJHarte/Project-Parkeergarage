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
    private ParkingLot iets;
    
	private JLabel timeL;
	private JLabel carL;
	private JLabel adhocL;
	private JLabel reserveL;
	private JLabel abboL;

    public TextView(ParkingLot parkingLot, int width, int height) {
        super(parkingLot, width, height);

        this.size = new Dimension(200, 100);
        this.iets = parkingLot;
        
		timeL = new JLabel("");
		carL = new JLabel("");
		adhocL = new JLabel("");
		reserveL = new JLabel("");
		abboL = new JLabel("");
		timeL.setPreferredSize(new Dimension(width, 13));
		carL.setPreferredSize(new Dimension(width, 13));
		adhocL.setPreferredSize(new Dimension(width, 13));
		reserveL.setPreferredSize(new Dimension(width, 13));
		abboL.setPreferredSize(new Dimension(width, 13));
		add(timeL);
		add(carL);
		add(adhocL);
		add(reserveL);
		add(abboL);
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
    	int week = 1;	//iets.getWeek();
    	int day = 2;	//iets.getDay();
    	int hour = 3;	//iets.getHour();
    	int min = 4;	//iets.getMinute();
    	String time = "Week: " + week + ", Day: " + day + ", Time: " + hour + ":" + (min < 10 ? ("0" + min) : min);
    	String cars = "Cars: " + this.parkingLot.getAmountOfCars() + " / 540";
    	String adhoc = "Ad-Hoc: " + iets;//getCarData()[0];
    	String reserv = "Reserved: " + iets;//getCarPark().getCarData()[1];
    	String abbo = "Pass: " + iets;//getCarPark().getCarData()[2];

    	timeL.setText(time);
    	carL.setText(cars);
    	adhocL.setText(adhoc);
    	reserveL.setText(reserv);
    	abboL.setText(abbo);	
    	}
}

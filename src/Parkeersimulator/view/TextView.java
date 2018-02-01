package Parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;

import Parkeersimulator.Models.Simulator;

/**
 * This class puts different information from the simulation into a 
 * textview that can be displayed
 */
public class TextView extends AbstractView {
    private static final long serialVersionUID = 1234L;

    private Simulator simulator;
    
    //defining the different JLabels
    
	private JLabel timeL;
	private JLabel carL;
	private JLabel adhocL;
	private JLabel reserveL;
	private JLabel abboL;
	private JLabel moneyL;
	private JLabel entranceL;
	private JLabel passentranceL;
	private JLabel paymentL;
	private JLabel exitL;
	private JLabel leftQueL;
	private JLabel legadhocL;
	private JLabel legpassL;
	private JLabel legreservL;

    public TextView(Simulator simulator, int width, int height) {
        super(simulator, width, height);

        this.size = new Dimension(250, 300);
        this.simulator = simulator;
        
		timeL = new JLabel("");
		carL = new JLabel("");
		adhocL = new JLabel("");
		reserveL = new JLabel("");
		abboL = new JLabel("");
		moneyL = new JLabel("");
		entranceL = new JLabel("");
		passentranceL = new JLabel("");
		paymentL = new JLabel("");
		exitL = new JLabel("");
		leftQueL = new JLabel("");
		legadhocL = new JLabel("Ad-Hoc Car");
		legpassL = new JLabel("Passholder Car");
		legreservL = new JLabel("Reserved Car");
		
		legadhocL.setForeground(Color.RED);
		legpassL.setForeground(Color.BLUE);
		legreservL.setForeground(Color.ORANGE);
		
		// setting dimensions
		
		timeL.setPreferredSize(new Dimension(width, 13));
		carL.setPreferredSize(new Dimension(width, 13));
		adhocL.setPreferredSize(new Dimension(width, 13));
		reserveL.setPreferredSize(new Dimension(width, 13));
		abboL.setPreferredSize(new Dimension(width, 13));
		moneyL.setPreferredSize(new Dimension(width, 13));
		entranceL.setPreferredSize(new Dimension(width, 13));
		passentranceL.setPreferredSize(new Dimension(width, 13));
		paymentL.setPreferredSize(new Dimension(width, 13));
		exitL.setPreferredSize(new Dimension(width, 13));
		leftQueL.setPreferredSize(new Dimension(width, 13));
		legadhocL.setPreferredSize(new Dimension(width, 13));
		legpassL.setPreferredSize(new Dimension(width, 13));
		legreservL.setPreferredSize(new Dimension(width, 13));
		
		//adding the labels
		
		add(timeL);
		add(carL);
		add(adhocL);
		add(reserveL);
		add(abboL);
		add(moneyL);
		add(entranceL);
		add(passentranceL);
		add(paymentL);
		add(exitL);
		add(leftQueL);
		add(legadhocL);
		add(legpassL);
		add(legreservL);
		updateLabels();
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    @Override
	public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }

    /**
     * Repaint the component, changes the label-contents
     */	
    public void paintComponent(Graphics g) {
    	g.setColor(new Color(0xee, 0xee, 0xee));
    	g.fillRect(0, 0, width, height);
    	updateLabels();
    }
    /**
     * updates the view with information
     */
    public void updateLabels() {
    	int week = this.simulator.getWeek();
    	int day = this.simulator.getDay();
    	int hour = this.simulator.getHour();
    	int min = this.simulator.getMinute();
    	String time = "Week: " + week + ", Day: " + day + ", Time: " + hour + ":" + (min < 10 ? ("0" + min) : min);
    	String cars = "Cars: " + this.simulator.getParkingLot().getAmountOfCars() + " / " + (this.simulator.getParkingLot().getNumberOfFloors() 
    			* this.simulator.getParkingLot().getNumberOfRows() * this.simulator.getParkingLot().getNumberOfPlaces());
    	String adhoc = "Ad-Hoc: " + this.simulator.getParkingLot().calculateAmountOfCars()[0].amount;
    	String reserv = "Reserved: " + this.simulator.getParkingLot().calculateAmountOfCars()[1].amount; 
    	String abbo = "Pass: " + this.simulator.getParkingLot().calculateAmountOfCars()[2].amount; 
    	String money = "Total money earned: " + (simulator.getTotalEarnings() * 0.01) + "$";
    	String enter = "Entrance Queue: " + simulator.getQueues().get(0).carsInQueue();
    	String passenter = "Passholders EQueue: " + simulator.getQueues().get(1).carsInQueue();;
    	String pay = "Payment Queue: " + simulator.getQueues().get(2).carsInQueue();
    	String exit = "Exit Queue: " + simulator.getQueues().get(3).carsInQueue();
    	String left = "Cars that left queue: " + (this.simulator.carsNotWantedToQueue[0] + 
    			this.simulator.carsNotWantedToQueue[1] + this.simulator.carsNotWantedToQueue[2]);
    	
    	
    	timeL.setText(time);
    	carL.setText(cars);
    	adhocL.setText(adhoc);
    	reserveL.setText(reserv);
    	abboL.setText(abbo);	
    	moneyL.setText(money);
    	entranceL.setText(enter);
    	passentranceL.setText(passenter);
    	paymentL.setText(pay);
    	exitL.setText(exit);
    	leftQueL.setText(left);
    	}
}

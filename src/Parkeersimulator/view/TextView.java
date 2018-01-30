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
	private JLabel entranceL;
	private JLabel passentranceL;
	private JLabel paymentL;
	private JLabel exitL;

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
		entranceL = new JLabel("");
		passentranceL = new JLabel("");
		paymentL = new JLabel("");
		exitL = new JLabel("");
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
    	String money = "Total money earned: " + simulator.getTotalEarnings() + "$";
    	String enter = "Entrance Queue: " + simulator.getQueues().get(0).carsInQueue();
    	String passenter = "Passholders EQueue: " + simulator.getQueues().get(1).carsInQueue();;
    	String pay = "Payment Queue: " + simulator.getQueues().get(2).carsInQueue();
    	String exit = "Exit Queue: " + simulator.getQueues().get(3).carsInQueue();
    	
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
    	}


}

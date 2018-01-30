package Parkeersimulator.Models;

import java.util.Random;

import Parkeersimulator.DataStore;
import Parkeersimulator.DataStore.StorageItem;

//import Parkeersimulator.SimulatorView;

public class Simulator implements Runnable{

	// Easy names for regular people and subscription holders.
	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String RESERVE = "3";

	// The difference queues.
	private CarQueue entranceCarQueue; // Entrance queue for regular people.
    private CarQueue entrancePassQueue; // Subscription holders have their own entrance.
    private CarQueue paymentCarQueue; // Queue where regular people pay.
    private CarQueue exitCarQueue; // Exit queue.

    private ParkingLot parkingLot;
	private DataStore datastore;

	//private ParkingLot parkinglot;

    // The current time.
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    protected int week = 0;

    // Milliseconds between ticks. Change this to make the simulation go faster.
    private int tickDuration = 100;

    // Average number of arriving cars per hour

    // For regular people (AD_HOC)
	int weekDayArrivals = 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour

    // For pass (subscription people) people (PASS).
    int weekDayPassArrivals = 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    // For reservees (RESERVE)
    int weekDayReservationArrivals = 75; // average number of arriving cars per hour
    int weekendReservationArrivals = 10; // average number of arriving cars per hour

    // Amount of cars the different types of queue can handle per minute.
    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute (apparently payment is very fast) (easy on the sass boii)
    int exitSpeed = 5; // number of cars that can leave per minute

    // Earnings
    private int totalEarnings = 0;

    private boolean running;

    /**
     * Simulator constructor, initializes queues and view
     */
    public Simulator(ParkingLot parkingLot) {
        this.entranceCarQueue = new CarQueue();
        this.entrancePassQueue = new CarQueue();
        this.paymentCarQueue = new CarQueue();
        this.exitCarQueue = new CarQueue();
        this.parkingLot = parkingLot;
        this.datastore = DataStore.createInstance();
        //System.out.println("ik ben de constructor van SImulator: " + this.parkingLot);
    }

    public int newTickDuration(int milliSec){
    	this.tickDuration = milliSec;
    	return tickDuration;
    }

    @Override
    public void run() {
    	int i = 0;
    	while (running) {
    		long startTime = System.currentTimeMillis(); // Time in milliseconds.

        	if (i%5 == 0) {
	            System.out.printf("Cur: %d %d:%d; Entrance queue: %d; Payment queue: %d; Exit queue: %d\n",
	            		this.day, this.hour, this.minute,
	            		this.entranceCarQueue.carsInQueue(),
	            		this.paymentCarQueue.carsInQueue(),
	            		this.exitCarQueue.carsInQueue());
        	}

            tick();

            long endTime = System.currentTimeMillis(); // Time in milliseconds.

            long timeTickTook = endTime-startTime; // How long this tick took to calculate

            long timeToSleep = this.tickDuration - timeTickTook;

    		// TODO: This sleep should not be done in tick, but in the method that continuely runs tick (which is run()).

            if (timeToSleep > 0) {
    	        // Pause.
    	        try {
    	            Thread.sleep(timeToSleep);
    	        } catch (InterruptedException e) {
    	            e.printStackTrace();
    	        }
            }

            i++;
        }
    }

    public void start()
    {
    	if (this.running) {
    		return;
    	}

    	this.running = true;

    	new Thread(this).start();
    }

    public void stop()
    {
    	this.running = false;
    }


    private void tick()
    {
    	this.tick(true);
    }

    /**
     * tick represents one time period in the simulation.
     * The tick triggers and handles events in this world.
     */
    private void tick(boolean updateViews)
    {
    	this.advanceTime();

    	this.handleExit();

        this.handleEntrance();

		StorageItem storageItem = this.datastore.new StorageItem();
		storageItem.minute = this.minute;
		storageItem.hour = this.hour;
		storageItem.day = this.day;
		storageItem.week = this.week;
		storageItem.carTypeAmount = this.parkingLot.calculateAmountOfCars();

		this.datastore.addItem(storageItem);

        this.updateViews();
    }

    /**
     * Execute given amount of ticks manually, as fast as possible.
     *
     * @param amount
     * @param Whether to update views every tick.
     */
    public void manualTick(int amount, boolean updateViewsEveryTick)
    {
    	for (int i = 0; i < amount; i++) {
    		tick(updateViewsEveryTick);
    	}
    }

    /**
     * advanceTime increases the current time in the simulation by one minute.
     */
    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
            week++;
        }

    }

	public int getWeek() {
		return week;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

    /**
     * handleEntrance triggers and handles events for the entrance queues.
     */
    private void handleEntrance(){
    	this.carsArriving();
    	this.carsEntering(entrancePassQueue);
    	this.carsEntering(entranceCarQueue);
    }

    /**
     * handleExit triggers and handles events for the exit queue.
     */
    private void handleExit(){
    	this.carsReadyToLeave();
    	this.carsPaying();
    	this.carsLeaving();
    }

    /**
     * Advances simulation view by one tick
     */
    private void updateViews(){
    	this.parkingLot.tick();
        // Update the car park view.
    	this.parkingLot.updateView();
    }

    /**
     * Generates arriving cars and adds them to the queues
     */
    private void carsArriving(){
    	int numberOfCars = this.getNumberOfCars(weekDayArrivals, weekendArrivals);
    	this.addArrivingCars(numberOfCars, AD_HOC);

    	numberOfCars = this.getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
    	this.addArrivingCars(numberOfCars, PASS);

    	numberOfCars = this.getNumberOfCars(weekDayReservationArrivals, weekendReservationArrivals);
    	this.addArrivingCars(numberOfCars, RESERVE);
    }

    /**
     * Removes cars from queues and places them into empty spots
     *
     * @param queue
     */
    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 &&	// Checks if any cars are in queue
    			this.parkingLot.getNumberOfOpenSpots()>0 && // Checks if spots are available
    			i<this.enterSpeed) {	// Checks if enterspeed is not reached
            Car car = queue.removeCar();
            Location freeLocation = this.parkingLot.getFirstFreeLocation();
            this.parkingLot.setCarAt(freeLocation, car);
            i++;
        }
    }

    /**
     * Adds paying cars to the payment queue.
     * Makes car leave parking spot.
     */
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
    	Car car;
        while ((car = this.parkingLot.getFirstLeavingCar()) != null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            this.paymentCarQueue.addCar(car);

	            this.totalEarnings += this.paymentCarQueue.carPays(car);

        	}
        	else {
        		this.carLeavesSpot(car);
        	}
        }
    }

    /**
    * Handles cars from the payment queue
    */
    private void carsPaying(){
        // Let cars pay.
    	int i = 0;
    	while (this.paymentCarQueue.carsInQueue() > 0 && // Checks if any cars are in queue
			i < this.paymentSpeed){ // Checks if enterspeed is not reached
            Car car = this.paymentCarQueue.removeCar();
            // TODO Handle payment.
            this.carLeavesSpot(car);
            i++;
    	}
    }

    /**
     * Removes first cars from exit queue.
     */
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (this.exitCarQueue.carsInQueue()>0 && i < this.exitSpeed){
    		this.exitCarQueue.removeCar();
            i++;
    	}
    }

    /**
     * Generates a random amount of cars depending on the current day o' the week.
     *
     * @param weekDay
     * @param weekend
     * @return amount of cars
     */
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = this.day < 5 // Checks if day is weekday
                ? weekDay	// return weekday
                : weekend;	// return weekend

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    /**
     * Creates given amount of cars and adds them to the correct queue, depending on the car type.
     * @param  numberOfCars
     * @param car type
     */
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC:
            for (int i = 0; i < numberOfCars; i++) {
            	this.entranceCarQueue.addCar(new AdHocCar());
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	this.entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;
    	case RESERVE:
            for (int i = 0; i < numberOfCars; i++) {
            	this.entrancePassQueue.addCar(new ReservedCar());
            }
            break;
    	}
    }

    /**
     * Removes car from location/spot and adss it to the exit queue
     * @param car
     */
    private void carLeavesSpot(Car car){
    	this.parkingLot.removeCarAt(car.getLocation());
        this.exitCarQueue.addCar(car);
        //System.out.println(this.totalEarnings*.01);
    }

	public ParkingLot getParkingLot() {
		//System.out.println("getParkingLot hier: " + this.parkingLot);
		return this.parkingLot;
	}

}

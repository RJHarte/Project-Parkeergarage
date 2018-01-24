package Parkeersimulator.Models;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import Parkeersimulator.view.CarParkView;

/**
 * Hoi dit is een comment
 */
public class ParkingLot extends JFrame {

    private CarParkView carParkView;

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;

    public ParkingLot(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors*numberOfRows*numberOfPlaces;

        this.cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        this.carParkView = new CarParkView(this);

        Container contentPane = this.getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);

        this.updateView();
    }

    public void updateView() {
        this.carParkView.updateView();
    }

	public int getNumberOfFloors() {
        return this.numberOfFloors;
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public int getNumberOfPlaces() {
        return this.numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
    	return this.numberOfOpenSpots;
    }

    /**
     * Get the car from the given parking spot location.
     *
     * @param location
     * @return the car on the location. Null if empty spot.
     */
    public Car getCarAt(Location location) {
        if (!this.locationIsValid(location)) {
            return null;
        }

        return this.cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Put the given car at the given spot location. Only if the spot isn't already taken.
     *
     * @param location
     * @param car
     * @return false if invalid location or taken location.
     */
    public boolean setCarAt(Location location, Car car) {
        if (!this.locationIsValid(location)) {
            return false;
        }

        Car oldCar = getCarAt(location);
        if (oldCar != null) {
        	return false;
        }

        this.cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
        car.setLocation(location);
        this.numberOfOpenSpots--;

        return true;
    }

    /**
     * Remove the car on the given spot location. Only if there is actually a car there.
     *
     * @param location
     * @return the removed car. Null if invalid location or still/already empty spot.
     */
    public Car removeCarAt(Location location) {
        if (!this.locationIsValid(location)) {
            return null;
        }

        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }

        this.cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        this.numberOfOpenSpots++;

        return car;
    }

    /**
     * Determine the first free parking spot location.
     *
     * @return The free spot. Null if no free spot.
     */
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < this.getNumberOfFloors(); floor++) {
            for (int row = 0; row < this.getNumberOfRows(); row++) {
                for (int place = 0; place < this.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (this.getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Determine the first car in the parking space that wants to leave and is not paying.
     *
     * @return Car that wants to leave.
     */
    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < this.getNumberOfFloors(); floor++) {
            for (int row = 0; row < this.getNumberOfRows(); row++) {
                for (int place = 0; place < this.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = this.getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Tell every car in the parking space to tick.
     */
    public void tick() {
        for (int floor = 0; floor < this.getNumberOfFloors(); floor++) {
            for (int row = 0; row < this.getNumberOfRows(); row++) {
                for (int place = 0; place < this.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = this.getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    /**
     * Check if a location exists; is not outside our parking space.
     *
     * @return false is location is invalid
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= this.numberOfFloors || row < 0 || row > this.numberOfRows || place < 0 || place > this.numberOfPlaces) {
            return false;
        }
        return true;
    }
}
package Parkeersimulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 */
public class SimulatorView extends JFrame {

    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors*numberOfRows*numberOfPlaces;

        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        carParkView = new CarParkView();

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

    private class CarParkView extends JPanel {

        private Dimension size;
        private Image carParkImage;

        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            this.size = new Dimension(0, 0);
        }

        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        @Override
		public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }

        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        @Override
		public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }

            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }

        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!this.size.equals(this.getSize())) {
                size = getSize();
                this.carParkImage = this.createImage(size.width, size.height);
            }

            // Draw the squares on the image.
            Graphics graphics = this.carParkImage.getGraphics();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        Color color = car == null ? Color.white : car.getColor();
                        this.drawPlace(graphics, location, color);
                    }
                }
            }
            this.repaint();
        }

        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}

package Parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import Parkeersimulator.ParkingLot;
import Parkeersimulator.Models.Car;
import Parkeersimulator.Models.Location;

public class CarParkView extends AbstractView {

    private Dimension size;
    private Image carParkImage;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(ParkingLot parkingLot) {
    	super(parkingLot);
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

    @Override
	public void updateView() {
        // Create a new car park image if the size has changed.
        if (!this.size.equals(this.getSize())) {
            size = getSize();
            this.carParkImage = this.createImage(size.width, size.height);
        }

        // Draw the squares on the image.
        Graphics graphics = this.carParkImage.getGraphics();
        for(int floor = 0; floor < this.parkingLot.getNumberOfFloors(); floor++) {
            for(int row = 0; row < this.parkingLot.getNumberOfRows(); row++) {
                for(int place = 0; place < this.parkingLot.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = this.parkingLot.getCarAt(location);
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
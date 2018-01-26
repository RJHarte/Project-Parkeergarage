package Parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import Parkeersimulator.Models.Car;
import Parkeersimulator.Models.Location;
import Parkeersimulator.Models.ParkingLot;

public class CarParkView extends AbstractView {
    private static final long serialVersionUID = 1L;
    private Dimension size;
    private Image carParkImage;
    private ParkingLot parkingLot;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(ParkingLot parkingLot, int width, int height) {
        super(parkingLot, width, height);
        this.size = new Dimension(500, 800);
        this.parkingLot = parkingLot;
        //this.carParkImage = createImage(500, 800);
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    @Override
	public Dimension getPreferredSize() {
        return new Dimension(500, 800);
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
        } else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    /**
     * Updates the view by redrawing the image that gets drawn on the screen
     */
    @Override
	public void updateView() {
        // Create a new car park image if the size has changed.
        if (!this.size.equals(getSize())) {
            this.size = this.getSize();
            this.carParkImage = createImage(size.width, size.height);
        }

        System.out.printf("Size in CarParkView: %f %f\n", this.size.getWidth(), this.size.getHeight());

        int floors = this.parkingLot.getNumberOfFloors();
        int rows = this.parkingLot.getNumberOfRows();
        int places = this.parkingLot.getNumberOfPlaces();

        Graphics graphics = this.carParkImage.getGraphics();
        graphics.setColor(new Color(0xee, 0xee, 0xee));
        graphics.fillRect(0, 0, size.width, size.height);
        for (int floor = 0; floor < floors; floor++) {
            for (int row = 0; row < rows; row++) {
                for (int spot = 0; spot < places; spot++) {
                	Location location = new Location(floor, row, spot);

                    Car car = this.parkingLot.getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    drawPlace(graphics, floor, row, spot, color);
                }
            }
        }
        repaint();
    }

    /**
     * Paint a place on this car park view in a given color.
     */
    private void drawPlace(Graphics graphics, int floor, int row, int spot, Color color) {
        graphics.setColor(color);
        graphics.fillRect(floor * 260 + (1 + (int) Math.floor(row * 0.5)) * 75
                + (row % 2) * 20, 60 + spot * 10, 20 - 1, 10 - 1); // TODO use dynamic size
                                                                                                // or constants
    }
}

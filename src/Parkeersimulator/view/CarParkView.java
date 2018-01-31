package Parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import Parkeersimulator.Models.Car;
import Parkeersimulator.Models.Location;
import Parkeersimulator.Models.Simulator;

public class CarParkView extends AbstractView {
    private static final long serialVersionUID = 1L;
    //private Dimension size;
    private Image carParkImage;
  //  private ParkingLot parkingLot;
  //  private Simulator simulator;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(Simulator simulator, int width, int height) {
        super(simulator, width, height);
    	System.out.println("hey hier Carparkview: " + simulator);

        //this.size = new Dimension(500, 800);
      //  this.simulator = simulator;
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
        if (this.size.equals(currentSize)) {
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
        if (!this.size.equals(getSize()) || this.carParkImage == null) {
            this.size = this.getSize();
            this.carParkImage = createImage(size.width, size.height);
        }

        int floors = this.simulator.getParkingLot().getNumberOfFloors();
        int rows = this.simulator.getParkingLot().getNumberOfRows();
        int places = this.simulator.getParkingLot().getNumberOfPlaces();
        int reservePlaced = this.simulator.getParkingLot().getNumberOfReservePlaces();

        Graphics graphics = this.carParkImage.getGraphics();
        graphics.setColor(Color.pink);
        graphics.fillRect(0, 0, size.width, size.height);
        for (int floor = 0; floor < floors; floor++) {
            for (int row = 0; row < rows; row++) {
                for (int place = 0; place < places; place++) {
                	
                	// set passPlace if row meets right conditions
                	Location location = new Location(floor, row, place);
                	this.simulator.getParkingLot().setAllPassPlaces(location,0,this.simulator.getParkingLot().getNumberOfPassPlaces()/3);
                	boolean isPassPlace = this.simulator.getParkingLot().locationIsPassPlace(location);
                	boolean isReserved = this.simulator.getParkingLot().isReserved(location);

                    Car car = this.simulator.getParkingLot().getCarAt(location);
                    //Color color = car == null ? Color.white : car.getColor();
                    Color color;
                    if (car == null && isPassPlace ) {
                    	color = Color.gray;
                    }
                    else if (car == null && isReserved ) {
                    	color = Color.green;
                    }
                    else if (car == null ) {
                    	color = Color.white;
                    }
                    else {
                    	color = car.getColor();
                    }
                    
                    drawPlace(graphics, floor, row, place, color);
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
        int x = (floor*260) + ((int) Math.floor(row * 0.5)) * 50 + (row % 2) * 20;
        int y = spot * 10;
        int xSize = 20 - 1;
        int ySize = 10 - 1;
        graphics.fillRect(x+10, y+10, xSize, ySize);
    }
}

package Parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import Parkeersimulator.Models.ParkingLot;
import Parkeersimulator.Models.ParkingLot.CarAmount;

public class OccupationPieChartView extends AbstractView {
    private static final long serialVersionUID = 1337L;

    private Dimension size;
    private Image image;
    private ParkingLot parkingLot;

    /**
     * Constructor for objects of class CarPark
     */
    public OccupationPieChartView(ParkingLot parkingLot, int width, int height) {
        super(parkingLot, width, height);

        this.size = new Dimension(500, 800);
        this.parkingLot = parkingLot;
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
        if (image == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(image, 0, 0, null);
        } else {
            // Rescale the previous image.
            g.drawImage(image, 0, 0, currentSize.width, currentSize.height, null);
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
            this.image = createImage(size.width, size.height);
        }

        Graphics graphics = this.image.getGraphics();
		this.drawPie(graphics);

        repaint();
    }

    private void drawPie(Graphics graphics)
    {
		CarAmount[] amounts = this.parkingLot.calculateAmountOfCars();

		double total = 0;
		for (int i = 0; i < amounts.length; i++) {
			total += amounts[i].amount;
		}

		double curValue = 0;
		int startAngle = 0;
		for (int i = 0; i < amounts.length; i++) {
			startAngle = (int) (curValue * 360 / total);
			int arcAngle = (int) (amounts[i].amount * 360 / total);

			if (amounts[i].carColor == null) {
				graphics.setColor(Color.white);
			} else{
				graphics.setColor(amounts[i].carColor);
			}
			graphics.fillArc(0, 0, (int)this.getSize().getWidth(), (int)this.getSize().getHeight(), startAngle, arcAngle);
			curValue += amounts[i].amount;
		}
    }
}

package Parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import Parkeersimulator.DataStore;
import Parkeersimulator.DataStore.StorageItem;
import Parkeersimulator.Models.ParkingLot;
import Parkeersimulator.Models.ParkingLot.CarAmount;

public class OccupationLineGraphView extends AbstractView
{
    private static final long serialVersionUID = 1337L;

    private Dimension size;
    private Image image;

    private JFreeChart chart;
    private ChartPanel panel;

    private boolean firstViewUpdate;

    /**
     * Constructor for objects of class CarPark
     */
    public OccupationLineGraphView(ParkingLot parkingLot, int width, int height)
    {
        super(parkingLot, width, height);

        this.size = new Dimension(800, 500);
        this.setPreferredSize(this.size);
        this.parkingLot = parkingLot;

        this.firstViewUpdate = true;
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    @Override
	public Dimension getPreferredSize()
    {
        return new Dimension(800, 400);
    }

    /**screen.getContentPane().add(occupationPieChartView);
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
	public void updateView()
    {
        // Create a new car park image if the size has changed.
        if (!this.size.equals(getSize())) {
            this.size = this.getSize();
            this.image = createImage(size.width, size.height);
        }

        DataStore store = DataStore.getInstance();

        Graphics graphics = this.image.getGraphics();

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());

        ArrayList<StorageItem> items = store.getItems();

        int highest = 0;
        int amount = items.size();
        int amountCars = 0;
        for (StorageItem item : items) {
        	if (amountCars == 0) {
        		amountCars = item.carTypeAmount.length;
        	}

        	for (CarAmount am : item.carTypeAmount) {
        		if (am.carColor == null) {
        			continue;
        		}

        		if (am.amount > highest) {
        			highest = am.amount;
        		}
        	}
        }

        //System.out.println("In OccupationLineGraphView: " + amount);
        //System.out.println("Size: " + this.getSize().getWidth() + " - " + this.getSize().getHeight());

        class Coords {
        	int x = 0;
        	int y = 0;
        }

        Coords[] lastPositions = new Coords[amountCars];

        int w = (int)this.getSize().getWidth();
        int h = (int)this.getSize().getHeight();

        int itemsY = 10;
        double amountPerItemY = (double)highest / itemsY;
        //System.out.println("AMount per item: " + amountPerItemY);
        graphics.setColor(Color.black);
        for (int i = 0; i < itemsY; i++) {
        	int y = (int)(h - (h * ((double)i / itemsY)));

        	graphics.drawString("" + (int)(i*amountPerItemY), 0, y);
        }

        int totalMinutes = 0;
        if (store.getItems().size() > 0) {
        	StorageItem lastItem = store.getItems().get(store.getItems().size()-1);
        	System.out.println(lastItem);
        	totalMinutes = lastItem.minute + (lastItem.hour*60) + (lastItem.day*60*24);
        }

        int itemsX = 15;
        double amountPerItemX = (double)totalMinutes / itemsX;
        //System.out.println("AMount per item: " + amountPerItemX);
        graphics.setColor(Color.black);
        for (int i = 0; i < itemsX; i++) {
        	int x = (int) ((double)i / itemsX * w);

        	int val = (int)(i*amountPerItemX);
        	if (val / 60 < 1) {
        		graphics.drawString(val + "m", x, h);
        	} else if (val / 60 / 24 < 1) {
        		graphics.drawString(val/60 + "h" + val%60 + "m", x, h);
        	} else {
        		graphics.drawString(val/60/24 + "d" + val/60 + "h", x, h);
        	}
        }

        int i = 0;
        for (StorageItem item : items) {
        	int j = 0;
        	for (CarAmount am : item.carTypeAmount) {
        		if (am.carColor == null) {
        			continue;
        		}

        		int x = (int) ((this.getSize().getWidth() / amount) * i);
        		int y = (int)(this.getSize().getHeight() - (this.getSize().getHeight() * (am.amount / (double)highest)));
        		//int y = (int)this.getSize().getHeight()-(int)(am.amount / highest * this.getSize().getHeight());
        		//int x = (int)(i / amount * this.getSize().getWidth());
        		//System.out.println(x + " - " + y);
        		if (am.carColor != null) {
        			graphics.setColor(am.carColor);
        		} else {
        			graphics.setColor(Color.gray);
        		}

        		Coords lastPos = lastPositions[j];
        		if (lastPos == null) {
        			lastPos = new Coords();
        		}

        		graphics.fillRect(x-1, y-1, 1, 1);
        		graphics.drawLine(lastPos.x, lastPos.y, x, y);

        		lastPos.x = x;
        		lastPos.y = y;
        		lastPositions[j] = lastPos;

        		j++;
        	}
        	i++;
        }

        repaint();
    }

}

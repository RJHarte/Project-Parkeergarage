package Parkeersimulator.Models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Parkeersimulator.view.AbstractView;
import Parkeersimulator.view.CarParkView;
import Parkeersimulator.Models.Simulator;

/**
 * Hoi dit is een comment
 */
public class ParkingLot implements Iterable<Car> {

	private List<AbstractView> views;
    private CarParkView carParkView;

    private int numberOfFloors;
    private int numberOfRows;
    
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    
    private int numberOfPassPlaces;
    private int numberOfOpenPassSpots;
    
    private int numberOfReservePlaces;
    private int numberOfOpenReserveSpots;
    
    private Car[][][] cars;
    
    public ParkingLot(int numberOfFloors, int numberOfRows, int numberOfPlaces, int numberOfPassPlaces, int numberOfReservePlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = (numberOfFloors*numberOfRows*numberOfPlaces)-numberOfPassPlaces;
        
        this.numberOfPassPlaces = numberOfPassPlaces;
        this.numberOfOpenPassSpots = numberOfPassPlaces;
        
        this.numberOfReservePlaces = numberOfReservePlaces;
        this.numberOfOpenReserveSpots = numberOfReservePlaces;
        
        this.cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        this.views = new ArrayList<AbstractView>();
    }

	public void addView(AbstractView view)
	{
		this.views.add(view);
	}

    public void updateView() {
		for (AbstractView v : this.views) {
        	v.updateView();
		}
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
    
    public int getNumberOfReservePlaces() {
    	return this.numberOfReservePlaces;
    }
    
    public int getNumberOfPassPlaces() {
    	return this.numberOfPassPlaces;
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
        
        if (car instanceof ParkingPassCar ) {
        	this.numberOfOpenPassSpots--;
        }
        else if (car instanceof ReservedCar ) {
        	this.numberOfOpenReserveSpots--;
        }
        else if (car instanceof AdHocCar ) {
        	this.numberOfOpenSpots--;
        }
        
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
        
        // opens up spots in relation to car type 
        if (car instanceof ParkingPassCar ) {
        	this.numberOfOpenPassSpots++;
        }
        else if (car instanceof ReservedCar ) {
        	this.numberOfOpenReserveSpots++;
        }
        else if (car instanceof AdHocCar ) {
        	this.numberOfOpenSpots++;
        }
        
        return car;
    }

    /**
     * Determine the first free parking spot location.
     *
     * @return The free spot. Null if no free spot.
     */
    public Location getFirstFreeLocation(Car car) {
    	
        for (int floor = 0; floor < this.getNumberOfFloors(); floor++) {
            for (int row = 0; row < this.getNumberOfRows(); row++) {
                for (int place = 0; place < this.getNumberOfPlaces(); place++) {
                	
                	Location location = new Location(floor, row, place);
                	setAllPassPlaces(location,0,this.numberOfPassPlaces/3);
                	
                	boolean isPassPlace = locationIsPassPlace(location);
                	boolean isReserved = locationIsReservedPlace(location);
                	

                	// if there's not car at location return location
                	if(car instanceof ParkingPassCar) {
                		if (this.numberOfOpenPassSpots > 0) {
                			if (isPassPlace) {
		                		if (this.getCarAt(location) == null) {
		                            return location;
		                		}
                			}
	                	}
                		else if (this.getCarAt(location) == null) {
                            return location;
                		}
                	}
                	else if(car instanceof ReservedCar) {
                		if (!isPassPlace) {
	                		if (this.numberOfOpenReserveSpots > 0 && isReserved){
		                		if (this.getCarAt(location) == null) {
		                            return location;
		                        }
	                		}
	                		else if (this.getCarAt(location) == null) {
	                            return location;
	                		}
                		}
                	}
                	else if(car instanceof AdHocCar) {
                		if (!isPassPlace && !isReserved) {
	                		if (this.getCarAt(location) == null) {
	                            return location;
	                        }
	                	}
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
                	setAllPassPlaces(location,0,this.numberOfPassPlaces/3);
                	boolean isPassPlace = locationIsPassPlace(location);
                	boolean isReserved = locationIsReservedPlace(location);
                	
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
                	setAllPassPlaces(location,0,this.numberOfPassPlaces/3);
                	boolean isPassPlace = locationIsPassPlace(location);
                	boolean isReserved = locationIsReservedPlace(location);
                	
                    Car car = this.getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    public CarAmount[] calculateAmountOfCars()
    {
    	CarAmount adHoc = new CarAmount();
    	adHoc.carName = "AdHoc";
    	adHoc.carColor = (new AdHocCar()).getColor();

    	CarAmount reservedCar = new CarAmount();
    	reservedCar.carName = "Reserved";
    	reservedCar.carColor = (new ReservedCar()).getColor();

    	CarAmount parkingPassCar = new CarAmount();
    	parkingPassCar.carName = "Pass";
    	parkingPassCar.carColor = (new ParkingPassCar()).getColor();

		CarAmount none = new CarAmount();

    	CarAmount[] amounts = new CarAmount[]{
    		adHoc, reservedCar, parkingPassCar, none,
    	};

    	// TODO: Loop over cars.
		for (Car c : this) {
			if (c == null) {
				amounts[3].amount++;
			} else if (c instanceof AdHocCar) {
				amounts[0].amount++;
			} else if (c instanceof ReservedCar) {
				amounts[1].amount++;
			} else if (c instanceof ParkingPassCar) {
				amounts[2].amount++;
			}
		}

		return amounts;
    }

	@Override
	public Iterator<Car> iterator()
	{
		return new ParkingLotSpotIterator();
	}

    public class CarAmount
    {
    	public String carName;
    	public Color carColor;
    	public int amount;
    }

	public class ParkingLotSpotIterator implements Iterator<Car>
	{
		private int curFloor = 0;
		private int curRow = 0;
		private int curSpot = 0;

		@Override
		public boolean hasNext()
		{
			if (this.curFloor+1 < numberOfFloors) {
				return true;
			}

			if (this.curRow+1 < numberOfRows) {
				return true;
			}

			if (this.curSpot+1 < numberOfPlaces) {
				return true;
			}

			return false;
		}

		@Override
		public Car next()
		{
			this.curSpot++;
			if (this.curSpot >= numberOfPlaces) {
				this.curSpot = 0;
				this.curRow++;
			}

			if (this.curRow >= numberOfRows) {
				this.curRow = 0;
				this.curFloor++;
			}

			if (this.curFloor >= numberOfFloors) {
				throw new RuntimeException("No");
			}

			return cars[this.curFloor][this.curRow][this.curSpot];
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
    
    public boolean locationIsPassPlace(Location location) {
    	return location.getPassPlace();
    }
    
    public boolean locationIsReservedPlace(Location location) {
    	return location.getReservedPlace();
    }

	public int getAmountOfCars() {
		return (numberOfFloors * numberOfRows * numberOfPlaces) - numberOfOpenSpots;
	}
	
	public void setAllPassPlaces(Location location, int row, int place) {
		if (location.getRow() == row && location.getPlace() < place) {
    		location.setPassPlace(true);
    	}
	}
}


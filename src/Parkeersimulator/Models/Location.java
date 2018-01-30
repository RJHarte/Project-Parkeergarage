package Parkeersimulator.Models;

public class Location {

    private int floor;
    private int row;
    private int place;
    private boolean passPlace;

    /**
     * Constructor for objects of class Location
     */
    public Location(int floor, int row, int place, boolean passPlace) {
        this.floor = floor;
        this.row = row;
        this.place = place;
        this.passPlace = passPlace;
    }

    /**
     * Implement content equality.
     *
     * @param Object to compare with.
     * @return does it equal? That is the question.
     */
    @Override
	public boolean equals(Object obj) {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return this.floor == other.getFloor() &&
            		this.row == other.getRow() &&
            		this.place == other.getPlace() &&
            		this.passPlace == other.getPassPlace();
        }
        else {
            return false;
        }
    }

    /**
     * Return a string of the form floor,row,place.
     * @return A string representation of the location.
     */
    @Override
	public String toString() {
        return floor + "," + row + "," + place;
    }

    /**
     * Use the 10 bits for each of the floor, row and place
     * values. Except for very big car parks, this should give
     * a unique hash code for each (floor, row, place) tupel.
     *
     * @return A hashcode for the location.
     */
    @Override
	public int hashCode() {
        return (floor << 20) + (row << 10) + place;
    }

    /**
     * @return The floor.
     */
    public int getFloor() {
        return this.floor;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return The place.
     */
    public int getPlace() {
        return this.place;
    }
    
    /*
     * @return if place is passholder place
     */
    public boolean getPassPlace() {
    	return this.passPlace;
    }
    
    public void setPassPlace(boolean passPlace) {
    	this.passPlace = passPlace;
    }

}
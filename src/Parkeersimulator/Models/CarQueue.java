package Parkeersimulator.Models;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Wrapper around a LinkedList queue of Cars.
 */
public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    /**
     * Add car to the queue.
     *
     * @param car
     *
     * @return false if things broke
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }

    /**
     * Remove the longest first car in the queue from the queue.
     *
     * @return the removed car
     */
    public Car removeCar() {
        return queue.poll();
    }

    /**
     * Get amount of cars currently in the queue.
     *
     * @return amount of cars
     */
    public int carsInQueue(){
    	return queue.size();
    }
}
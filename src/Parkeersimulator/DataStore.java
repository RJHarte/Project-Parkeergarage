package Parkeersimulator;

import java.util.ArrayList;

import Parkeersimulator.Models.ParkingLot.CarAmount;

/**
 * Class DataStore stores an ArrayList of DataStore.StorageItem representing the
 * history of the state of the simulator.
 * DataStore can be used for displaying a graph displaying the history of the simulation.
 */
public class DataStore
{
	// TODO: Singleton is not needed, so remove the singleton functionality.
	// Singleton is in my opinion is only making this class more complicated to use
	// than it has to be.

	private static DataStore instance;

	private static ArrayList<StorageItem> items;

	private DataStore()
	{
		this.items = new ArrayList<StorageItem>();
	}

	public static DataStore getInstance()
	{
		return instance;
	}

	public static DataStore createInstance()
	{
		return instance = new DataStore();
	}

	/**
	 * addItem appends a StorageItem to the internal ArrayList.
	 *
	 * @param item
	 */
	public void addItem(StorageItem item)
	{
		this.items.add(item);
	}

	/**
	 * getItems returns the internal ArrayList of StorageItem representing the
	 * history of the simulation.
	 *
	 * @return the internal ArrayList of StorageItem
	 */
	public ArrayList<StorageItem> getItems()
	{
		return this.items;
	}

	/**
	 * Class StorageItem represents the state of the simulation at a certain time.
	 */
	public class StorageItem
	{
		// Time and date of which this StorageItem represents the simulation state.

		public int minute;
		public int hour;

		public int day; // Day of the simulation. 0...alot

		public CarAmount[] carTypeAmount;

		// TODO: Add income/money data.

		@Override
		public String toString()
		{
			String carAmount = "";
			for (CarAmount amount : this.carTypeAmount) {
				carAmount += String.format("%s => %d; ", amount.carName, amount.amount);
			}

			return String.format("%02d:%02d %03d: %s", this.hour, this.minute, this.day, carAmount);
		}
	}
}

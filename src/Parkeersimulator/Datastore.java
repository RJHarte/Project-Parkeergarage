package Parkeersimulator;

import java.util.ArrayList;

import Parkeersimulator.Models.ParkingLot.CarAmount;

public class Datastore
{
	// TODO: Singleton is not needed, so remove the singleton functionality.
	// Singleton is in my opinion is only making this class more complicated to use than it has to be.

	private static Datastore instance;

	private static ArrayList<StorageItem> items;

	private Datastore()
	{
		this.items = new ArrayList<StorageItem>();
	}

	public static Datastore getInstance()
	{
		return instance;
	}

	public static Datastore createInstance()
	{
		return instance = new Datastore();
	}

	public void addItem(StorageItem item)
	{
		this.items.add(item);
	}

	public ArrayList<StorageItem> getItems()
	{
		return this.items;
	}

	public class StorageItem
	{
		public int minute;
		public int hour;

		public int day; // Day of the simulation. 0...alot

		public CarAmount[] carTypeAmount;

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

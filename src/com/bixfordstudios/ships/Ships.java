package com.bixfordstudios.ships;


/**
 * Class that contains all types of ships.
 * @author DarkEther
 *
 */
public class Ships {
	
	private int[] shipArray = new int[ShipType.values().length + 1];
	
	public Ships() {}
	
	public int getTotalQuantity()
	{
		int ret = 0;
		for (int i : shipArray)
		{
			ret += i;
		}
		return ret;
	}
	
	public int getQuantity(ShipType shipType)
	{
		return shipArray[shipType.ordinal()];
	}
	
	public void setQuantityOf(ShipType shipType, int quantity)
	{
		shipArray[shipType.ordinal()] = quantity;
	}
}

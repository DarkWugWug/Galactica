package com.bixfordstudios.defenses;


/**
 * Class that contains all types of defense structures.
 * @author DarkEther
 *
 */
public class Defenses {
	
	private int[] defenseArray = new int[DefenseType.values().length + 1];
	
	public Defenses() {}
	
	public int getQuantity(DefenseType defenseType)
	{
		return defenseArray[defenseType.ordinal()];
	}
	
	public void setQuantityOf(DefenseType defenseType, int quantity)
	{
		defenseArray[defenseType.ordinal()] = quantity;
	}
	
	
}

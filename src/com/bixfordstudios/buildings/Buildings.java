package com.bixfordstudios.buildings;


/**
 * Class that contains all types of buildings.
 * @author DarkEther
 *
 */
public class Buildings {
	
	public int usedFields = -1;
	public int totalFields = -1;
	
	private int[] buildingArray = new int[BuildingType.values().length + 1];
	
	public Buildings() {}
	
	public int getQuantityOf(BuildingType buildingType)
	{
		return buildingArray[buildingType.ordinal()];
	}
	
	public void setQuantityOf(BuildingType buildingType, int quantity)
	{
		buildingArray[buildingType.ordinal()] = quantity;
	}
	
	
}

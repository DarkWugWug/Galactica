package com.bixfordstudios.technologies;


public class Technologies {

	private int[] techArray = new int[TechnologyType.values().length + 1];
	
	public Technologies() {}
	
	public int getQuantityOf(TechnologyType technologyType)
	{
		return techArray[technologyType.ordinal()];
	}
	
	public void setQuantityOf(TechnologyType technologyType, int quantity)
	{
		techArray[technologyType.ordinal()] = quantity;
	}
}

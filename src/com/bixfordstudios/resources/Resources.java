package com.bixfordstudios.resources;

/**
 * Class that contains longs for raw resources -- metal, crystal, and deuterium -- and integers for energy production and use.
 * @author DarkEther
 *
 */
public class Resources {
	
	public long metal, crystal, deuterium;
	public int energyProduced, energyTotal;
	
	public Resources() {}
	
	public Resources(long metal, long crystal, long deuterium, int energyProduced, int energyTotal)
	{
		this.metal = metal;
		this.crystal = crystal;
		this.deuterium = deuterium;
		this.energyProduced = energyProduced;
		this.energyTotal = energyTotal;
	}
	
	public Resources(long metal, long crystal, long deuterium)
	{
		this(metal, crystal, deuterium, 0, 0);
	}
}

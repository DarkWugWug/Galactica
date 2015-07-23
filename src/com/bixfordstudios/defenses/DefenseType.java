package com.bixfordstudios.defenses;

import com.bixfordstudios.detail.Detail;

public enum DefenseType {
	ROCKET_LAUNCHER("Rocket Launcher", "RL"),
	LIGHT_LASER("Light Laser", "LL"),
	HEAVY_LASER("Heavy Laser", "HL"),
	GAUSS_CANNON("Gauss Cannon", "GC"), 
	ION_CANNON("Ion Cannon", "IC"), 
	PLASMA_CANNON("Plasma Cannon", "PC"), 
	SMALL_SHIELD_DOME("Small Shield Dome", "SSD"),
	LARGE_SHIELD_DOME("Large Shield Dome", "LSD"), 
	ANTIBALLISTIC_MISSILE("Antiballistic Missile", "ABM"),
    INTERPLANETARY_MISSILE("Interplanetary Missile", "IPM");
	
	public Detail details = new Detail();
	
	DefenseType(String name, String shortName)
	{
		this.details = new Detail(name, shortName);
	}
	
	/**
	 * Compares the given string to the constants name and returns the first it matches. Else it returns null.<p>
	 * Trims and converts the string to lower case to compare to the constant in lower case.
	 * @param string A string
	 * @return A constant or null if there was none to match.
	 */
	public static DefenseType getType(String name)
	{
		for (DefenseType type : DefenseType.values())
		{
			if (type.details.name.toLowerCase().equals(name.trim().toLowerCase())) return type;
		}
		
		return null;
	}
}

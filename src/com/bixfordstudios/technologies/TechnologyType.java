package com.bixfordstudios.technologies;

import com.bixfordstudios.detail.Detail;

public enum TechnologyType {
	ESPIONAGE("Espionage Technology", "ESP"),
	COMPUTER("Computer Technology", "COMP"), 
	WEAPONS("Weapons Technology", "WEAP"), 
	SHIELD("Shield Technology", "SHLD"), 
	ARMOR("Armor Technology", "ARMR"), 
	ENERGY("Energy Technology", "ENRG"), 
	HYPERSPACE("Hyperspace Technology", "HYST"), 
	COMBUSTION_ENGINE("Combustion Engine", "CMBE"), 
	IMPULSE_ENGINE("Impulse Engine", "IMPE"), 
	HYPERSPACE_ENGINE("Hyperspace Engine", "HYSE"),
    LASER("Laser Technology", "LASR"), 
    ION("Ion Technology", "ION"), 
    PLASMA("Plasma Technology", "PLSM"), 
    INTERGALACTIC_RESEARCH_NETWORK("Intergalactic Research Network", "IRN"), 
    EXPEDITION("Expedition Technology", "EXPD"), 
    ZORG_PHYSICS("Zorg Physics", "ZPYS"), 
    GRAVITON("Graviton Technology", "GRAV");
	
	public Detail details = new Detail();
	
	TechnologyType(String name, String shortName)
	{
		this.details = new Detail(name, shortName);
	}
	
	/**
	 * Compares the given string to the constants name and returns the first it matches. Else it returns null.<p>
	 * Trims and converts the string to lower case to compare to the constant in lower case.
	 * @param string A string
	 * @return A constant or null if there was none to match.
	 */
	public static TechnologyType getType(String name)
	{
		for (TechnologyType type : TechnologyType.values())
		{
			if (type.details.name.toLowerCase().equals(name.trim().toLowerCase())) return type;
		}
		
		return null;
	}
}

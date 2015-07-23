package com.bixfordstudios.ships;

import com.bixfordstudios.detail.Detail;

public enum ShipType {
	SMALL_CARGO_SHIP("Small Cargo Ship", "SC"),
	LARGE_CARGO_SHIP("Large Cargo Ship", "LC"),
	LIGHT_FIGHTER("Light Fighter", "LF"),
	HEAVY_FIGHTER("Heavy Fighter", "HF"),
	CRUISER("Cruiser", "C"),
	BATTLESHIP("Battleship", "BS"),
	COLONY_SHIP("Colony Ship", "CS"),
	RECYCLER("Recycler", "R"),
	ESPIONAGE_SHIP("Espionage Ship", "ESP"),
	BOMBER("Bomber", "B"),
	SOLAR_SATELLITE("Solar Satellite", "SS"),
    DESTROYER("Destroyer", "D"),
    DEATH_STAR("Death Star", "DS"),
    BATTLECRUSIER("Battlecruiser", "BC"),
    LUNAR_GUARDIAN("Lunar Guardian", "LG"),
    ELITE_CARGO("Elite Cargo", "EC"),
    ELITE_RECYCLER("Elite Recycler", "ER");
	
	public Detail details = new Detail();
	
	ShipType(String name, String shortName) 
	{
		this.details = new Detail(name, shortName);
	}
	
	/**
	 * Compares the given string to the constants name and returns the first it matches. Else it returns null.<p>
	 * Trims and converts the string to lower case to compare to the constant in lower case.
	 * @param string A string
	 * @return A constant or null if there was none to match.
	 */
	public static ShipType getType(String name)
	{
		for (ShipType shipType : ShipType.values())
		{
			if (shipType.details.name.toLowerCase().equals(name.trim().toLowerCase())) return shipType;
		}
		
		return null;
	}
}

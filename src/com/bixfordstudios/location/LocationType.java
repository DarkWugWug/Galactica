package com.bixfordstudios.location;

import com.bixfordstudios.detail.Detail;

/**
 * Defines the different types of locations along with gui friendly names.
 * @author DarkEther
 *
 */
public enum LocationType {
	//Check If Outer-Space (exploration) is a different field
	PLANET("Planet", "P"),
	DEBRIS("Debris", "DF"),
	MOON("Moon", "M");
	
	public Detail details = new Detail();
	
	LocationType(String name, String shortName)
	{
		this.details = new Detail(name, shortName);
	}
	
	/**
	 * Compares the given string to the constants name and returns the first it matches. Else it returns null.<p>
	 * Trims and converts the string to lower case to compare to the constant in lower case.
	 * @param string A string
	 * @return A constant or null if there was none to match.
	 */
	public static LocationType getType(String name)
	{
		for (LocationType type : LocationType.values())
		{
			if (type.details.name.toLowerCase().equals(name.trim().toLowerCase())) return type;
		}
		
		return null;
	}
	
	/**
	 * Splits a given string on all it's spaces and then compares each word to the constant's name via {@link #getType(String)}.
	 * @param sentence A string (assumed with spaces)
	 * @return The first matching location type in the sentence
	 */
	public static LocationType getTypeWithin(String sentence)
	{
		for (String string : sentence.split(" "))
		{
			if (getType(string) != null) return getType(string);
		}
		return null;
	}
}

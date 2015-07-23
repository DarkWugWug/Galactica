package com.bixfordstudios.flight;

public enum FlightMission {
	//Explore?
	ATTACK("Attack"),
	ACS_ATTACK("ACS Attack"),
	TRANSPORT("Transport"),
	DEPLOY("Deploy"),
	ACS_DEFEND("ACS Defend"),
	ESPIONAGE("Espionage"),
	COLONISE("Colonise");
	
	public String name;
	
	FlightMission(String name)
	{
		this.name = name;
	}
	
	/**
	 * Compares the given string to the constants name and returns the first it matches. Else it returns null.<p>
	 * Trims and converts the string to lower case to compare to the constant in lower case.
	 * @param string A string
	 * @return A constant or null if there was none to match.
	 */
	public static FlightMission getMission(String string)
	{
		for (FlightMission mission : FlightMission.values())
		{
			if (mission.name.toLowerCase().equals(string.trim().toLowerCase())) return mission;
		}
		
		return null;
	}
}

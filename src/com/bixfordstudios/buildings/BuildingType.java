package com.bixfordstudios.buildings;

import com.bixfordstudios.detail.Detail;
import com.bixfordstudios.location.LocationType;

public enum BuildingType {
	METAL_MINE("Metal Mine", "MM"),
	CRYSTAL_MINE("Crystal Mine", "CM"), 
	DEUTERIUM_SYNTHESIZER("Deuterium Synthesizer", "DSyn"), 
	SOLAR_PLANT("Solar Plant", "SolP"), 
	FUSION_REACTOR("Fusion Reactor", "FusR"), 
	ROBOT_FACTORY("Robot Factory", "RF"), 
	NANITE_FACTORY("Nanite Factory", "NF"), 
	SHIP_YARD("Ship Yard", "SYard"), 
	METAL_STORAGE("Metal Storage", "MSto"), 
	CRYSTAL_STORAGE("Crystal Storage", "CSto"),
    DEUTERIUM_TANK("Deuterium Tank", "DSto"), 
    RESEARCH_LAB("Research Lab", "RL"), 
    TERRAFORMER("Terraformer", "Tf"), 
    ACS_DEPOT("ACS Depot", "ACSD"), 
    MISSILE_SILO("Missile Silo", "MSlo"), 
    LUNAR_BASE("Lunar Base", "LB"), 
    SENSOR_PHALANX("Sensor Phalanx", "SenPx"), 
    JUMP_GATE("Jump Gate", "JG");
	
	public Detail details = new Detail();
	
	BuildingType(String name, String shortName)
	{
		this.details = new Detail(name, shortName);
	}
	
	/**
	 * Compares the given string to the constants name and returns the first it matches. Else it returns null.<p>
	 * Trims and converts the string to lower case to compare to the constant in lower case.
	 * @param string A string
	 * @return A constant or null if there was none to match.
	 */
	public static BuildingType getType(String name)
	{
		for (BuildingType type : BuildingType.values())
		{
			if (type.details.name.toLowerCase().equals(name.trim().toLowerCase())) return type;
		}
		
		return null;
	}
}

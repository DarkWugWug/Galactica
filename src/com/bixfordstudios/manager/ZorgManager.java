package com.bixfordstudios.manager;

import com.bixfordstudios.buildings.BuildingType;
import com.bixfordstudios.empire.Empire;
import com.bixfordstudios.planet.Planet;
import com.bixfordstudios.zorg.Server;
import com.bixfordstudios.zorg.Zorg;

/**
 * Highest level manager of functions.
 * @author DarkEther
 *
 */
public class ZorgManager {
	
	/**
	 * Variable does not have a setter as it has to be set by updating.
	 */
	private static Empire empire = new Empire();
	
	public static Empire getEmpire() {
		return empire;
	}

	public static boolean login(Server server, String name, char[] password)
	{
		Zorg.setServer(server);
		Zorg.login(name, password);
		
		if(Zorg.isLoggedIn()) 
		{
			LogManager.record("[ZorgMnrg] "+ name +" successfully logged in!");
			return true;
		}
		else 
		{
			LogManager.record("[ZorgMnrg] "+ name +" failed to login!");
			return false;
		}
		
	}
	
	/**
	 * Updates all variables of the empire -- flights, planets, and technologies.
	 */
	public static void updateEmpire()
	{
		updateFleet();
		updatePlanets();
		updateTechnologies();
	}
	
	/**
	 * Updates the current empire's flight details. See: {@link Zorg#getAllFlights()}.
	 */
	public static void updateFleet()
	{
		if (Zorg.isLoggedIn()) empire.flightList = Zorg.getAllFlights();
	}
	
	/**
	 * Updates the current empire's planet details. See: {@link Zorg#getAllPlanets()}.
	 */
	public static void updatePlanets()
	{
		if (Zorg.isLoggedIn()) empire.planetList = Zorg.getAllPlanets();
	}
	
	/**
	 * Updates the current empire's technologies.<p>
	 * Uses the planet with the highest level research lab as only that planet will have all possible technologies the player might have. By iterating through the current listings
	 * of planets that the empire has.
	 */
	public static void updateTechnologies()
	{
		
		
		Planet greatestPlanet = empire.planetList.get(0); 
		for (Planet planet : empire.planetList)
		{
			if (planet.buildings.getQuantityOf(BuildingType.RESEARCH_LAB) > greatestPlanet.buildings.getQuantityOf(BuildingType.RESEARCH_LAB)) greatestPlanet = planet;
		}
		if (Zorg.isLoggedIn()) empire.technologies = Zorg.getTechnologies(greatestPlanet);		
	}
}

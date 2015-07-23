package com.bixfordstudios.empire;

import java.util.ArrayList;

import com.bixfordstudios.flight.Flight;
import com.bixfordstudios.planet.Planet;
import com.bixfordstudios.technologies.Technologies;

public class Empire {
	
	public ArrayList<Planet> planetList = new ArrayList<Planet>();
	public ArrayList<Flight> flightList = new ArrayList<Flight>();
	public Technologies technologies = new Technologies();
	
	public Empire() {}
	
	public Empire(ArrayList<Planet> planetList, ArrayList<Flight> flightList, Technologies technologies) 
	{
		this.planetList = planetList;
		this.flightList = flightList;
		this.technologies = technologies;
	}	
}

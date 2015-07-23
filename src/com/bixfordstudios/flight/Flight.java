package com.bixfordstudios.flight;

import java.util.Date;

import com.bixfordstudios.location.Location;
import com.bixfordstudios.resources.Resources;
import com.bixfordstudios.ships.Ships;

public class Flight {
	
	public FlightMission mission;
	public FlightType type;
	public Ships ships = new Ships();
	public Resources resources = new Resources();
	public Location departureLocation, targetLocation = new Location();
	public Date departureTime, arrivalTime, returnTime, modifiedTime = new Date();
	public float speed;
	public boolean isReturning;
	
	public Flight() {}
	
	public Flight(FlightMission mission, FlightType type, Ships ships, Resources resources, Location departureLocation,
			Location targetLocation, Date departureTime, Date arrivalTime, Date returnTime, Date modifiedTime, float speed, boolean isReturning) 
	{
		this.mission = mission;
		this.type = type;
		this.ships = ships;
		this.resources = resources;
		this.departureLocation = departureLocation;
		this.targetLocation = targetLocation;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.returnTime = returnTime;
		this.modifiedTime = modifiedTime;
		this.speed = speed;
		this.isReturning = isReturning;
	}

	
	
}

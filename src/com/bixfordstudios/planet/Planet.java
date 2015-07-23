package com.bixfordstudios.planet;

import java.util.Date;

import com.bixfordstudios.buildings.Buildings;
import com.bixfordstudios.defenses.Defenses;
import com.bixfordstudios.location.Location;
import com.bixfordstudios.resources.Resources;
import com.bixfordstudios.ships.Ships;

/**
 * Class that pulls together all objects that make up a planet.
 * @author DarkEther
 *
 */
public class Planet {

	public String name, listId;
	public Resources resources = new Resources();
	public Location location = new Location();
	public Buildings buildings = new Buildings();
	public Defenses defenses = new Defenses();
	public Ships ships = new Ships();
	public Date modifiedTime = new Date();
	
	public Planet() {}
	
	public Planet(String name, String listId, Resources resources,
			Location location, Buildings buildings, Defenses defenses,
			Ships ships, Date modifiedTime) {
		this.name = name;
		this.listId = listId;
		this.resources = resources;
		this.location = location;
		this.buildings = buildings;
		this.defenses = defenses;
		this.ships = ships;
		this.modifiedTime = modifiedTime;
	}
	
}

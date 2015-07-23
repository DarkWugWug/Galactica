package com.bixfordstudios.location;

public class Location {
	
	public int galaxy, system, planetNumber;
	public LocationType type;
	
	//Lower limits are all 1
	//Warning: Only tested on speed universe
	public static final int GALAXY_UPPER_LIMIT = 9;
	public static final int SYSTEM_UPPER_LIMIT = 365;
	public static final int PLANET_NUM_UPPER_LIMIT = 16;
	
	public Location() {}
	
	/**
	 * Constructor for Location.
	 * @param galaxy An int between 1 and {@link #GALAXY_UPPER_LIMIT}
	 * @param system An int between 1 and {@link #SYSTEM_UPPER_LIMIT}
	 * @param planetNumber An int between 1 and {@link #PLANET_NUM_UPPER_LIMIT}
	 * @param type A {@link LocationType}
	 */
	public Location(int galaxy, int system, int planetNumber, LocationType type)
	{
		if (isValid(galaxy, system, planetNumber))
		{
			this.galaxy = galaxy;
			this.system = system;
			this.planetNumber = planetNumber;
			this.type = type;
		} 
		else
		{
			throw new AssertionError("For " + this.toString() + "an index is non-valid!");
		}
	}
	
	/**
	 * Constructor for Location. Uses {@link #toCoordinates(String)} for string conversion.
	 * @param string A string in form [galaxy:system:planetNumber]
	 * @param type A {@link LocationType}
	 */
	public Location(String string, LocationType type)
	{
		setCoordinates(string);
        this.type = type;
	}
	
	private boolean isValid(int galaxy, int system, int planetNumber)
	{
		if (!(galaxy > 1 && galaxy < GALAXY_UPPER_LIMIT)) return false;
		if (!(system > 1 && system < SYSTEM_UPPER_LIMIT)) return false;
		if (!(planetNumber > 1 && planetNumber < PLANET_NUM_UPPER_LIMIT)) return false;
		return true;
	}
	
	/**
	 * Sets variables from a string in form [galaxy:system:planetNumber].
	 * @param coord A string
	 */
	public void setCoordinates(String coord)
    {
        String[] composite = coord.replace("]  ", "").split(":");
        String galaxy = composite[0].replaceAll("[^0-9]", "");
        String system = composite[1].replaceAll("[^0-9]", "");
        String planet = composite[2].replaceAll("[^0-9]", "");
        
        this.galaxy = Integer.parseInt(galaxy);
        this.system = Integer.parseInt(system);
        this.planetNumber = Integer.parseInt(planet);
    }
	
	/**
	 * Checks if the object is of the class Location. Then, if they match in galaxy, system, planetNumber, and type.
	 */
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		
		if (obj.getClass() != getClass()) return false;
		
		Location location = (Location) obj;
		if (this.galaxy == location.galaxy
				&& this.system == location.system
				&& this.planetNumber == location.planetNumber
				&& this.type == location.type) return true;
		else return false;
	}
	
	/**
	 * Converts the Location to a String in format:<br>
	 * [galaxy:system:planetNumber] -- type
	 */	
	public String toString()
	{
		return "["+ this.galaxy +":"+ this.system +":"+ this.planetNumber +"] -- "+ this.type.details.name;
	}
	
	/**
	 * Converts the Location to a String in format:<br>
	 * [galaxy:system:planetNumber] -- type
	 */	
	public String coordsToString()
	{
		return "["+ this.galaxy +":"+ this.system +":"+ this.planetNumber +"]";
	}
}

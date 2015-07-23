package com.bixfordstudios.zorg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.bixfordstudios.buildings.BuildingType;
import com.bixfordstudios.defenses.DefenseType;
import com.bixfordstudios.flight.Flight;
import com.bixfordstudios.flight.FlightMission;
import com.bixfordstudios.flight.FlightType;
import com.bixfordstudios.location.Location;
import com.bixfordstudios.location.LocationType;
import com.bixfordstudios.manager.LogManager;
import com.bixfordstudios.planet.Planet;
import com.bixfordstudios.ships.ShipType;
import com.bixfordstudios.technologies.Technologies;
import com.bixfordstudios.technologies.TechnologyType;
import com.bixfordstudios.utils.DateUtils;

/**
 * Web navigation and interface to zorg empire
 * 
 * @author Nolan E. Rosen
 */
public class Zorg extends HtmlUnitDriver implements WebDriver {
	
	//Meta to use the functionality of HtmlUnitDriver and WebDriver in an all static class
	private static Zorg driver = new Zorg();
	
	public Zorg() 
	{
		this.setJavascriptEnabled(true);
		this.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	private static Server curServer;
	
	/**
	 * Sets the active server from {@link Server}
	 * @param server A server
	 */
	public static void setServer(Server server)
	{
		curServer = server;
	}
	
	/**
	 * Gets the active server from {@link Server}
	 * @param server A server
	 */
	public static Server getServer()
	{
		return curServer;
	}
	
	/**
	 * Navigates to the url of the requested page from {@link Page} on the active server if not already on the requested page
	 * @param page A page
	 * @return True, had to load new page; false, currently on the page requested (no action taken)
	 */
	public static boolean load(Page page)
	{
		if (!driver.getCurrentUrl().contains(curServer.url + page.url))
		{
			assertLoad(page);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Forces the driver to load a page
	 * @param page A page
	 */
	public static void assertLoad(Page page)
	{
		driver.get(curServer.url + page.url);
	}
	
	/**
	 * Attempts a login for the given user and password. The password is cleared after attempt.
	 * @param username A string
	 * @param password A character array (for security purposes)
	 */
	public static void login(String username, char[] password)
	{
		assertLoad(Page.LOGIN);
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(String.valueOf(password));
		driver.findElement(By.name("submit")).click();
	}
	
	/**
	 * Checks if the user can navigate to the overview page without being redirected.
	 */
	public static boolean isLoggedIn()
	{
		//Reloading the login page after a successful log it will direct the driver to Page.OVERVIEW
		assertLoad(Page.LOGIN);
		
		if (driver.getCurrentUrl().contains(Page.LOGIN.url))
		{
			//Reloading of the login page stuck; user has failed the check
			LogManager.record("[Zorg] Login is no longer maintained! Has been redirected to the login page.");
			return false;
		}
		else if (load(Page.OVERVIEW))
		{
			//Reloading of the login page was redirected to overview as expected			
			String htmlHeadTitle;
			try
			{
				htmlHeadTitle = driver.findElement(By.xpath("/html/head/title")).getText();
			}
			catch (NoSuchElementException e)
			{
				LogManager.record("[Zorg] Login check failed due to failure to find a element for the title.");
				return false;
			}
			
			if (htmlHeadTitle.contains("Login Required! The best space strategy game!")
					|| htmlHeadTitle.contains("Zorg Empire Accoutn Security Gaurd"))
			{
				//Either the user timed out or logged out; user has failed the check
				LogManager.record("[Zorg] Login has been terminated by time-out or a log out (Title contains redirect messages).");
				return false;
			}
			
			if (htmlHeadTitle.contains("Bot Protection The best space strategy game!"))
			{
				//Anti-bot function call here
				LogManager.record("[Zorg] User is effectively no longer logged in due to anti-bot test.");
				return false;
			}
			
			if (htmlHeadTitle.contains("The best space strategy game!"))
			{
				//User is logged it
				LogManager.record("[Zorg] Login check successful!");
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check is there is a row in the overview page that has the attribute 'messages.php' for any hyper-links
	 * @return A boolean
	 */
	public static boolean hasMail()
	{
		if (driver.findElements(By.xpath("/html/body/center[2]/center/table[2]/tbody/tr[2]/th//a[@href = 'messages.php']")).size() == 0)
		{
			//There was no mail in the bar that pops up at the top of the overview page
			return false;
		} 
		else
		{
			//Message bar found (Size == 1)
			return true;
		}
	}

	public static final int OVERVIEW_MAIL_ROWS_IN_TABLE = 1;
	public static final int OVERVIEW_TABLE_ROWS = 3;
	
	/**
	 * Determines the number of flight readouts on the overview there are by getting all the rows then subtracting all expected rows (i.e. mail and 3 other standard rows).
	 * @return A integer, the number of flights
	 */
	public static int numOfFlights()
	{
		return (hasMail() ?  (driver.findElements(By.xpath("/html/body/center[2]/center/table[2]/tbody/tr")).size() - OVERVIEW_TABLE_ROWS - OVERVIEW_MAIL_ROWS_IN_TABLE) :
			(driver.findElements(By.xpath("/html/body/center[2]/center/table[2]/tbody/tr")).size() - OVERVIEW_TABLE_ROWS));
	}
	
	/**
	 * Focuses on the given row on the overview. Note: Method does not reload the page.
	 * @param rowIndex An int representing the "/tr" element -- the row
	 * @return A flight
	 */
	public static Flight getFlight(int rowIndex)
	{
		//Ensure we are on the overview page; If it has to load the page the rowIndex might not be correct
		if (load(Page.OVERVIEW)) return null;
		Flight ret = new Flight();
		
		WebElement rowElement = driver.findElement(By.xpath("/html/body/center[2]/center/table[2]/tbody/tr["+ rowIndex +"]"));
		String fleetDefinition = rowElement.findElement(By.xpath("th[2]/span")).getText();
		
		try
		{
			//Get the countdown timer and add to the current date to get the arrival time
			ret.arrivalTime = DateUtils.addToCurrent(rowElement.findElement(By.xpath("th/font")).getText());
			
			//The last part of the definition is "Fleet Mission: <mission>"
			String[] semiColonSplit = fleetDefinition.split(":");
			ret.mission = FlightMission.getMission(semiColonSplit[semiColonSplit.length - 1]);
			
			//If the fleet isn't the user's then there will be an extra element before the coord text offset by one. 
			int aOffset = 0;
			//If there is a your anywhere in the definition it will be considered your fleet
			//CHECK
			if (fleetDefinition.contains("your")) {ret.type = FlightType.OWN;}
			else 
			{
				aOffset++;
				switch (rowElement.getCssValue("color"))
                {
                case "rgb(255, 0, 0)": {ret.type = FlightType.ENEMY; break;}
                default: {ret.type = FlightType.FRIENDLY; break;}
                }
			}
			
			String[] openBracketSplit = fleetDefinition.split("\\[");
			if(fleetDefinition.contains("RETURN"))
			{
				ret.isReturning = true;
				//First Coord location
				ret.targetLocation = new Location(rowElement.findElement(By.xpath("th[2]/span/a["+ (2 + aOffset) +"]")).getText(), LocationType.getTypeWithin(openBracketSplit[0]));
				//Second Coord location
				ret.departureLocation = new Location(rowElement.findElement(By.xpath("th[2]/span/a["+ (3 + aOffset) +"]")).getText(), LocationType.getTypeWithin(openBracketSplit[1]));
			}
			else
			{
				ret.isReturning = false;
				//Second Coord location
				ret.targetLocation = new Location(rowElement.findElement(By.xpath("th[2]/span/a["+ (3 + aOffset) +"]")).getText(), LocationType.getTypeWithin(openBracketSplit[1]));
				//First Coord location
				ret.departureLocation = new Location(rowElement.findElement(By.xpath("th[2]/span/a["+ (2 + aOffset) +"]")).getText(), LocationType.getTypeWithin(openBracketSplit[0]));
			}
			
			//Gets the pop down menu and then replaces all javascript junk and then splits on the semicolon
			String[] fleetDetails = rowElement.findElement(By.xpath("th[2]/span/a[1]")).getAttribute("onmouseover").replaceAll("<[^>]*>",  "").replace("return overlib('", "").replace("');", "").split(":");
			
			//Add two to continue to the next row
			for (int index = 0; index < fleetDetails.length; index += 2)
			{
				try 
				{
					//Name is the before the semi-colon and the number is after
					ret.ships.setQuantityOf(ShipType.getType(fleetDetails[index].replaceAll("[0-9]", "")), Integer.parseInt(fleetDetails[index + 1].replaceAll("[^0-9]", "")));
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					break;
				}
			}
			
			//Get resource pop down menu and then replaces all javascript junk and then splits on the semicolon
			//CHECK ARRAY INDEXES
			try
			{
			String[] resourceDetails = rowElement.findElement(By.xpath("th[2]/span/a["+ 4 + aOffset +"]")).getAttribute("onmouseover").replaceAll("<[^>]*>", "").replace("return overlib('", "").replace("');", "").split(":");
			ret.resources.metal = Long.parseLong(resourceDetails[1].replace(".", ""));
			ret.resources.crystal = Long.parseLong(resourceDetails[2].replace(".", ""));
			ret.resources.deuterium = Long.parseLong(resourceDetails[3].replace(".", ""));
			}
			catch (NoSuchElementException e){}//There was no information about resources on the overview page (i.e. no pop up)
		}
		catch (NoSuchElementException e)
		{
			LogManager.record("[Zorg] Could not get flight info! No such element exception.");
		}
		//Update the modified Time
		ret.modifiedTime = new Date();
		
		return ret;
	}
	
	public static final int OVERVIEW_FLEET_ROW_OFFSET = 2;
	
	/**
	 * Runs through all flights on the overview using {@link #getFlight(int)}.
	 * @return An arraylist populated with flights from the overview
	 */
	public static ArrayList<Flight> getAllFlights()
	{
		//Reload the overview page for most recent data
		assertLoad(Page.OVERVIEW);
		
		ArrayList<Flight> ret = new ArrayList<Flight>();
		
		int curRowIndex = OVERVIEW_FLEET_ROW_OFFSET;
		if (hasMail()) curRowIndex += OVERVIEW_MAIL_ROWS_IN_TABLE;
		
		int endingRowIndex = numOfFlights() + OVERVIEW_FLEET_ROW_OFFSET;
		
		while (curRowIndex <= endingRowIndex)
		{
			ret.add(getFlight(curRowIndex));
			curRowIndex++;
		}
		
		return ret;		
	}
	
	/**
	 * The amount of leading columns that are not the column containing planet information
	 */
	public static final int EMPIRE_PAGE_COLUMN_OFFSET = 1;
	/**
	 * The number of rows above the first row for buildings (i.e. metal mine)
	 */
	public static final int EMPIRE_PAGE_ROW_BUILDING_OFFSET = 12;
	/**
	 * The number of rows above the first row for ships (i.e. small cargo ship)
	 */
	public static final int EMPIRE_PAGE_ROW_FLEET_OFFSET = 31;
	/**
	 * The number of rows above the first row for defenses (i.e. rocket launcher)
	 */
	public static final int EMPIRE_PAGE_ROW_DEFENSE_OFFSET = 49;
	
	/**
	 * Focuses on the give column on the {@link Page#EMPIRE} and populates all data in a planet object. Note: Only passively loads the empire page if the drive isn't already on it.
	 * @param index The number of the planet in the empire view pane. I.e the third planet would be index 3.
	 * @return A planet
	 */
	public static Planet getPlanet(int index)
	{
		load(Page.EMPIRE);
		
		Planet ret = new Planet();		
		//Offsets by the column offset and then by one to match the indexing by xpath. Example, the first column would be index 0 but for xpath it's 1
		index += EMPIRE_PAGE_COLUMN_OFFSET + 1;
		
		WebElement table = driver.findElement(By.xpath("/html/body/center[2]/center/table/tbody"));
		
		//Get the link from the picture of the planet and replace everything to make it match the format in the drop down menu
		ret.listId = table.findElement(By.xpath("tr[2]/th["+ index +"]/a")).getAttribute("href").replace("http://www.zorgempire.org/overview.php", "").replace("&", "&mode=&");
		
		ret.name = table.findElement(By.xpath("tr[3]/th["+ index +"]")).getText();
		
		//If the picture is of a moon then make the location type a moon else a planet since debris fields don't show up in the empire screen
		if (table.findElement(By.xpath("tr[2]/th["+ index +"]/a/img")).getAttribute("src").contains("mond.jpg")) ret.location.type = LocationType.MOON;
		else ret.location.type = LocationType.PLANET;
		
		ret.location.setCoordinates(table.findElement(By.xpath("tr[4]/th["+ index +"]")).getText());
		
		String[] fieldBackslashSplit = table.findElement(By.xpath("tr[5]/th["+ index +"]")).getText().split("/");
		ret.buildings.usedFields = Integer.parseInt(fieldBackslashSplit[0]);
		ret.buildings.totalFields = Integer.parseInt(fieldBackslashSplit[1]);
		
		ret.resources.metal = Long.parseLong(table.findElement(By.xpath("tr[7]/th["+ index +"]/a")).getText().replace(".", ""));
		ret.resources.crystal = Long.parseLong(table.findElement(By.xpath("tr[8]/th["+ index +"]/a")).getText().replace(".", ""));
		ret.resources.deuterium = Long.parseLong(table.findElement(By.xpath("tr[9]/th["+ index +"]/a")).getText().replace(".", ""));
		
		String[] energyBackslashSplit = table.findElement(By.xpath("tr[10]/th["+ index +"]")).getText().replace(".", "").split("/");
		ret.resources.energyProduced = Integer.parseInt(energyBackslashSplit[0].trim());
		ret.resources.energyTotal = Integer.parseInt(energyBackslashSplit[1].trim());
		
		for (int i = EMPIRE_PAGE_ROW_BUILDING_OFFSET; i < BuildingType.values().length + EMPIRE_PAGE_ROW_BUILDING_OFFSET; i++)
		{
			//Gets the name of the row (the building name) and then set it to the value in the index column
			ret.buildings.setQuantityOf(BuildingType.getType(table.findElement(By.xpath("tr["+ i +"]/th[1]")).getText()), Integer.parseInt(table.findElement(By.xpath("tr["+ i +"]/th["+ index +"]")).getText().replace("-", "0")));
		}
		
		for (int i = EMPIRE_PAGE_ROW_FLEET_OFFSET; i < ShipType.values().length + EMPIRE_PAGE_ROW_FLEET_OFFSET; i++)
		{
			//Gets the name of the row (the ship name) and then set it to the value in the index column
			ret.ships.setQuantityOf(ShipType.getType(table.findElement(By.xpath("tr["+ i +"]/th[1]")).getText()), Integer.parseInt(table.findElement(By.xpath("tr["+ i +"]/th["+ index +"]")).getText().replace("-", "0")));
		}
		
		for (int i = EMPIRE_PAGE_ROW_DEFENSE_OFFSET; i < DefenseType.values().length + EMPIRE_PAGE_ROW_DEFENSE_OFFSET; i++)
		{
			ret.defenses.setQuantityOf(DefenseType.getType(table.findElement(By.xpath("tr["+ i +"]/th[1]")).getText()) , Integer.parseInt(table.findElement(By.xpath("tr["+ i +"]/th["+ index +"]")).getText().replace("-", "0")));
		}
		
		//Update last modified time
		ret.modifiedTime = new Date();
		
		return ret;
	}
	
	/**
	 * Runs through all planets and populates an arraylist setting the planet to the index it was retrieve from on the {@link Page#EMPIRE}
	 * @return An arraylist of planets
	 */
	public static ArrayList<Planet> getAllPlanets()
	{
		assertLoad(Page.EMPIRE);
		//Counts the number of planets displayed on the empire page minus the offset
		int numberOfPlanets = driver.findElements(By.xpath("/html/body/center[2]/center/table/tbody/tr[2]/th")).size() - EMPIRE_PAGE_COLUMN_OFFSET;
		ArrayList<Planet> ret = new ArrayList<Planet>();
		for (int i = 0; i < numberOfPlanets; i++)
		{
			ret.add(getPlanet(i));
		}
		return ret;
	}
	
	/**
	 * Sets focus, the active, planet to the given planet
	 * @param planet A planet with name and location values non-null
	 */
	public static void setFocusOn(Planet planet)
	{
		assertLoad(Page.OVERVIEW);
		
		List<WebElement> dropDownPlanetMenu = driver.findElements(By.xpath("/html/body/center[1]/table/tbody/tr/td[1]/center/table/tbody/tr/td[2]/table/tbody/tr[2]/td/select/option"));
		
		for (WebElement element : dropDownPlanetMenu)
		{
			//Make sure the element has all values (listId, name, and coords) of the desired planet
			if (element.getAttribute("value").contains(planet.listId)
					&& element.getText().contains(planet.name)
					&& element.getText().contains(planet.location.coordsToString()))
			{
				element.click();
				break;
			}
		}
	}
	
	/**
	 * Gets all technology levels from the given planet
	 * @param planet A planet
	 * @return	The technologies
	 */
	public static Technologies getTechnologies(Planet planet)
	{	
		setFocusOn(planet);
		assertLoad(Page.RESEARCH);
		
		Technologies ret = new Technologies();
		
		//Get the center table on the research page
		List<WebElement> technologyTable = driver.findElements(By.xpath("/html/body/center[2]/center/table/tbody/tr/td/table/tbody/tr"));
		//Remove the first index of the table as it's a head on the web page
		technologyTable.remove(0);
		
		for (WebElement element : technologyTable)
		{
			//Set each tech to it's level
			ret.setQuantityOf(TechnologyType.getType(element.findElement(By.xpath("td/a")).getText()), Integer.parseInt(element.findElement(By.xpath("td")).getText().split("\\)")[0].replaceAll("[^0-9]", "").trim()));
		}
		
		return ret;
	}
}

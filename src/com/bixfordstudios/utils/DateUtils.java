package com.bixfordstudios.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Helper methods to better interface with Zorg's formating
 * @author DarkEther
 *
 */
public class DateUtils {
	
	/**
	 * Adds the give string to the current date and time
	 * @param hourMinSec A string in format hh:mm:ss
	 */
	public static Date addToCurrent(String hourMinSec)
	{
		String[] semiColonSplit = hourMinSec.split(":");
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.add(Calendar.HOUR, Integer.parseInt(semiColonSplit[0].trim()));
		cal.add(Calendar.SECOND, Integer.parseInt(semiColonSplit[1].trim()));
		cal.add(Calendar.MINUTE, Integer.parseInt(semiColonSplit[2].trim()));
		return cal.getTime();
	}
}

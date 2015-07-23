package com.bixfordstudios.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Simple Logger the writes to a file based on the start-time of the program.
 * @author DarkEther
 *
 */
public class LogManager {
	
	public static final String DEFAULT_FILE_SAVE = "./Logs";
	
	private static String RUNTIME_FILE_SAVE;
	private static PrintWriter PRINT;
	
	public void test()
	{
		
	}
	
	
	static
	{
		File file = new File(DEFAULT_FILE_SAVE + getFileName());
		if (!file.exists())
		{
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//For use at a later time (since the name would change if using getFileName()
		RUNTIME_FILE_SAVE = file.getAbsolutePath();		
		
		try {
			PRINT = new PrintWriter(RUNTIME_FILE_SAVE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private LogManager()
	{
		throw new AssertionError();
	}
	
	/**
	 * Prints message to file
	 * @param message A string
	 */
	public static void record(String message)
	{
		PRINT.println(message);
		
		//Flush to ready the stream for another input
		PRINT.flush();
	}
	
	/**
	 * Gets the file's specific name based on time of call
	 * @return
	 */
	private static String getFileName()
	{
		return "/" + new Date().toString().replaceAll("[ :]", "-") +".txt";
	}
	
	public static void shutdown()
	{
		PRINT.close();
	}
}
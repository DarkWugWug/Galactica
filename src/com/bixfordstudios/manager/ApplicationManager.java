package com.bixfordstudios.manager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 * Ensures the application can function.
 * @author DarkEther
 *
 */
public class ApplicationManager {
	
	public static final String FILE_LOCK_LOCATION = ".fileLock";
	
	public static File FILE;
	public static RandomAccessFile RANDOMACCESSFILE;
	public static FileLock FILELOCK;
	
	/**
	 * Determines if there is another instance of this program running.
	 * @return A boolean. True, if is the first and only instance using this file lock; false, if there is another instance using this file lock.
	 */
	public static boolean isOnlyInstance()
	{
		try
		{
			FILE = new File(FILE_LOCK_LOCATION);
			RANDOMACCESSFILE = new RandomAccessFile(FILE, "rw");
			FILELOCK = RANDOMACCESSFILE.getChannel().tryLock();
			
			if (FILELOCK == null)
			{
				//File cannot be locked; Application is already running
				LogManager.record("[AppMngr] Lock file is already locked; Application already running!");
				return false;
			}
			else
			{
				//File is locked; Application is the first and only instance running
				return true;
			}
		}
		catch (Exception e)
		{
			LogManager.record("[AppMngr] Unable to set file lock!");
			return false;
		}
	}
	
	/**
	 * Unlocks file lock, deletes file, and closes random access file.
	 */
	public static void shutdown()
	{
        try 
        {
        	FILELOCK.release();
			RANDOMACCESSFILE.close();
		} catch (IOException e) 
        {
			LogManager.record("[AppMngr] Could not close log files!");
		}
        FILE.delete();
	}
}

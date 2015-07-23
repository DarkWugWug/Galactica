package com.bixfordstudios.main;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bixfordstudios.manager.ApplicationManager;
import com.bixfordstudios.manager.LogManager;
import com.bixfordstudios.manager.ZorgManager;
import com.bixfordstudios.zorg.Server;



public class Main {
	
	public static void main(String args[]) throws ParseException
	{
		init();
		
		if (ApplicationManager.isOnlyInstance())
		{
			//RUNTIME ACTIONS
			if (ZorgManager.login(Server.SPEED, "ThatDude", "ThatDude".toCharArray())) ZorgManager.updateEmpire();
		}
	}
	
	private static void init()
	{
		//Silence the gargoylsoftware logger for css and xpath parsing due to it being very verbose
		Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		Logger.getLogger("com.gargoylesoftware.htmlunit.DefaultCssErrorHandler").setLevel(null);;
		
		//Allows for managers to properly end their task
		Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                shutdown();
            }
        });
	}
	
	private static void shutdown()
	{
		ApplicationManager.shutdown();
		LogManager.shutdown();
	}
	
}

package net.nettape.server.service;
/**
 * 
 */

/**
 * @author valentijn
 *
 */
import java.net.*;
//import org.apache.derby.*;
//import org.apache.derby.drda.NetworkServerControl;
import java.security.Security;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import net.nettape.client.ShadowHandler;
import net.nettape.client.command.SendCDPItemCommand;
import net.nettape.connection.*;
import net.nettape.object.CDP;
import net.nettape.object.ModifiedFile;
import net.nettape.object.ThreadList;
import net.nettape.object.Constants.*;

public class EyeOfGod {

	private static int port = 1234;
	private static int portHttp = 8000;
	private static LinkedList<HandOfGod> threadList;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
		
			
			System.out.println("NetTape Backup Server starting...");
			
			EyeForSockets eyeForSockets = new EyeForSockets();
			Thread tSockets = new Thread(eyeForSockets);
			tSockets.start();
			
			EyeForHttp eyeForHttp = new EyeForHttp();
			Thread tHttp = new Thread(eyeForHttp);
			tHttp.start();
			
			//let's start a timer to do some maintenance
			/*
			int delay = 500;   
			int period = 300000;
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
			        public void run() {
			            try
				        {
			            	//TODO:check if backups are running to long
			            	//TODO:check if restores are running to long
			            	//TODO:check if backups that database says are running are indeed still running
			            	// get the backups in database that are running
			            	// now check for each backup if it is still running
			            	for(Thread thread: ThreadList.getInstance().List)
			            	{
			            		HandOfGod hog =((HandOfGod)thread);
			            		if(hog.command != null)
			            		{
			            			if(hog.command == net.nettape.connection.Command.SENDBACKUP)
			            			{
			            				
			            			}
			            		}
			            	}
			            	//TODO:check if restores that database says are running are indeed still running
			            }
			            catch(Exception ex)
			            {
			            	
			            }
			        }
			    }, delay, period);

			*/
			
	
			


		}
		catch (Exception e)
		{
			System.out.println("Unable to listen on port " + port);
		}

	}

}

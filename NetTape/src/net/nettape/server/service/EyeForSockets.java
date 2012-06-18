package net.nettape.server.service;
import java.net.*;
import java.security.Security;
import java.util.*;

import net.nettape.connection.inFileDeltaProvider;
import net.nettape.object.ThreadList;
import net.nettape.object.Constants.ConnectionType;



public class EyeForSockets extends Thread
{

	private int port = 1234;

	
	public EyeForSockets()
	{
	}

	public void run()
	{
		ServerSocket ss;
		try {
			ss = new ServerSocket(port);
		
			System.out.println("NetTape Backup Server started. Listening on port " + port + " for socket connections.");
			Security.addProvider(new inFileDeltaProvider());

			
			while(true)
			{
	
				Socket s = ss.accept();
				//start a thread for this socket now
				HandOfGod handOfGod = new HandOfGod(ConnectionType.SOCKETS,s,ThreadList.getInstance().List,null);
				Thread t = new Thread(handOfGod);
				t.start();
				
	        	synchronized(ThreadList.getInstance().List)
	        	{
	        		ThreadList.getInstance().List.add(handOfGod);
	        	}
			}
		}
		catch(Exception ex)
		{
			System.out.println("Unable to listen on port " + port);
		}
	}
}

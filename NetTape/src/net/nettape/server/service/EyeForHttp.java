package net.nettape.server.service;
import java.net.*;
import java.security.Security;
import java.util.*;

import net.nettape.connection.inFileDeltaProvider;
import net.nettape.object.Constants.ConnectionType;
import net.nettape.object.ThreadList;



public class EyeForHttp extends Thread
{

	private int port = 8000;

	
	public EyeForHttp()
	{
	}

	public void run()
	{
		ServerSocket ssHttp;
		try {
			ssHttp = new ServerSocket();
			//ssHttp.setReuseAddress(true);
			ssHttp.bind(new java.net.InetSocketAddress(port));
			System.out.println("NetTape Backup Server started. Listening on port " + port + " for http connections.");
			Security.addProvider(new inFileDeltaProvider());
			
			while(true)
			{
	
				Socket s = ssHttp.accept();
				//start a thread for this socket now
				HandOfGod handOfGod = new HandOfGod(ConnectionType.HTTPCHUNKED,s,ThreadList.getInstance().List,null);
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

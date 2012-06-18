package net.nettape.client.command;



import java.net.*;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import net.nettape.client.MessageHandler;
import net.nettape.client.Global;
import net.nettape.client.gui.AppConstants;
import net.nettape.client.gui.admin.INIFile;
import net.nettape.connection.*;
import net.nettape.dal.object.*;
import net.nettape.object.*;
import net.nettape.object.Constants.ConnectionType;


public class LoginCommand 
{
	private Connection connection;
	private User user;
	private String clientGUID;
	private String serverName;
	private String port;
	private ConnectionType connectionType;
	public LoginCommand(String serverName, ConnectionType connectionType, String username, String password)
	{
		try
		{
			user = new User();
			user.setUsername(username);
			user.setPassword(password);
			this.serverName = serverName;
			this.port = port;
			this.connectionType = connectionType;
		}
		catch(Exception ex)
		{

		}


	}
	
	public Connection GetConnection()
	{
		return this.connection;
	}
	
    private void writeInt(int i, OutputStream out) throws IOException {
        System.out.print("write1");
        out.write((byte) ((i >>> 24) & 0xff));
        System.out.print("write2");
        out.write((byte) ((i >>> 16) & 0xff));
        System.out.print("write3");
        out.write((byte) ((i >>>  8) & 0xff));
        System.out.print("write4");
        out.write((byte) ( i & 0xff));
     }
	public boolean Execute() throws Exception
	{
		
		try
		{
			try
			{
				Socket socket= null;
				URI uri = new URI(this.serverName);
				socket = new Socket(uri.getHost(),uri.getPort());
				connection = (new ConnectionFactory()).MakeConnection(this.connectionType, socket, uri, true, false, null);
				connection.ServerName = this.serverName;
				connection.Port = this.port;
				connection.dalUser = new User();
				connection.dalUser.setUsername(user.getUsername());
				connection.dalUser.setPassword(user.getPassword());
				connection.SendCommand(Command.LOGIN);
				connection.sendObject(connection.dalUser);
				connection.dalUser = (User)connection.receiveObject();
				if(connection.dalUser.getUserid() != 0)
				{
					Client aClient = new Client();
					AppConstants.getInstance();
					AppConstants.setSettings("settings.ini");
					TreeMap<String, String> p = AppConstants.getSettings().get("LOGIN");
					clientGUID = Global.ClientGUID;
					if(clientGUID == null)
					{
						// make a new GUID and send to the server, if clientid returned is 0 then too many clients for license
						java.util.UUID uuid = java.util.UUID.randomUUID(); 
						clientGUID = uuid.toString();
						if(p != null)
						{
							p.put("ClientGUID", clientGUID);
							AppConstants.getSettings().put("LOGIN", p);
							INIFile ini = new INIFile(AppConstants.SETTINGS_DIR
									+ "settings.ini");
							ini.setSections(AppConstants.getSettings());
							ini.saveFile();
						}
					}
					aClient.setGuid(clientGUID);
					aClient.setType(Global.ClientType);
					connection.sendObject(aClient);
					connection.dalClient = (Client)connection.receiveObject();
					if(connection.dalClient.getClientid() != 0)
					{
						Global.ClientId = connection.dalClient.getClientid();
						
						if(connection.dalUser.getUserid() != 0)
						{
							Global.UserId = connection.dalUser.getUserid();
							
							connection.Login();
							return true;
						}
						else
						{
							connection.Logout();
							connection = null;
							MessageHandler.HandleMessage(null,true,"Username or password is incorrect.",false,null, null);
						}
					}
					else
					{
						connection.Logout();
						connection = null;
						MessageHandler.HandleMessage(null,true,"Check the license on the server. Too many clients.",false,null, null);
					}
				}
			}
			catch(Exception ex)
			{
				try
				{
					connection.Logout();
					connection = null;
				}
				catch(Exception ex2)
				{}
				MessageHandler.HandleMessage(null,true,"Unable to connect to server. Please check your internet connection.",false,null, null);
				return false;
			}
			return false;


		}
		catch(Exception ex)
		{
			return false;
		}
		
	}
}

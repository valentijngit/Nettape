package net.nettape.client;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.TreeMap;

import net.nettape.client.gui.AppConstants;
import net.nettape.connection.Command;
import net.nettape.connection.Connection;
import net.nettape.connection.ConnectionFactory;
import net.nettape.dal.object.User;
import net.nettape.client.command.SendMessageCommand; 
import net.nettape.object.Message;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import net.nettape.object.*;
import net.nettape.object.Constants.ConnectionType;
import java.net.URI;

public class MessageHandler {
	

	public static void HandleMessage(Exception ex, boolean showToUser, String userMessage, boolean sendToServer, Message message, Shell shell)
	{
		if(showToUser)
		{
			if(shell != null) showMessageToUser(userMessage, shell);
		}
		if(sendToServer)
		{
			// make a new connection!
			try 
			{
				TreeMap<String, String> p = AppConstants.getSettings().get("LOGIN");
				if(p != null)
				{
					String port = p.get("Port");
					URI uri = new URI(p.get("ServerName") + ":" + port);
					String userName = p.get("UserName");
					String password = p.get("Password");
					Integer connectionType = Integer.parseInt(p.get("ConnectionType"));
					
					Socket s = new Socket(uri.getHost(),uri.getPort());
					Connection connection = (new ConnectionFactory()).MakeConnection(ConnectionType.class.getEnumConstants()[connectionType], s, uri, true, false, null);	
					connection.dalUser = new User();
					connection.dalUser.setUsername(userName);
					connection.dalUser.setPassword(password);
					connection.SendCommand(Command.LOGIN);
					connection.sendObject(connection.dalUser);
					connection.dalUser = (User)connection.receiveObject();
					if(connection.dalUser.getUserid() != 0)
					{
						connection.Login();
						message.ClientId = Global.ClientId;
						message.Date_ = Calendar.getInstance().getTime();
						message.UserId = Global.UserId;
				
						SendMessageCommand sendMessageCommand = new SendMessageCommand(connection,message);
						if(! sendMessageCommand.Execute())
						{
							if(showToUser)
							{
								if(shell != null) showMessageToUser("Backup client was unable to log an error to the server.", shell);
							}
							
						}
						
						connection.Logout();
					}
				}
			}
			catch (Exception e)
			{
				if(showToUser)
				{
					if(shell != null) showMessageToUser("Backup client was unable to log an error to the server.", shell);
				}
				
			}
			
		}
	}
	private static void showMessageToUser(String message, Shell shell)
	{
		if(shell == null)
		{
			try
			{
				shell = Display.getCurrent().getActiveShell();
			}
			catch(Exception ex)
			{}
		}
		
		try
		{
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR 
		            | SWT.OK);
		        messageBox.setMessage(message);
		        messageBox.setText("Error");
		        messageBox.open();
		}
		catch(Exception ex)
		{
			int test = 0; 
		}
	}
}

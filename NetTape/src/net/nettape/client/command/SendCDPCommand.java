package net.nettape.client.command;

import java.io.*;
import java.net.SocketException;

import net.nettape.connection.*;
import net.nettape.object.BackupSet;


public class SendCDPCommand extends ClientCommand
{

	private BackupSet backupSet;
	public boolean cancel = false;
	public int backupId = 0;
	public SendCDPCommand(Connection connection, BackupSet backupSet)
	{
		super(connection);
		
		this.backupSet = backupSet;
	}

	public void Execute(boolean reConnect) throws ConnectionException
	{
		if(reConnect) 
		{
			// try to login again in a loop (with cancel function)
			while(!this.cancel)
			{
				try{
					LoginCommand loginCommand = new LoginCommand(this.connection.ServerName, this.connection.connectionType , this.connection.dalUser.getUsername(),this.connection.dalUser.getPassword());
					if(loginCommand.Execute())
					{
						connection = loginCommand.GetConnection();
	 					break;
					}
				}
				catch(Exception ex)
				{
					
				}
			}

			
		}
		if(connection.IsLoggedIn() && !this.cancel)
		{
			try
			{
				connection.SendCommand(Command.SENDCDP);
				connection.sendInt(this.backupSet.backupset.getBackupsetid());
				this.backupId = connection.receiveInt();
			}
			catch(SocketException ex)
			{
			}
			catch(IOException ex)
			{
			}
			catch(Exception ex)
			{
			}
		
		}
	}
	
}


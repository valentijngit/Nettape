package net.nettape.client.command;

import net.nettape.client.Global;
import net.nettape.connection.*;


public abstract class ClientCommand
{
	protected Connection connection;
	public ClientCommand(Connection connection)
	{
		this(connection,true);
	}
	public ClientCommand(Connection connection, boolean checkConnected)
	{
		this.connection = connection;
		if(!this.connection.IsLoggedIn())
		{
			try
			{
				connection.Logout();
				LoginCommand loginCommand = new LoginCommand(this.connection.ServerName, this.connection.connectionType , this.connection.dalUser.getUsername(),this.connection.dalUser.getPassword());
				if(loginCommand.Execute())
				{
					this.connection = loginCommand.GetConnection();
					Global.connection = this.connection;
				}
			}
			catch(Exception ex)
			{
				
			}

		}
		if(checkConnected)
		{
			if(!this.connection.isConnected())
			{
				try
				{
					connection.Logout();
					LoginCommand loginCommand = new LoginCommand(this.connection.ServerName, this.connection.connectionType , this.connection.dalUser.getUsername(),this.connection.dalUser.getPassword());
					if(loginCommand.Execute())
					{
						this.connection = loginCommand.GetConnection();
					}
				}
				catch(Exception ex)
				{
					
				}
	
			}
		}
	}
	public Connection GetConnection()
	{
		return this.connection;
	}

}
 
package net.nettape.server.command;
import net.nettape.connection.Connection;






public abstract class ServerCommand 
{
	protected Connection connection;
	public ServerCommand(Connection connection)
	{
		this.connection = connection;
	}
	public abstract void Execute() throws Exception;
}

package net.nettape.connection;
import java.net.*;
import net.nettape.object.Constants.ConnectionType;

public class ConnectionFactory {
	public ConnectionFactory()
	{
		
	}
	public Connection MakeConnection(ConnectionType connectionType, Socket s, URI uri, boolean secure, boolean serverConnection, String password) throws Exception
	{
		switch(connectionType)
		{
		case SOCKETS:
			if(secure)
			{
				return new SecureDHConnection(s, serverConnection, connectionType);
			}
			
		case HTTPCHUNKED:
			if(secure)
			{
				return new SecureDHConnectionHttp(s, uri, serverConnection, connectionType);
			}
			
		}
		return null;

	}

}

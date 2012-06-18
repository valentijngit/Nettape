package net.nettape.connection;

import java.net.*;
import javax.crypto.*;
import java.io.*;


import net.nettape.client.SmartFile;
import net.nettape.dal.object.*;
import net.nettape.object.Constants.ConnectionType;

public abstract class Connection {
	protected Socket s;
	protected URI uri;
	protected boolean LoggedIn = false;
	public SecretKey key;
	public InputStream inputStream = null;
	public OutputStream outputStream = null;
	public ObjectInputStream objectInputStream = null;
	public ObjectOutputStream objectOutputStream = null;
	public User dalUser = null;
	public Client dalClient = null;
 	public String ServerName = null;
 	public String Port = null;
 	public ConnectionType connectionType;
 	public String SocketNumber = null;
 	
	public Socket GetSocket()
	{
		return this.s;
	}
	public boolean IsLoggedIn()
	{
		return this.LoggedIn;
	}
	public void Login()
	{

		this.LoggedIn = true;
	}
	public void Logout()
	{ 
		try 
		{
			if(! s.isClosed()) s.close();
		}
		catch(Exception ex)
		{
			
		}
		this.LoggedIn = false;
	}
	
	public void SendCommand(Command command) throws IOException
	{
		ObjectOutputStream out = getObjectOutputStream();
		out.writeObject(command);
		out.flush();
	}
	public Command ReceiveCommand() throws Exception
	{
		ObjectInputStream in = getObjectInputStream();
		Command command = (Command) in.readObject();
		return command;
	}	
	
	public abstract void sendObject(Object object) throws Exception;
	public abstract Object receiveObject() throws Exception;
	public abstract void sendInt(int i) throws Exception;
	public abstract int receiveInt() throws Exception;
	public abstract void sendLong(long l) throws Exception;
	public abstract long receiveLong() throws Exception;
	public abstract void sendFileOnServer(RandomAccessFile ras, long length) throws Exception, IOException;
	public abstract void sendFileOnClient(SmartFile smartFile) throws Exception, IOException;
	public abstract long receiveFileOnClient(SmartFile smartFile) throws Exception;
	public abstract void receiveFileOnServer(FileOutputStream fos) throws Exception, IOException;
	public abstract byte[] receiveData(InputStream ins, int an_int) throws Exception;
	public abstract void sendString(String s) throws Exception;
	public abstract String receiveString() throws Exception;
	
	public abstract OutputStream getOutputStream() throws IOException;
	public abstract InputStream getInputStream() throws IOException;
	public abstract ObjectOutputStream getObjectOutputStream() throws IOException;
	public abstract ObjectInputStream getObjectInputStream() throws IOException;
	public abstract boolean isConnected();
    public abstract InputStream getOutInputStream() throws IOException;
    public abstract OutputStream getInOutputStream() throws IOException;
}



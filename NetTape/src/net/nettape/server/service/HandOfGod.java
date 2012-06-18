package net.nettape.server.service;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

import net.nettape.connection.Command;
import net.nettape.connection.Connection;
import net.nettape.connection.ConnectionFactory;
import net.nettape.server.command.*;
import net.nettape.object.Constants.ConnectionType;



public class HandOfGod extends Thread
{
	public Connection connection;
	public Command command = null;
	public boolean noop = false;
	public String username ="";
	public String password ="";
	private LinkedList<HandOfGod> threadList;
	private String socketNumber;
	public Integer receiveLength = 0;
	public Integer sendLength = 0;
	public byte[] sendBuffer = null;
	public byte[] receiveBuffer = null;
	public Integer sendBufferLength = 0;
	public Integer receiveBufferLength = 0;
	
	public HandOfGod(ConnectionType connectionType, Socket s, LinkedList<HandOfGod> threadList, String socketNumber)
	{
		try
		{
			this.threadList = threadList;
			this.socketNumber = socketNumber;
			this.connection = (new ConnectionFactory()).MakeConnection(connectionType, s, null, true, true, null);
		}
		catch (Exception ex)
		{
			
		}
	
	}

	public void run()
	{
		ServerCommand serverCommand;
		try
		{
			while(true)
			{
				noop = false;
				serverCommand = null;
				
				command = connection.ReceiveCommand();
				if(connection.IsLoggedIn())
				{
					switch(command)
					{
					case NOOP:
						noop = true;
						break;
					case LOGIN:
						serverCommand = new LoginCommand(connection);
						break;
					case SENDFILE:
						serverCommand = new SendFileCommand(connection);
						break;
					case SENDBACKUPSET:
						serverCommand = new SendBackupSetCommand(connection);
						break;
					case SENDBACKUP:
						serverCommand = new SendBackupCommand(connection);
						break;
					case SENDBACKUPITEM:
						serverCommand = new SendBackupItemCommand(connection);
						break;
					case RECEIVERESTORE:
						serverCommand = new ReceiveRestoreCommand(connection);
						break;
					case RECEIVEBACKUPSETS:
						serverCommand = new ReceiveBackupSetsCommand(connection);
						break;
					case SENDCDP:
						serverCommand = new SendCDPCommand(connection);
						break;
					case SENDCDPITEM:
						serverCommand = new SendCDPItemCommand(connection);
						break;
					case RECEIVESCHEDULES:
						serverCommand = new ReceiveSchedulesCommand(connection);
						break;
					case SENDMESSAGE:
						serverCommand = new SendMessageCommand(connection);
						break;
					case RECEIVEBACKUPS:
						serverCommand = new ReceiveBackupsCommand(connection);
						break;
					case RECEIVEBACKUP:
						serverCommand = new ReceiveBackupCommand(connection);
						break;
					case LOGOUT:
						connection.sendInt(0);
						//set noop to true, because server should never end connection out of own initiative, the client has to close the socket, the server detects that
						noop = true;
						break;
					
					default:
						break;
					}
				}
				else
				{
					switch(command)
					{
					case LOGIN:
						serverCommand = new LoginCommand(connection);
						break;
					default:
						connection.Logout();
						break;
					}
				}
				if(serverCommand != null)
				{
					serverCommand.Execute();
				}
				else
				{
					if (!noop)
					{
						break;
					}
				}
			}
			System.out.println("Dropping connection to " + connection.GetSocket().getInetAddress().getHostAddress() + ".");
			connection.GetSocket().close();

		}
		catch (EOFException ex)
		{
			// client closed the connection, EOFException is when trying to read a command through the compressed stream
		}
		catch (Exception ex)
		{
			System.out.println("An error has occured while reading command from a client.");
		}
		finally
		{
			synchronized(threadList)
			{
				threadList.remove(this);
			}
		}
	}
}

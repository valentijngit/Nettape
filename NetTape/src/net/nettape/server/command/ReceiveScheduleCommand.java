package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.FileInfo;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class ReceiveScheduleCommand extends ServerCommand
{

	private Schedule schedule;
	
	public ReceiveScheduleCommand(Connection connection, Schedule schedule)
	{
		super(connection);
		this.schedule = schedule;
	}
	public ReceiveScheduleCommand(Connection connection)
	{
		super(connection);
	
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        session.beginTransaction();
				if(schedule == null)
				{
					int scheduleId = connection.receiveInt(); 
					this.schedule = (Schedule)session.get(Schedule.class, scheduleId);
				}

		        session.getTransaction().commit();

		        connection.sendObject(this.schedule);

				
		    }
			catch(Exception ex)
			{
				System.out.println("Error receiving a file.");
			}
		}
	}
}

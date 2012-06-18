package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.*;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class ReceiveSchedulesCommand extends ServerCommand
{

	public ReceiveSchedulesCommand(Connection connection)
	{
		super(connection);
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				int backupsetId = connection.receiveInt();
				
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        session.beginTransaction();

		        ArrayList<Schedule> scheduleList = (ArrayList<Schedule>) session
			        .createQuery("select s from Schedule s where s.backupset.backupsetid = :bsid")
			        .setParameter("bsid", backupsetId)
			        .list(); 
		        session.getTransaction().commit();

		        connection.sendInt(scheduleList.size());
		        for(Schedule item : scheduleList)
		        {
		        	ReceiveScheduleCommand receiveScheduleCommand = new ReceiveScheduleCommand(connection,item);
		        	receiveScheduleCommand.Execute();

		        }


				
		    }
			catch(Exception ex)
			{
				System.out.println("Error receiving a file.");
			}
		}
	}
}

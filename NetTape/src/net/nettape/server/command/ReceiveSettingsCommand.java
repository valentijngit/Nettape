package net.nettape.server.command;




import net.nettape.connection.Connection;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class ReceiveSettingsCommand extends ServerCommand
{

	public ReceiveSettingsCommand(Connection connection)
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

		        Settings settings = (Settings) session
			        .createQuery("select s from Settings s")
			        .uniqueResult();
		        session.getTransaction().commit();

		        connection.sendObject(settings);
				
		    }
			catch(Exception ex)
			{
				//TODO log this exception
			}
		}
	}
}

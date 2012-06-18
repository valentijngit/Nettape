package net.nettape.server.command;



import java.net.*;
import java.io.*;
import java.util.*;

import org.hibernate.Session;

import net.nettape.connection.*;
import net.nettape.dal.HibernateUtil;
import net.nettape.dal.object.*;

public class LoginCommand extends ServerCommand
{

	
	public LoginCommand(Connection connection)
	{
		super(connection);
	}
    private int readInt(InputStream in) throws IOException {
        int i = 0;
        for (int j = 3; j >= 0; j--) {
        	System.out.print("now:" + j);
           int k = in.read();
           if (k == -1) throw new EOFException();
           i |= (k&0xff) << 8*j;
        }
        return i;
    }

	public void Execute() 
	{
		try
		{

			User aUser = (User)connection.receiveObject();

			
	        try{
	        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        session.beginTransaction();
	        	aUser = (User) session
		        .createQuery("select u from User u where u.username = :username and u.password = :password")
		        .setParameter("username", aUser.getUsername())
		        .setParameter("password", aUser.getPassword())
		        .uniqueResult(); 

	        	session.getTransaction().commit();

	        	
	        }
	        catch(Exception ex)
	        {
	        	aUser.setUserid(0);
	        }
	        
	        
	        connection.sendObject(aUser);
	        
	        if(aUser.getUserid() != 0)
	        {
	        	connection.dalUser = aUser;
				Client aClient = (Client)(connection.receiveObject());
				Client newClient = null;
	        	try
	        	{
		        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			        session.beginTransaction();
		        	newClient = (Client) session
			        .createQuery("select c from Client c where c.guid = :guid")
			        .setParameter("guid", aClient.getGuid())
			        .uniqueResult(); 
	
		        	session.getTransaction().commit();
	
	        	}
		        catch(Exception ex)
		        {
		        }
		        if(newClient == null)
		        {
		        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			        session.beginTransaction();
		        	License license = (License) session
			        .createQuery("select l from License l")
			        .uniqueResult(); 
		        	Long current = (Long) session
			        .createQuery("select count(c.clientid) from Client c where c.user.active = true and c.type = :type")
			        .setParameter("type", aClient.getType())
			        .uniqueResult();
		        	session.getTransaction().commit();
		        	boolean ok = false;
		        	if(aClient.getType() == 1)
		        	{
		        		if(current < license.getEbcclientquota())
		        			ok = true;
		        	}
		        	if(aClient.getType() == 2)
		        	{
		        		if(current < license.getBbcclientquota())
		        			ok = true;
		        	}
		        	
			        newClient = new Client();
					if(ok)
		        	{
			        	try
			        	{
				        	session = HibernateUtil.getSessionFactory().getCurrentSession();
					        session.beginTransaction();
					        newClient.setGuid(aClient.getGuid());
					        newClient.setUser(aUser);
					        newClient.setType(aClient.getType());
					        session.save(newClient);
				        	session.getTransaction().commit();
			        		
			        	}
			        	catch(Exception e)
			        	{
				        	newClient.setClientid(0);
			        	}
		        	}
		        	else
		        		newClient.setClientid(0);
		        }
		        connection.sendObject(newClient);
		        if (newClient.getClientid() != 0)
		        {
		        	connection.Login();
		        	connection.dalClient = newClient;
		        }
	        }
		}
		catch(Exception ex)
		{
			String test ="";
			
		}

		
	}
}

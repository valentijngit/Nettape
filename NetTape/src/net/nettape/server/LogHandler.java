package net.nettape.server;

import java.io.*;

import org.hibernate.Session;

import net.nettape.dal.HibernateUtil;
import net.nettape.dal.object.*;
import net.nettape.object.Constants;
import net.nettape.object.IOHelper;
import net.nettape.dal.*;
import java.util.Calendar;
import net.nettape.object.Message; 

public class LogHandler {
	public void handle(Message message) {
		Log log = new Log();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Client client =(Client) session.get(Client.class , message.ClientId );
		log.setBackupitemid(message.BackupItemId);
		log.setClient(client);
		if(message.Date_ != null)
		{
			log.setDatetime(message.Date_);
		}
		else
		{
			log.setDatetime(Calendar.getInstance().getTime());
		}
		log.setMessage(message.Message);
		log.setRestoreitemid(message.RestoreItemId);
		log.setBackupid(message.BackupId);
		log.setRestoreid(message.RestoreId);
		log.setType((int)(message.Type.ordinal()));
        session.save(log);
        session.getTransaction().commit();

	}
	
}

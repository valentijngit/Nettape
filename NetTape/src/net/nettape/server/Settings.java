package net.nettape.server;

import java.io.*;

import org.hibernate.Session;

import net.nettape.dal.HibernateUtil;
import net.nettape.dal.object.*;
import net.nettape.object.Constants;
import net.nettape.dal.*;
import java.util.Calendar;

public class Settings {
	private static net.nettape.dal.object.Settings settings = null;
	
	public static net.nettape.dal.object.Settings getSettings()
	{
		if(settings == null)
		{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			settings = (net.nettape.dal.object.Settings)session.get(net.nettape.dal.object.Settings.class, 0);
			session.getTransaction().commit();
		}
		return settings;
	}
	public static void save(net.nettape.dal.object.Settings value)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		value.setSettingsid(0);
		session.save(value);
		session.getTransaction().commit();
	}
	
}

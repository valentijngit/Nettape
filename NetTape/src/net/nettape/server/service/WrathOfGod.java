package net.nettape.server.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;
import net.nettape.object.Constants;
import net.nettape.server.BackupFileHandler;
import net.nettape.server.FileVersion;
import net.nettape.server.SmartPath;

public class WrathOfGod {
	// This deletes all files in all backups of all clients of all users that are no longer required for retention
	
	public static void Maintain()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		ArrayList<Client> clientList = (ArrayList<Client>) session
		        .createQuery("select c from Client as c order by c.id")
		        .list(); 	
	    session.getTransaction().commit();
		for(Client client : clientList)
		{
			session.beginTransaction();
			ArrayList<Backupset> backupsetList = (ArrayList<Backupset>) session
			        .createQuery("select bs from Backupset as bs where bs.clientid = :cid")
			        .setParameter("cid", client.getClientid())
			        .list(); 	
		    session.getTransaction().commit();
		    for(Backupset backupset : backupsetList)
		    {
		    	session.beginTransaction();
				ArrayList<Retentionschedule> retentionscheduleList = (ArrayList<Retentionschedule>) session
				        .createQuery("select rs from Retentionschedule as rs where rs.backupsetid = :bsid")
				        .setParameter("bsid", backupset.getBackupsetid())
				        .list(); 	
			    session.getTransaction().commit();
				session.beginTransaction();
				ArrayList<Backup> backupList = (ArrayList<Backup>) session
				        .createQuery("select b from Backup as b where b.backupsetid = :bsid order by b.datetime desc")
				        .setParameter("bsid", backupset.getBackupsetid())
				        .list(); 	
			    session.getTransaction().commit();
			    boolean isLatestBackup = true;
		    	for(Backup backup : backupList)
			    {
			    	BackupFileHandler backupFileHandler = new BackupFileHandler(backup, client);
			    	try
			    	{
			    		List<Backupitem> backupitemList = backupFileHandler.GetFilesAndFoldersRecursive();
			    		for(Backupitem backupitem: backupitemList)
			    		{
			    			boolean keep = false;
			    			if(isLatestBackup)
			    			{
			    				// this is the latest backup so only search in that backup for later versions
			    				SmartPath smartPath = backupFileHandler.FindEncodedPath(backupitem.getPath());
			    				smartPath.Version = backupitem.getVersion();
			    				if (backupFileHandler.IsLatestVersion(smartPath))
			    					keep = true;
			    			}
			    			else
			    			{
			    				//this is Not the latest backup found, so also see if in later backups there is a newer version
			    				if(IsLatestVersion(client,backupFileHandler,backupitem,backupList))
			    					keep = true;
			    				
			    			}
			    			if(!keep)
			    			{
			    				for(Retentionschedule retentionschedule : retentionscheduleList)
			    				{
			    					switch(retentionschedule.getDatetype())
			    					{
			    						case 1:
			    							//DAILY
			    							Calendar beginCalDaily = GregorianCalendar.getInstance();
			    							beginCalDaily.add(Calendar.DAY_OF_MONTH,retentionschedule.getCopies() - (retentionschedule.getCopies() *2));
			    							if(beginCalDaily.before(backupitem.getDatetime()))
			    							{
			    								// still have to check if this is the latest CDP version
			    								SmartPath smartPath = backupFileHandler.FindEncodedPath(backupitem.getPath());
			    			    				smartPath.Version = backupitem.getVersion();
			    								if(backupFileHandler.IsLatestVersion(smartPath))
			    									keep = true;
			    							}
			    							break;
			    						case 2:
			    							//WEEKLY
			    							//Only support one dayofweek
			    							//get the two dates on each side of the backupitem that fullfill the retention schedule
			    							Calendar backupitemCalWeekly = new GregorianCalendar();
			    							backupitemCalWeekly.setTime(backupitem.getDatetime());
			    							Calendar calculatorCal = new GregorianCalendar();
			    							List<Calendar> calendarList = new ArrayList<Calendar>();
			    							// go back copies + 1 to keep all the needed files
			    							Calendar previousCal = GregorianCalendar.getInstance();
			    			
			    							for (int i=0; i<retentionschedule.getCopies() + 1;i++)
			    							{
			    								while(true)
			    								{
			    									if(calculatorCal.get(Calendar.DAY_OF_WEEK) == retentionschedule.getDaysofweek())
			    									{
			    										break;
			    									}
			    									calculatorCal.add(Calendar.DAY_OF_MONTH, -1);
			    								}
			    								// check if the backupitem is between the previous and this one
			    								if(backupitemCalWeekly.before(previousCal) && backupitemCalWeekly.after(calculatorCal))
			    								{
			    									// now check if backupitem is the latest between those dates
			    									if (IsLatestVersionBetweenDates(client, backupFileHandler,backupitem,backupList,calculatorCal,previousCal))
			    										keep=true;
			    								}
			    								previousCal.setTime(calculatorCal.getTime());
			    							}
			    							
			    							break;
			    						case 3:
			    							//MONTHLY
			    							break;
			    						case 4:
			    							//YEARLY
			    							break;
			    						case 5:
			    							//CUSTOM
			    							break;
			    						default:
			    							break;
			    					}
			    				}
			    			}
			    			//TODO: create two methods in backupfilehandler
			    			// 1. to check if a file is the latest version
			    			// 2. to check if a file is the highest version between two dates
			    			
			    			//TODO: if latest version => keep
			    			// 		for(retentionschedules voor backupset)
			    			//		daily: vandaag - aantal dagen
			    			//				-> valt er tussen => keep
			    			//		monthly: 3x 2de van de maand
			    			//				-> make array met 3 laatste datums ( 2 oktober, 2 nov, 2 dec)
			    			//					-> is de file de laatste tss de 2 datums in begin en op het einde van zijn periode => keep
			    			//      yearly: zelfde principe als monthly
			    			
			    			// if not keep => delete
			    		}
			    	}
			    	catch(Exception ex)
			    	{
			    		//TODO: handle the fact that the files in this backup were not maintained
			    	}
			    	isLatestBackup = false;
			    }
		    }
		}
	}
	
	private static boolean IsLatestVersion(Client client, BackupFileHandler backupFileHandler, Backupitem backupitem, ArrayList<Backup> backupList) throws Exception
	{
		try
		{
			SmartPath smartPath = backupFileHandler.FindEncodedPath(backupitem.getPath());
			smartPath.Version = backupitem.getVersion();
			if(!backupFileHandler.IsLatestVersion(smartPath))
			{
				return false;
			}
			else
			{
				for(Backup backup: backupList)
				{
					if(backup.getDatetime().after(backupitem.getDatetime()))
					{
						BackupFileHandler thisBackupFileHandler = new BackupFileHandler(backup, client);
						SmartPath thisSmartPath = thisBackupFileHandler.FindEncodedPath(backupitem.getPath());
	    				thisSmartPath.Version = backupitem.getVersion();
						if(thisBackupFileHandler.FilesExist(thisSmartPath))
							return false;
					}
				}
			}
			return true;
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	
	private static boolean IsLatestVersionBetweenDates(Client client, BackupFileHandler backupFileHandler, Backupitem backupitem, ArrayList<Backup> backupList, Calendar beginCal, Calendar endCal) throws Exception
	{
		try
		{
			SmartPath smartPath = backupFileHandler.FindEncodedPath(backupitem.getPath());
			smartPath.Version = backupitem.getVersion();
			if(!backupFileHandler.IsLatestVersion(smartPath))
			{
				return false;
			}
			else
			{
				for(Backup backup: backupList)
				{
					if(backup.getDatetime().after(backupitem.getDatetime()))
					{
						Calendar backupCal = GregorianCalendar.getInstance();
						if(backupCal.after(beginCal) && backupCal.before(endCal))
						{
							BackupFileHandler thisBackupFileHandler = new BackupFileHandler(backup, client);
							SmartPath thisSmartPath = thisBackupFileHandler.FindEncodedPath(backupitem.getPath());
		    				thisSmartPath.Version = backupitem.getVersion();
							if(thisBackupFileHandler.FilesExist(thisSmartPath))
								return false;
						}
					}
				}
			}
			return true;
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
}

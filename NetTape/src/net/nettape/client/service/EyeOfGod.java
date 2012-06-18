package net.nettape.client.service;
/**
 * 
 */

/**
 * @author valentijn
 *
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
//import org.apache.derby.*;
//import org.apache.derby.drda.NetworkServerControl;
import java.security.Security;


import net.nettape.client.MessageHandler;
import net.nettape.client.ShadowHandler;
import net.nettape.client.command.LoginCommand;
import net.nettape.client.command.ReceiveBackupSetsCommand; 
import net.nettape.client.command.ReceiveSchedulesCommand;
import net.nettape.client.command.SendCDPItemCommand;
import net.nettape.client.command.SendBackupCommand;
import net.nettape.client.gui.AppConstants;
import net.nettape.connection.*;
import net.nettape.dal.object.Schedule;
import net.nettape.object.BackupSet;
import net.nettape.object.CDP;
import net.nettape.object.Message;
import net.nettape.object.RunningBackup;
import net.nettape.object.Constants;
import net.nettape.object.ModifiedFile;
import net.nettape.object.Constants.ScheduleType;
import net.nettape.object.Constants.ConnectionType;
public class EyeOfGod {

	private static Connection connection;
	private static LinkedList<BackupSet> backupSetList;
	private static LinkedList<CDP> cdpList;
	private static LinkedList<RunningBackup> runningBackupList;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			cdpList = new LinkedList<CDP>();
			runningBackupList = new LinkedList<RunningBackup>();
			System.out.println("Backup service starting...");
			Security.addProvider(new inFileDeltaProvider());
			AppConstants.getInstance();
			AppConstants.setSettings("settings.ini");
			TreeMap<String, String> p = AppConstants.getSettings().get("LOGIN");
			if(p != null)
			{
				String strServerName = p.get("ServerName");
				String strLoginName = p.get("LoginName");
				String strPassword = p.get("Password");
				String strConnectionType = p.get("ConnectionType");
				Integer connectionType = Integer.parseInt(strConnectionType);
				LoginCommand loginCommand = new LoginCommand(strServerName, ConnectionType.class.getEnumConstants()[connectionType], strLoginName ,strPassword);
				Boolean result = false;
				if(loginCommand.Execute())	
				{
					if(loginCommand != null && loginCommand.GetConnection() != null && loginCommand.GetConnection().IsLoggedIn())
					{
						connection = loginCommand.GetConnection();
						result = true;
					}
					else
					{
						result = false;
					}
				}
				else
				{
					result = false;
				}
				if(result)
				{
					// in a timer get all the backupsets from the server...
					int delay = 4000;   // delay for 10 sec.
					int period = 300000;
					Timer timer = new Timer();

					timer.scheduleAtFixedRate(new TimerTask() {
					        public void run() {
					            try
					            {
									// get the backup sets from the server
									ReceiveBackupSetsCommand receiveBackupSetsCommand = new ReceiveBackupSetsCommand(connection);
									backupSetList = receiveBackupSetsCommand.Execute();
									System.out.println("Backup Service started.");
									// so we have everything we need now from the server, lets see what should be done with it
									for (BackupSet backupSet : backupSetList)
									{
										if(backupSet.backupset.getContinuousprotection())
										{
											if(backupSet.backupset.getSnapshotcomplete())
											{
												// check if we already have a thread doing this CDP
												boolean add=true;
												for(CDP cdp : cdpList)
												{
													if(cdp.backupSet.backupset.getBackupsetid().equals(backupSet.backupset.getBackupsetid()))
													{
														if(cdp.thread.isAlive())
														{
															add=false;
														}
														else
														{
															cdpList.remove(cdp);
															//TODO: how to destroy a thread?
															cdp = null;
														}
													}
												}
												if(add)
												{
													Thread thread = new Thread(new HandleCDP(connection,backupSet));
													thread.start();
													CDP cdp = new CDP();
													cdp.thread = thread;
													cdp.backupSet = backupSet;
													// to be able to stop a thread when a backupset has been altered on the server, lets keep this in a list
													cdpList.add(cdp);
												}
												
											}
											else
											{
												//TODO: do a complete snapshot backup
											
												RunBackup(backupSet);
											}
											
										}
										if(! backupSet.backupset.getContinuousprotection())
										{
											// ok, we check the time to run this backup and maybe start a thread for it
											// get the schedules for this backupset
											ReceiveSchedulesCommand receiveSchedulesCommand = new ReceiveSchedulesCommand(connection,backupSet.backupset);
											LinkedList<Schedule> scheduleList = receiveSchedulesCommand.Execute();
											for(Schedule schedule : scheduleList)
											{
												//first calculate at what date and time the schedule should be run next
												Calendar currentCal;
												switch (schedule.getDatetype())
												{
												case 1:
													currentCal = GregorianCalendar.getInstance();
													currentCal.set(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
															currentCal.get(Calendar.DAY_OF_MONTH),0,0,0);
													if((schedule.getLastrundate() == null) || (schedule.getLastrundate().before(currentCal.getTime())))
													{
														//compare the time to run the schedule with the current time, and check if running already
														currentCal = GregorianCalendar.getInstance();
														Calendar scheduleCal = GregorianCalendar.getInstance();
														scheduleCal.set(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
																currentCal.get(Calendar.DAY_OF_MONTH),schedule.getTime().getHours(),schedule.getTime().getMinutes(),0);
														if(scheduleCal.before(currentCal))
														{
															if(! schedule.getRunning())
															{
																RunBackup(backupSet);
															}
														}
													
													}
													break;
												case 2:
													currentCal = GregorianCalendar.getInstance();
													Integer dayOfWeek = currentCal.get(Calendar.DAY_OF_WEEK );
													currentCal.set(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
															currentCal.get(Calendar.DAY_OF_MONTH),0,0,0);
													if((schedule.getLastrundate() == null) || (schedule.getLastrundate().before(currentCal.getTime())))
													{
														//see if this day of week is in daysofweek field
														String strDayOfWeek = dayOfWeek.toString();
														String strDaysOfWeek = schedule.getDaysofweek().toString();
														if(strDaysOfWeek.indexOf(strDayOfWeek) >= 0) 
														{
															//compare the time to run the schedule with the current time, and check if running already
															currentCal = GregorianCalendar.getInstance();
															Calendar scheduleCal = GregorianCalendar.getInstance();
															scheduleCal.set(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
																	currentCal.get(Calendar.DAY_OF_MONTH),schedule.getTime().getHours(),schedule.getTime().getMinutes(),0);
															if(scheduleCal.before(currentCal))
															{
																if(! schedule.getRunning())
																{
																	RunBackup(backupSet);
																}
															}
														}
													
													}
													break;
												case 3:
													currentCal = GregorianCalendar.getInstance();
													Integer dayOfMonth = currentCal.get(Calendar.DAY_OF_MONTH  );
													currentCal.set(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
															currentCal.get(Calendar.DAY_OF_MONTH),0,0,0);
													if((schedule.getLastrundate() == null) || (schedule.getLastrundate().before(currentCal.getTime())))
													{
														//see if this day is the day to run the backup on
														
														
														if(dayOfMonth == schedule.getDay()) 
														{
															//compare the time to run the schedule with the current time, and check if running already
															currentCal = GregorianCalendar.getInstance();
															Calendar scheduleCal = GregorianCalendar.getInstance();
															scheduleCal.set(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
																	currentCal.get(Calendar.DAY_OF_MONTH),schedule.getTime().getHours(),schedule.getTime().getMinutes(),0);
															if(scheduleCal.before(currentCal))
															{
																if(! schedule.getRunning())
																{
																	RunBackup(backupSet);
																}
															}
														}
													
													}
													break;
												//TODO: case 4 and check case 5!!!!! YEARLY was added later
												case 5:
													currentCal = GregorianCalendar.getInstance();
													currentCal.set(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
															currentCal.get(Calendar.DAY_OF_MONTH),0,0,0);
													if((schedule.getLastrundate() == null) || (schedule.getLastrundate().before(currentCal.getTime())))
													{
														//see if this date is the date to run the backup on
														
														if(schedule.getDate().equals(currentCal.getTime())) 
														{
															//compare the time to run the schedule with the current time, and check if running already
															currentCal = GregorianCalendar.getInstance();
															Calendar scheduleCal = GregorianCalendar.getInstance();
															scheduleCal.set(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
																	currentCal.get(Calendar.DAY_OF_MONTH),schedule.getTime().getHours(),schedule.getTime().getMinutes(),0);
															if(scheduleCal.before(currentCal))
															{
																if(! schedule.getRunning())
																{
																	RunBackup(backupSet);
																}
															}
														}
													
													}
													break;
												}
											}
										}

									}
    
					            }
					            catch(Exception ex)
					            {
					            	Message message = new Message();
					            	message.Message = "A problem occured in looking up backupsets on the server.";
					            	message.Type = Constants.MessageType.SYSTEM_ERROR;
					            	MessageHandler.HandleMessage(ex, false, "", true, message, null);
					            }
					        }
					    }, delay, period);

					
					
				}
				else
				{
	            	Message message = new Message();
	            	message.Message = "Could not start clientside service.";
	            	message.Type = Constants.MessageType.SYSTEM_ERROR;
	            	MessageHandler.HandleMessage(null, false, "", true, message, null);
				}
				
			}
			else
			{
            	Message message = new Message();
            	message.Message = "Could not start clientside service.";
            	message.Type = Constants.MessageType.SYSTEM_ERROR;
            	MessageHandler.HandleMessage(null, false, "", true, message, null);
				
			}
		}
		catch (Exception e)
		{
        	Message message = new Message();
        	message.Message = "Could not start clientside service.";
        	message.Type = Constants.MessageType.SYSTEM_ERROR;
        	MessageHandler.HandleMessage(null, false, "", true, message, null);
		}

	}
	
	private static void RunBackup(BackupSet backupSet)
	{
		//ok, start the backup for this backupset in a new thread
		// check if we already have a thread doing this Backup
		boolean add=true;
		LinkedList<RunningBackup> listToRemove = new LinkedList<RunningBackup>();
		for(RunningBackup runningBackup : runningBackupList)
		{
			if(runningBackup.backupSet.backupset.getBackupsetid().equals(backupSet.backupset.getBackupsetid()))
			{
				if(runningBackup.thread.isAlive())
				{
					add=false;
				}
				else
				{
					listToRemove.add(runningBackup);
					runningBackup = null;
				}
			}
		}
		for(RunningBackup runningBackup : listToRemove)
		{			//TODO: how to stop a thread?
			runningBackupList.remove(runningBackup);
		}
		if(add)
		{
			
			Thread thread = new Thread(new HandleBackup(connection,backupSet));
			thread.start();
			
			RunningBackup runningBackup = new RunningBackup();
			runningBackup.thread = thread;
			runningBackup.backupSet = backupSet;
			// to be able to stop a thread when a backupset has been altered on the server, lets keep this in a list
			// TODO: i think the thread should check the server and stop itself instead.!!
			runningBackupList.add(runningBackup);
		}
			

	}
}

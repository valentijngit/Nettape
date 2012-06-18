package net.nettape.client.test;
/**
 * 
 */

/**
 * @author valentijn
 *
 */

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
//import org.apache.derby.*;
//import org.apache.derby.drda.NetworkServerControl;
import java.security.Security;


import net.nettape.client.FilePermissions;
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
import net.nettape.object.RunningBackup;
import net.nettape.object.Constants;
import net.nettape.object.ModifiedFile;
import net.nettape.object.Constants.ScheduleType;
import net.nettape.object.Permissions; 

public class Test {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Permissions permissions = new Permissions();
		FilePermissions.GetPermissions("c:\\test.txt", permissions);
		FilePermissions.SetPermissions("c:\\test2.txt", permissions);
	}
	
}

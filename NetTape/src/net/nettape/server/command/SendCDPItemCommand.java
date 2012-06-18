package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import net.nettape.Config;
import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.connection.InFileDelta;
import net.nettape.object.FileInfo;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;
import net.nettape.object.*;
import net.nettape.server.*;

public class SendCDPItemCommand extends ServerCommand
{
	private Backup backup;
	private BackupFileHandler backupFileHandler;
	private BackupFileHandler oldBackupFileHandler;
	private Backupset backupset;
	private Backupsetitem backupsetitem;
	private BackupHelper backupHelper;
	public SendCDPItemCommand(Connection connection)
	{
		super(connection);
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{	
				SendBackupItemCommand sendBackupItemCommand = new SendBackupItemCommand(this.connection);
				sendBackupItemCommand.Execute();
		    }

			catch(Exception ex)
			{
				//TODO backup did not succeed.
				System.out.println(ex.getMessage());
			}
		}
	}

}

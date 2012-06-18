package net.nettape.client.service;
import net.nettape.connection.Connection;
import net.nettape.object.BackupSet;
import net.nettape.client.command.*;



public class HandleBackup implements Runnable
{
	private Connection connection;
	private BackupSet backupSet;
	private SendBackupCommand sendBackupCommand;
	public HandleBackup(Connection connection, BackupSet backupSet)
	{
		this.connection = connection;
		this.backupSet = backupSet;
	}

	public void run()
	{
		try
		{
			sendBackupCommand = new SendBackupCommand(connection,backupSet, null);
			sendBackupCommand.Execute(false);
		}
		catch (Exception ex)
		{
		}
	}
}

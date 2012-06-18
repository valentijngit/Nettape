package net.nettape.client.command;

import java.io.*;

import net.nettape.client.FileCopier;
import net.nettape.client.ShadowHandler;
import net.nettape.client.SmartFile;
import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.FileInfo;


public class SendFileCommand extends ClientCommand
{

	private FileInfo fileInfo;
	private ShadowHandler shadowHandler;
	public SendFileCommand(Connection connection, String Path, ShadowHandler shadowHandler)
	{
		super(connection, false);
		this.shadowHandler = shadowHandler;
		this.fileInfo = new FileInfo(Path,new File(Path).length(),false);
	}

	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				connection.SendCommand(Command.SENDFILE);
				connection.sendObject(this.fileInfo);
				FileCopier fileCopier = new FileCopier();
				SmartFile openFile = fileCopier.openFile(fileInfo.GetPath());
				if(openFile == null)
				{
					openFile = shadowHandler.openFileWithShadowCopy(fileInfo.GetPath());
				}
				
				connection.sendFileOnClient(openFile);
				openFile.Delete();
			}
			catch(Exception ex)
			{
				System.out.println("Error sending a file.");
			}
		}
	}
}


package net.nettape.object;

import java.io.*;
import net.nettape.dal.object.*;

public class RunningBackup implements Serializable{
	
	public BackupSet backupSet;
	public Thread thread;
	
	public RunningBackup()
	{

	}

}


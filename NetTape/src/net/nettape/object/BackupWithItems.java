package net.nettape.object;

import java.io.*;
import net.nettape.dal.object.*;
import java.util.ArrayList;
import java.util.Set;

public class BackupWithItems implements Serializable{
	
	public Backup backup;
	public Set<Backupitem> backupitems;

	
	public BackupWithItems()
	{

	}

}

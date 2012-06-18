package net.nettape.object;

import java.io.*;
import net.nettape.dal.object.*;
import java.util.ArrayList;
import java.util.Set;

public class BackupSet implements Serializable{
	
	public Backupset backupset;
	public Set<Backupsetitem> backupsetitems;
	public Set<Filter> filters;
	public boolean running = false;
	public BackupSet()
	{

	}

}

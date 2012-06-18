package net.nettape.object;

import java.io.*;
import net.nettape.dal.object.*;
import java.util.ArrayList;
import java.util.HashSet;

public class RestoreWithBackupItems implements Serializable{
	
	public Restore restore;
	public HashSet<RestoreItem> restoreItems;

	
	public RestoreWithBackupItems()
	{

	}

}

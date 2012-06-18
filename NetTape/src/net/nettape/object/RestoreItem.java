package net.nettape.object;

import java.io.*;
import net.nettape.dal.object.*;
import java.util.ArrayList;

public class RestoreItem implements Serializable{

	public Restoreitem restoreitem;
	public Backupitem backupitem;
	
	public RestoreItem(Restoreitem restoreitem, Backupitem backupitem)
	{
		this.backupitem = backupitem;
		this.restoreitem = restoreitem;
	}

}

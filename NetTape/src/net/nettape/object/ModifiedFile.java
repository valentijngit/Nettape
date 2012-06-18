package net.nettape.object;

import java.util.Date;
import net.nettape.dal.object.*;

public class ModifiedFile {

	public String root;
	public String path;
	public Backupsetitem backupsetitem;
	public boolean isDeleted = false;
	public boolean isRenamed = false;
	public boolean isDeltas = false;
	public Date dateTime;
}

package net.nettape.object;

import java.util.Comparator;

import net.nettape.dal.object.Backupsetitem;

public class BackupsetitemComparator implements Comparator<Backupsetitem>
{

	public int compare(Backupsetitem backupsetitem1, Backupsetitem backupsetitem2)
	{
		try
		{
			String path1 = backupsetitem1.getPath(); 
			String path2 = backupsetitem2.getPath();
			if (path1 == null)
			return -1;
			else
			{
				int result = path1.compareTo(path2);
				if(result == 0)
				{
					// they are equal, but folders go before files
					if(backupsetitem1.getIsfolder() && backupsetitem2.getIsfolder())
					{
						return result;
					}
					else if(backupsetitem1.getIsfolder() && (!(backupsetitem2.getIsfolder())))
					{
						return -1;
					}
					else if((!(backupsetitem1.getIsfolder())) && backupsetitem2.getIsfolder())
					{
						return 1;
					}
					else if((!(backupsetitem1.getIsfolder())) && (!(backupsetitem2.getIsfolder())))
					{
						return result;
					}
					return result;
				}
				else
				{
					return result;
				}
			}
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}

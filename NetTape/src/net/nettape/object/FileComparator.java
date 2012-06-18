package net.nettape.object;

import java.io.File;
import java.util.Comparator;

import net.nettape.dal.object.Backupsetitem;

public class FileComparator implements Comparator<File>
{

	public int compare(File file1, File file2)
	{
		try
		{
			String path1 = file1.getPath(); 
			String path2 = file2.getPath();
			if (path1 == null)
			return -1;
			else
			{
				int result = path1.compareTo(path2);
				if(result == 0)
				{
					// they are equal, but folders go before files
					if(file1.isDirectory() && file2.isDirectory())
					{
						return result;
					}
					else if(file1.isDirectory() && (!(file2.isDirectory())))
					{
						return -1;
					}
					else if((!(file1.isDirectory())) && file2.isDirectory())
					{
						return 1;
					}
					else if((!(file1.isDirectory())) && (!(file2.isDirectory())))
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

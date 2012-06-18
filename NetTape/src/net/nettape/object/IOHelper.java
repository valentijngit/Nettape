package net.nettape.object;

import java.io.File;

public class IOHelper {
	public class Filename {
		  private String fullPath;
		  private char pathSeparator, extensionSeparator;

		  public Filename(String str, char sep, char ext) {
		    fullPath = str;
		    pathSeparator = sep;
		    extensionSeparator = ext;
		  }

		  public String extension() {
		    int dot = fullPath.lastIndexOf(extensionSeparator);
		    return fullPath.substring(dot + 1);
		  }

		  public String filename() { // gets filename without extension
		    int dot = fullPath.lastIndexOf(extensionSeparator);
		    int sep = fullPath.lastIndexOf(pathSeparator);
		    if(dot > -1)
		    {
		    	return fullPath.substring(sep + 1, dot);
		    }
		    else
		    {
		    	return fullPath.substring(sep + 1);
		    }
		  }
		  
		  public String fullname() { // gets filename with extension
			  int sep = fullPath.lastIndexOf(pathSeparator);
			  return fullPath.substring(sep + 1);
		  }

		  public String path() {
		    int sep = fullPath.lastIndexOf(pathSeparator);
		    return fullPath.substring(0, sep);
		  }
		  public String root() {
			int sep = fullPath.indexOf(pathSeparator);
			if(sep > 0)
			{
				return fullPath.substring(0,sep);
			}
			else
			{
				return fullPath;
			}
		  }
		}
	public Filename filename;
	public String checkFolderForSeperatorEnd(String folder)
	{
		translateAbstractPath(makePathAbstract(folder));
		if (folder.lastIndexOf(File.separator) == folder.length() - 1)
			return folder;
		else
			return folder +File.separator;
	}
	
	public String makePathAbstract(String path)
	{
		path = path.replace(File.separatorChar, '/');
		return path.replace('\\', '/');
	}
	public String translateAbstractPath(String path)
	{
		return path.replace('/',File.separatorChar);
	}
	public void createFoldersInPath(String path)
	{
		translateAbstractPath(makePathAbstract(path));
		if(!(new File(path).exists())) 
		{
			new File(path).mkdirs();
		}
	}
	public String StripSpecialChars(String path)
	{
		path = translateAbstractPath(makePathAbstract(path));
		int sep = path.indexOf(File.separator);
		String root = path.substring(0,sep);
		path = path.substring(sep);
		path = path.replace(":", "");
		path = path.replace("*", "");
		path = path.replace("?", "");
		path = path.replace("\"", "");
		path = path.replace("<", "");
		path = path.replace(">", "");
		path = path.replace("|", "");
		return root + path;
	}
}

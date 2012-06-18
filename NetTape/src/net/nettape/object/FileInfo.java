package net.nettape.object;

import java.io.*;

public class FileInfo implements Serializable{
	
	private String Path;
	private long Size;
	private boolean isFolder;
	
	public FileInfo(String Path, long Size, boolean isFolder)
	{
		this.Path = Path;
		this.Size = Size;
		this.isFolder = isFolder;
	}
	public long GetSize()
	{
		return this.Size;
	}
	public String GetPath()
	{
		return this.Path;
	}
	public boolean GetIsFolder()
	{
		return this.isFolder;
	}

}

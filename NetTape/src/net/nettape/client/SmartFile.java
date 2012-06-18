package net.nettape.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.InflaterOutputStream;

public class SmartFile {
	
	private File file;
	
	public SmartFile(String path)
	{
		file = new File(path);
	}
	
	public InputStream GetInputStream() throws FileNotFoundException
	{
		//return new DeflaterInputStream(new FileInputStream(file));
		return new FileInputStream(file);
	}
	
	public OutputStream GetOutputStream() throws FileNotFoundException
	{
		//return new InflaterOutputStream(new FileOutputStream(file));
		return new FileOutputStream(file);
	}
	
	public String GetAbsolutePath()
	{
		return file.getAbsolutePath();
	}
	
	public void Delete()
	{
		file.delete();
	}

}

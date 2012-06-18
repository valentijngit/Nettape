package net.nettape.client;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.StandardCopyOption;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.nettape.Config;
import net.nettape.object.IOHelper;

public class FileCopier {
	
	
	public String copyFileName;
	
	public FileCopier()
	{}
	
	private void copyFile(String srFile, String dtFile) throws Exception
	{
		/*
		FileInputStream in = new FileInputStream(srFile);
		FileOutputStream out = new FileOutputStream(dtFile);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
	    */
		java.nio.file.Path srPath = FileSystems.getDefault().getPath(srFile);
		java.nio.file.Path dtPath = FileSystems.getDefault().getPath(dtFile);
		srPath.copyTo(dtPath, StandardCopyOption.REPLACE_EXISTING);
		srPath = null;
		dtPath = null;
		
	}
	
	
	private void copyAndCompressFile(String srFile, String dtFile) throws Exception
	{
		FileInputStream in = new FileInputStream(srFile);
		BufferedOutputStream out = new BufferedOutputStream(
              new GZIPOutputStream(new FileOutputStream(dtFile)));
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
	    /*
		java.nio.file.Path srPath = FileSystems.getDefault().getPath(srFile);
		java.nio.file.Path dtPath = FileSystems.getDefault().getPath(dtFile);
		srPath.copyTo(dtPath, StandardCopyOption.REPLACE_EXISTING);
		srPath = null;
		dtPath = null;
		*/
	}
	public void copyAndDecompressFile(String srFile, String dtFile) throws Exception
	{
		BufferedInputStream in = new BufferedInputStream(new GZIPInputStream(new FileInputStream(srFile)));
		FileOutputStream out = new FileOutputStream(dtFile);
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.close();
	    /*
		java.nio.file.Path srPath = FileSystems.getDefault().getPath(srFile);
		java.nio.file.Path dtPath = FileSystems.getDefault().getPath(dtFile);
		srPath.copyTo(dtPath, StandardCopyOption.REPLACE_EXISTING);
		srPath = null;
		dtPath = null;
		*/
	}
	
	public SmartFile openFile(String fileName) throws Exception 
	{
		IOHelper iOHelper = new IOHelper();
		
		// make sure this is correct for this file system!
		fileName = iOHelper.translateAbstractPath(iOHelper.makePathAbstract(fileName));
		if(!new File(Config.getProperty("TempPath")).exists())
		{
			new File(Config.getProperty("TempPath")).mkdir();
		}
		int number = 1;
		while(true)
		{
			if(new File(Config.getProperty("TempPath")  + new File(fileName).getName() + number).exists())
			{
				number ++;
			}
			else
			{
				break;
			}
		}
		this.copyFileName = Config.getProperty("TempPath")  + new File(fileName).getName() + number;
		try
		{
			copyFile(fileName,this.copyFileName);
			return new SmartFile(this.copyFileName);
		}
		catch (Exception ex)
		{
			return null;
		}
		
	}
		
	public void closeFile() throws Exception
	{
		/*TODO:
		 * this gave a class not found exception, please INVESTIGATE!
		java.nio.file.Path path = FileSystems.getDefault().getPath(this.copyFileName);
		path.deleteIfExists();
		*/
		new File(this.copyFileName).delete();
	}
}

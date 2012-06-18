package net.nettape.client;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.StandardCopyOption;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.nettape.Config;
import net.nettape.object.IOHelper;

public class ShadowHandler {
	
	private Process shadowProcess = null;
	private PrintWriter shadowCommand = null;
	private boolean useShadowCopy = false;
	public ShadowHandler()
	{}
	public ShadowHandler(String root, boolean useShadowCopy) throws Exception
	{
		this.useShadowCopy = useShadowCopy;
		if(System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0 && useShadowCopy)
		{
			shadowProcess = Runtime.getRuntime().exec("D:\\Mijn documenten\\workspace\\NetTape\\required\\shadowcopyconsole\\shadowcopy");
			OutputStream cmd_input = shadowProcess.getOutputStream();
			
			shadowCommand = new PrintWriter(new BufferedWriter(new OutputStreamWriter(cmd_input)));
			shadowCommand.println("start");
			shadowCommand.flush();
			shadowCommand.println(root);
			shadowCommand.flush();
			//TODO: check if no exceptions from external application, otherwise send error to server and set useShadowCopy = false
		}
	}
	
	public void closeShadow() 
	{
		if(useShadowCopy && (shadowCommand != null))
		{
			shadowCommand.println("stop");
			shadowCommand.flush();
		}
	}
	public boolean canOpenFile(String file)
	{
		if(!useShadowCopy)
		{
			try
			{
				File openFile = new File(file);
				if(openFile.canRead())
				{
					openFile = null;
					return true;
				}
				else
				{
					openFile = null;
					return false;
				}
			}
			catch(Exception ex)
			{
				return false;
			}
		}
		else return true;
	}
	public boolean canOpenFileWithoutShadow(String file)
	{
		try
		{
			File openFile = new File(file);
			if(openFile.canRead())
			{
				openFile = null;
				return true;
			}
			else
			{
				openFile = null;
				return false;
			}
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	public boolean isDirectory(String file)
	{
		File openFile = new File(file);
		return openFile.isDirectory();
	}

	
	private void copyFile(String srFile, String dtFile) throws Exception
	{
		FileInputStream in = new FileInputStream(srFile);
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
	
	public String tempFileForRestore(String fileName) throws Exception
	{
		int number = 1;
		while(true)
		{
			if(new File(Config.getProperty("TempPath")  + fileName + number).exists())
			{
				number ++;
			}
			else
			{
				break;
			}
		}
		return Config.getProperty("TempPath") + fileName + number;
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
	private void copyShadowFile(String srFile, String dtDir) throws Exception
	{
		shadowCommand.println("copy");
		shadowCommand.flush();
		shadowCommand.println(srFile);
		shadowCommand.flush();
		shadowCommand.println(dtDir);
		shadowCommand.flush();
	}

	
	public SmartFile openFileWithShadowCopy(String fileName)
	{
		if(useShadowCopy)
		{
			try
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
				String copyFileName = Config.getProperty("TempPath")  + new File(fileName).getName() + number;
				copyShadowFile(fileName,copyFileName);
				return new SmartFile(copyFileName);
			}
			catch (Exception e)
			{
				return null;
			}
			
		}
		else return null;
	}
	
	
}

package net.nettape.server;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.hibernate.Session;

import net.nettape.dal.HibernateUtil;
import net.nettape.dal.object.*;
import net.nettape.object.Constants;
import net.nettape.dal.*;
import net.nettape.exception.NoSnapshotException;

import java.util.Calendar;
import static java.nio.file.StandardCopyOption.*;

public class TempFileHandler {
	private RandomAccessFile randomAccessFile;
	private RandomAccessFile fileOutputStream;
	private String path;
	public TempFileHandler(String path) throws IOException {
		this.path = path;
	}
	
	public void Rename(String newName) throws NoSnapshotException
	{
		try
		{
			/*
			 * this gave errors
			 */
			java.nio.file.Path path = FileSystems.getDefault().getPath(this.path);
			java.nio.file.Path path2 = FileSystems.getDefault().getPath(newName);
			Files.move(path, path2, REPLACE_EXISTING);
			/*
			FileInputStream in = 
		            new FileInputStream(this.path);
			FileOutputStream out = 
					new FileOutputStream(newName);
	        int c;
	        while ((c = in.read()) != -1) out.write(c);
	        in.close();
	        out.close();
			new File(this.path).delete();*/
		}
		catch(Exception ex)
		{
			throw new NoSnapshotException();
		}
	}
	
	public void Delete() throws NoSnapshotException
	{
		try
		{
			/*
			 * this gave errors noclassdeffound
			 *
			java.nio.file.Path path = FileSystems.getDefault().getPath(this.path);
			path.deleteIfExists();
			*/
			new File(this.path).delete();
		}
		catch(Exception ex)
		{
			throw new NoSnapshotException();
		}
	}
	
	public void OpenFileForOutput()
	{
		try {
			File file = new File(path);
			file.createNewFile();
			this.fileOutputStream = new RandomAccessFile(path,"rw");
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException ioex)
		{
			
		}
	}
	public void OpenFileForInput(long Position) throws IOException
	{
		try {
			this.randomAccessFile = new RandomAccessFile(path,"rw");
			randomAccessFile.seek(Position);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public RandomAccessFile getFileOutputStream()
	{
		return this.fileOutputStream;        

	}
	public RandomAccessFile getFileInputStream()
	{
		return this.randomAccessFile;   

	}
	public void CloseFileForOutput()
	{
		try {
			this.fileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void CloseFileForInput()
	{
		try {
			this.randomAccessFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

package net.nettape.server;

import java.io.*;

import org.hibernate.Session;

import net.nettape.dal.HibernateUtil;
import net.nettape.dal.object.*;
import net.nettape.object.Constants;
import net.nettape.object.Constants.BackupItemType;
import net.nettape.object.IOHelper;
import net.nettape.object.WildCardFileFilter;
import net.nettape.dal.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class BackupFileHandler {
	private FileOutputStream fileOutputStream;
	private RandomAccessFile randomAccessFile;
	private FileInputStream fileInputStream;
	private net.nettape.dal.object.Settings settings;
	private String clientPath;
	private String backupPath;
	private Client client;
	public Backup backup;

	public BackupFileHandler(Backup backup, Client client) {
		this.backup = backup;
		this.client = client;
		this.settings = net.nettape.server.Settings.getSettings();
		IOHelper ioHelper = new IOHelper();
		String userHome = ioHelper.checkFolderForSeperatorEnd(this.settings.getUserhome());
		this.clientPath = userHome + client.getClientid() + File.separator;
		if(!new File(userHome + client.getClientid()).exists())
		{
			new File(userHome + client.getClientid()).mkdir();
		}
		if(backup.getName() != null)
		{
			this.backupPath = clientPath + backup.getName() + File.separator;
		}
	}
	
	public String CreateBackupFolder()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(backup.getDatetime());
		String fileName = "" + cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.MONTH) + cal.get(Calendar.YEAR);
		if((new File(clientPath + fileName).exists()))
		{
			int number = 2;
			while ((new File(clientPath + fileName + number).exists()))
			{
				number++;
			}
			innerwhile:
			while (true)
			{
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.beginTransaction();
		        Backup backup = (Backup) session
		        .createQuery("select b from Backup b where b.name = :name")
		        .setParameter("name", fileName + number)
		        .uniqueResult(); 
				session.getTransaction().commit();
				if(backup == null)
				{
					break innerwhile;
				}
				number++;
			}
			this.backup.setName(fileName + number);
			this.backupPath = clientPath + fileName + number + File.separator;
		}
		else
		{
			this.backup.setName(fileName);
			this.backupPath = clientPath + fileName + File.separator;
		}
		File file = new File(this.backupPath);
		if(!file.exists())
			file.mkdir();
		file = null;
		return this.backup.getName();
	}
	
	public void CreateFolder(SmartPath path)
	{
		IOHelper iOHelper = new IOHelper();
		String realPath = this.backupPath + path.EncodedPath;
		File file = new File(realPath);
		if(!file.exists())
		{
			iOHelper.createFoldersInPath(realPath);
		}
		file = null;
	}
	
	public boolean FilesExist(SmartPath path)
	{
		String folder = path.EncodedPath.substring(0, path.EncodedPath.lastIndexOf(File.separator));
		String folderIncludingBackupPath = this.backupPath + folder;
		File dir = new File(folderIncludingBackupPath);
		String fileName = path.EncodedPath.substring(path.EncodedPath.lastIndexOf(File.separator) + 1);
		File[] files = dir.listFiles(new WildCardFileFilter("??????????" , fileName));
		if(files != null)
		{
			if(files.length > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
			return false;
		
	}
	
	private String GetFolderPart(String path)
	{
		IOHelper iOHelper = new IOHelper();
		path = iOHelper.translateAbstractPath(iOHelper.makePathAbstract(path));
		if(path.lastIndexOf(File.separator) > -1)
		{
			return path.substring(0, path.lastIndexOf(File.separator)) + File.separator;
		}
		else
		{
			return path;
		}
	}
	
	private String GetFilePart(String path)
	{
		IOHelper iOHelper = new IOHelper();
		path = iOHelper.translateAbstractPath(iOHelper.makePathAbstract(path));
		if(path.lastIndexOf(File.separator) > -1)
		{
			return path.substring(path.lastIndexOf(File.separator) + 1);
		}
		else
		{
			return path;
		}
	}
	
	private void OpenFileForOutput(SmartPath path, Constants.BackupItemType type, boolean isDeltas, boolean isPermissions, int cDPVersion) throws IOException
	{
		IOHelper iOHelper = new IOHelper();
		iOHelper.createFoldersInPath(this.backupPath + GetFolderPart(path.EncodedPath));
		this.fileOutputStream = new FileOutputStream(this.backupPath + iOHelper.checkFolderForSeperatorEnd(GetFolderPart(path.EncodedPath))
				+ AddInfo(GetFilePart(path.EncodedPath),type,isDeltas,isPermissions,cDPVersion), false);
	}
	
	public void OpenFileForOutput(SmartPath path, Constants.BackupItemType type, boolean isDeltas, boolean isPermissions) throws IOException
	{

		IOHelper iOHelper = new IOHelper();
		int cDPVersion = 1;
		while(true)
		{	
			if(!new File(this.backupPath + iOHelper.checkFolderForSeperatorEnd(GetFolderPart(path.EncodedPath))
					+ AddInfo(GetFilePart(path.EncodedPath),type,isDeltas,isPermissions,cDPVersion)).exists())
			{
				break;
			}
			cDPVersion++;
		}
		OpenFileForOutput(path,type,isDeltas,isPermissions,cDPVersion);
	}
	public void OpenFileForInput(FileVersion fileVersion) throws IOException
	{
		IOHelper iOHelper = new IOHelper();
		String path = this.backupPath + iOHelper.checkFolderForSeperatorEnd(GetFolderPart(fileVersion.SmartPath.EncodedPath))
				+ AddInfo(GetFilePart(fileVersion.SmartPath.EncodedPath),fileVersion.BackupItemType,fileVersion.IsDeltas,fileVersion.IsPermissions,fileVersion.Version);
		path = iOHelper.translateAbstractPath(iOHelper.makePathAbstract(path));
		File tempFile = new File(path);
		if(tempFile.exists())
		{
			this.randomAccessFile = new RandomAccessFile(path,"rw");
		}
	}
	public void OpenFileForPermissionsInput(FileVersion fileVersion) throws IOException
	{
		IOHelper iOHelper = new IOHelper();
		String path = this.backupPath + iOHelper.checkFolderForSeperatorEnd(GetFolderPart(fileVersion.SmartPath.EncodedPath))
				+ AddInfo(GetFilePart(fileVersion.SmartPath.EncodedPath),fileVersion.BackupItemType,fileVersion.IsDeltas,fileVersion.IsPermissions,fileVersion.Version);
		path = iOHelper.translateAbstractPath(iOHelper.makePathAbstract(path));
		File tempFile = new File(path);
		if(tempFile.exists())
		{
			this.fileInputStream = new FileInputStream(path);
		}
	}
	public FileVersion SearchLatestVersion(SmartPath smartPath) throws IOException, Exception
	{
		try
		{
			// gets the latest version (for cdp) of a file in the current backup opened by this backup file handler
			FileVersion fileVersion = new FileVersion();
			String folder = smartPath.EncodedPath.substring(0, smartPath.EncodedPath.lastIndexOf(File.separator));
			String folderIncludingBackupPath = this.backupPath + folder;
			File dir = new File(folderIncludingBackupPath);
			String fileName = smartPath.EncodedPath.substring(smartPath.EncodedPath.lastIndexOf(File.separator) + 1);
			File[] files = dir.listFiles(new WildCardFileFilter("??0???????", fileName));
			if(files != null)
			{
				if(files.length > 0)
				{
					int biggestCDPVersion = 0;
					for(File file: files)
					{
						int cDPVersion = GetVersion(file.getName());
						if(cDPVersion >= biggestCDPVersion) 
						{
							fileName = file.getName();
							biggestCDPVersion = cDPVersion;
						}
					}
					this.randomAccessFile = new RandomAccessFile(folderIncludingBackupPath + File.separator + fileName,"rw");
					fileVersion.SmartPath = smartPath;
					fileVersion.Version = biggestCDPVersion;
					fileVersion.Backupid = this.backup.getBackupid();
					fileVersion.BackupItemType = GetType(fileName);
					fileVersion.IsDeltas = GetIsDeltas(fileName);
					fileVersion.IsPermissions = GetIsPermissions(fileName);
					return fileVersion;
				}
				else
				{
					fileVersion.Version = -1;
					return fileVersion;
				}
				}
			else
			{
				fileVersion.Version = -1;
				return fileVersion;
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
			
	}

	public FileVersion SearchSpecificVersion(SmartPath smartPath, int version) throws IOException
	{
		// gets a specific version (for cdp) of a file in the current backup opened by this backup file handler
		FileVersion fileVersion = new FileVersion();
		String folder = smartPath.EncodedPath.substring(0, smartPath.EncodedPath.lastIndexOf(File.separator));
		String folderIncludingBackupPath = this.backupPath + folder;
		File dir = new File(folderIncludingBackupPath);
		String fileName = smartPath.EncodedPath.substring(smartPath.EncodedPath.lastIndexOf(File.separator) + 1);
		File[] files = dir.listFiles(new WildCardFileFilter("??0???????", fileName));
		if(files != null)
		{
			if(files.length > 0)
			{
				boolean found = false;
				for(File file: files)
				{
					int cDPVersion = GetVersion(file.getName());
					if(cDPVersion == version) 
					{
						fileName = file.getName();
						found = true;
						break;
					}
				}
				if(found)
				{
					this.randomAccessFile = new RandomAccessFile(folderIncludingBackupPath + File.separator + fileName,"rw");
					fileVersion.SmartPath = smartPath;
					fileVersion.Version = version;
					fileVersion.Backupid = this.backup.getBackupid();
					fileVersion.BackupItemType = GetType(fileName);
					fileVersion.IsDeltas = GetIsDeltas(fileName);
					fileVersion.IsPermissions = GetIsPermissions(fileName);
					return fileVersion;
				}
				else
				{
					fileVersion.Version = -1;
					return fileVersion;
				}	
			}
			else
			{
				fileVersion.Version = -1;
				return fileVersion;
			}
		}
		else
		{
			fileVersion.Version = -1;
			return fileVersion;
		}
			
	}
	
	public FileVersion SearchPreviousVersion(SmartPath smartPath, int version) throws IOException
	{
		// gets the previous version (the highest of all smaller versions) (for cdp) of a file in the current backup opened by this backup file handler
		FileVersion fileVersion = new FileVersion();
		String folder = smartPath.EncodedPath.substring(0, smartPath.EncodedPath.lastIndexOf(File.separator));
		String folderIncludingBackupPath = this.backupPath + folder;
		File dir = new File(folderIncludingBackupPath);
		String fileName = smartPath.EncodedPath.substring(smartPath.EncodedPath.lastIndexOf(File.separator) + 1);
		File[] files = dir.listFiles(new WildCardFileFilter("??0???????", fileName));
		if(files != null)
		{
			if(files.length > 0)
			{
				int foundVersion = 0;
				boolean found = false;
				for(File file: files)
				{
					int cDPVersion = GetVersion(file.getName());
					if(cDPVersion < version && cDPVersion >= foundVersion) 
					{
						foundVersion = cDPVersion;
						fileName = file.getName();
						found = true;
					}
				}
				if(found)
				{
					this.randomAccessFile = new RandomAccessFile(folderIncludingBackupPath + File.separator + fileName,"rw");
					fileVersion.SmartPath = smartPath;
					fileVersion.Version = foundVersion;
					fileVersion.Backupid = this.backup.getBackupid();
					fileVersion.BackupItemType = GetType(fileName);
					fileVersion.IsDeltas = GetIsDeltas(fileName);
					fileVersion.IsPermissions = GetIsPermissions(fileName);
					return fileVersion;
				}
				else
				{
					fileVersion.Version = -1;
					return fileVersion;
				}	
			}
			else
			{
				fileVersion.Version = -1;
				return fileVersion;
			}
		}
		else
		{
			fileVersion.Version = -1;
			return fileVersion;
		}
	}
	

	public FileOutputStream getFileOutputStream()
	{
		return this.fileOutputStream;        

	}
	public RandomAccessFile getRandomAccessFile()
	{
		return this.randomAccessFile;   

	}
	public FileInputStream getFileInputStream()
	{
		return this.fileInputStream;   

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
	
	public String[] ListPath(String path)
	{
		IOHelper iOHelper = new IOHelper();
		path = iOHelper.translateAbstractPath(iOHelper.makePathAbstract(path));
		File tempFile = new File(this.backupPath + path);
		return tempFile.list();
	}
	
	public SmartPath FindEncodedPath(String path)
	{
		IOHelper iOHelper = new IOHelper();
		path = iOHelper.translateAbstractPath(iOHelper.makePathAbstract(path));
		boolean leaveSeparator = false;
		if(path.lastIndexOf(File.separator) == path.length() -1)
		{
			leaveSeparator = true;
		}
		List<String> splittedPath = SplitPath(path);
		String encodedPath = "";
		for(String name : splittedPath)
		{
			String encodedName = BackupFileHandler.LookupFilenameInNames(name,this.backupPath + encodedPath);
			if(encodedName == null) return null;
			encodedPath += encodedName + File.separator; 
		}
		if(encodedPath.lastIndexOf(File.separator) > -1 && !leaveSeparator)
		{
			encodedPath = encodedPath.substring(0,encodedPath.lastIndexOf(File.separator));
		}
		
		return new SmartPath(encodedPath,path, this.backup.getBackupid());
	}
	
	public SmartPath CreateEncodedPath(String path)
	{
		IOHelper iOHelper = new IOHelper();
		path = iOHelper.translateAbstractPath(iOHelper.makePathAbstract(path));
		boolean leaveSeparator = false;
		if(path.lastIndexOf(File.separator) == path.length() -1)
		{
			leaveSeparator = true;
		}
		List<String> splittedPath = SplitPath(path);
		String encodedPath = "";
		for(String name : splittedPath)
		{
			String encodedName = BackupFileHandler.LookupFilenameInNames(name, this.backupPath + encodedPath);
			if(encodedName == null)
			{
				encodedName = BackupFileHandler.CreateFilenameInNames(name, this.backupPath + encodedPath);
			}
			encodedPath += encodedName + File.separator; 
		}
		if(encodedPath.lastIndexOf(File.separator) > -1 && !leaveSeparator)
		{
			encodedPath = encodedPath.substring(0,encodedPath.lastIndexOf(File.separator));
		}
		
		return new SmartPath(encodedPath,path,this.backup.getBackupid());
	}
	
	public static synchronized String LookupFilenameInNames(String name, String path)
	{
		String fileName = null;
		try
		{
			FileReader fileReader = new FileReader(path + "&names");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = bufferedReader.readLine();
			while (line != null)
			{
				// name and encodedname are separated by a separatorchar, so do split
				String[] lines = line.split("/");
				if (lines[0].equals(name)) 
				{
					fileName = lines[1];
					break;
				}
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		}
		catch(Exception ex)
		{}
		return fileName;
	}
	
	public static synchronized String LookupFilenameInNamesReverse(String name, String path)
	{
		String fileName = null;
		try
		{
			FileReader fileReader = new FileReader(path + "&names");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = bufferedReader.readLine();
			while (line != null)
			{
				// name and encodedname are separated by a separatorchar, so do split
				String[] lines = line.split("/");
				if (lines[1].equals(name)) 
				{
					fileName = lines[0];
					break;
				}
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		}
		catch(Exception ex)
		{}
		return fileName;
	}
	public static synchronized String CreateFilenameInNames(String name, String path)
	{
		String fileName = null;
		try
		{
			IOHelper iOHelper = new IOHelper();
			iOHelper.createFoldersInPath(path);
			FileWriter fileWriter = new FileWriter(path + "&names", true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			fileName = UUID.randomUUID().toString();
			bufferedWriter.write(name + "/" + fileName);
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch(Exception ex)
		{}
		return fileName;
	}
	
	private List<String> SplitPath(String path)
	{
		List<String> splittedPath = new ArrayList<String>();
		int oldPosition = -1;
		int position = path.indexOf(File.separator);
		String part;
		while(position > 0)
		{
			part = path.substring(oldPosition+1,position);
			if(part.length() > 0) splittedPath.add(part);
			oldPosition = position;
			position = path.indexOf(File.separator,position + 1);
		}
		if(path.length() > oldPosition + 1)
		{
			part = path.substring(oldPosition+1);
			splittedPath.add(part);
		}
		return splittedPath;
	}
	
	public List<Backupitem> GetFilesAndFolders(SmartPath smartPath)
	{
		List<Backupitem> backupitemList = new ArrayList<Backupitem>();
		if(this.backupPath != null)
		{
			IOHelper iOHelper = new IOHelper();
			String path = iOHelper.checkFolderForSeperatorEnd(smartPath.EncodedPath);
			File file = new File(this.backupPath + path);
			File[] list = file.listFiles();
			if(list != null)
			{
				for(File item:list)
				{
					String name = item.getName();
					if(!item.isDirectory())
					{
						if(!GetIsPermissions(name))
						{
							Backupitem backupitem = new Backupitem();
							backupitem.setBackupitemid(0);
							backupitem.setBackupid(this.backup.getBackupid());
							backupitem.setPath(smartPath.DecodedPath + BackupFileHandler.LookupFilenameInNamesReverse(StripInfo(name),this.backupPath + path));
							backupitem.setType((short)GetType(name).type);
							backupitem.setIsdeltas(GetIsDeltas(name));
							backupitem.setIsfolder(false);
							backupitem.setVersion(GetVersion(name));
							//TODO: check on linux if this gives the date that the file was created
							backupitem.setDatetime(this.backup.getDatetime());
							backupitemList.add(backupitem);
						}	
					}
					else
					{
						Backupitem backupitem = new Backupitem();
						backupitem.setBackupitemid(0);
						backupitem.setBackupid(this.backup.getBackupid());
						backupitem.setPath(smartPath.DecodedPath + BackupFileHandler.LookupFilenameInNamesReverse(name,this.backupPath + path) + File.separator);
						backupitem.setIsfolder(true);
						backupitemList.add(backupitem);
					}
					
				}
			}
			iOHelper = null;
		}
		return backupitemList;
	}
	
	public List<Backupitem> GetFilesAndFoldersRecursive() throws Exception
	{
		return GetFilesAndFoldersRecursive(new SmartPath("","",this.backup.getBackupid()),null);
	}
	
	private String StripInfo(String path)
	{
		return path.substring(10);
	}
	
	private String AddInfo(String fileName, Constants.BackupItemType type, boolean isDeltas, boolean isPermissions, int version)
	{
		String nameStart = String.valueOf(type.type);
		
		if(isDeltas)
			nameStart += "1";
		else
			nameStart += "0";
		
		if(isPermissions)
			nameStart += "1";
		else
			nameStart += "0";
		
		nameStart += String.format("%07d", version);
		return nameStart + fileName;
	}
	
	private int GetVersion(String fileName)
	{
		return Integer.parseInt(fileName.substring(3,10));
	}
	
	private BackupItemType GetType(String fileName)
	{
		return BackupItemType.values()[Short.parseShort(fileName.substring(0,1)) -1];
	}
	
	private boolean GetIsDeltas(String fileName)
	{
		if(fileName.substring(1,2).equals("1"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean GetIsPermissions(String fileName)
	{
		if(fileName.substring(2,3).equals("1"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public List<Backupitem> GetFilesAndFoldersRecursive(SmartPath smartPath, List<Backupitem> backupitemList) throws Exception
	{
		try
		{
			IOHelper iOHelper = new IOHelper();
			String path = iOHelper.checkFolderForSeperatorEnd(smartPath.EncodedPath);
			iOHelper = null;
			if(backupitemList == null)
			{
				backupitemList = new ArrayList<Backupitem>();
			}
			File file = new File(this.backupPath + path);
			File[] list = file.listFiles();
			file = null;
			if(list != null)
			{
				for(File item:list)
				{
					String name = item.getName();
					if(!name.equals("&names"))
					{
						if(!item.isDirectory())
						{
							if(!GetIsPermissions(name))
							{
								Backupitem backupitem = new Backupitem();
								backupitem.setBackupitemid(0);
								backupitem.setBackupid(this.backup.getBackupid());
								backupitem.setPath(smartPath.DecodedPath + BackupFileHandler.LookupFilenameInNamesReverse(StripInfo(name),this.backupPath + path));
								backupitem.setType((short)GetType(name).type);
								backupitem.setIsdeltas(GetIsDeltas(name));
								backupitem.setIsfolder(false);
								backupitem.setVersion(GetVersion(name));
								backupitem.setDatetime(this.backup.getDatetime());
								backupitemList.add(backupitem);
							}	
						}
						else
						{
							Backupitem backupitem = new Backupitem();
							backupitem.setBackupitemid(0);
							backupitem.setBackupid(this.backup.getBackupid());
							String decodedName = BackupFileHandler.LookupFilenameInNamesReverse(name,this.backupPath + path);
							backupitem.setPath(smartPath.DecodedPath + decodedName + File.separator);
							backupitem.setIsfolder(true);
							backupitemList.add(backupitem);
							SmartPath newSmartPath = new SmartPath(smartPath.EncodedPath + name + file.separator,smartPath.DecodedPath + decodedName + File.separator,this.backup.getBackupid());
							GetFilesAndFoldersRecursive(newSmartPath,backupitemList);
							
						}
					}
					
				}
			}

			return backupitemList;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public boolean IsLatestVersion(SmartPath smartPath) throws Exception
	{
		try
		{
			FileVersion fileVersion = this.SearchLatestVersion(smartPath);
			return (fileVersion.Version == smartPath.Version);
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	
}

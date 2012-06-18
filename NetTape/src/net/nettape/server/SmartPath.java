package net.nettape.server;

public class SmartPath {
	public String EncodedPath;
	public String DecodedPath;
	public Integer BackupId;
	public Integer Version;
	public SmartPath(String encodedPath, String decodedPath, Integer backupId)
	{
		EncodedPath = encodedPath;
		DecodedPath = decodedPath;
		BackupId = backupId;
	}
}

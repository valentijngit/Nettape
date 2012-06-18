package net.nettape.client;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.*;
import java.util.*;

import net.nettape.object.Permissions;

public class FilePermissions {
	/*
	private static DosFileAttributes GetDosFileAttributes(java.nio.file.Path path) throws Exception
	{
		DosFileAttributes attributes = java.nio.file.attribute.Attributes.readDosFileAttributes(path, (LinkOption)null);
		return attributes;
	}
	private static void SetDosFileAttributes(java.nio.file.Path path) throws Exception
	{
		java.nio.file.attribute.Attributes.setDosFileAttributes(path, (LinkOption)null);
		return attributes;
	}
	*/
	private static PosixFileAttributes GetPosixFileAttributes(java.nio.file.Path path) throws Exception
	{
		PosixFileAttributes attributes = Files.getFileAttributeView(path, PosixFileAttributeView.class,(LinkOption)null).readAttributes();
		return attributes;
	}
	private static void SetPosixFileAttributes(java.nio.file.Path path, Permissions permissions) throws Exception
	{
		Files.setPosixFilePermissions(path, permissions.posixFileAttributes.permissions());
	}
	private static List<AclEntry> GetAcl(java.nio.file.Path path) throws Exception
	{
		List<AclEntry> attributes = Files.getFileAttributeView(path, AclFileAttributeView.class).getAcl();
		return attributes;
	}
	private static void SetAcl(java.nio.file.Path path, Permissions permissions) throws Exception
	{
		Files.getFileAttributeView(path, AclFileAttributeView.class).setAcl(permissions.aclList);
	}
	public static Boolean GetPermissions(String fileName, Permissions permissions)
	{
		java.nio.file.Path path = FileSystems.getDefault().getPath(fileName);
		Boolean ok = false;
		try
		{
			PosixFileAttributes posixFileAttributes = GetPosixFileAttributes(path);
			permissions.posixFileAttributes = posixFileAttributes;
			ok = true;
		}
		catch(Exception ex)
		{
			
		}
		try
		{
			List<AclEntry> aclList = GetAcl(path);
			permissions.aclList = aclList;
			ok = true;
		}
		catch(Exception ex)
		{
			
		}
		return ok;
	}
	public static Boolean SetPermissions(String fileName, Permissions permissions)
	{
		java.nio.file.Path path = FileSystems.getDefault().getPath(fileName);
		Boolean ok = false;
		try
		{
			SetPosixFileAttributes(path,permissions);
			ok = true;
		}
		catch(Exception ex)
		{
			
		}
		try
		{
			SetAcl(path,permissions);
			ok = true;
		}
		catch(Exception ex)
		{
			
		}
		
		return ok;
	}
}

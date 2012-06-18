package net.nettape.object;

import java.io.*;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

import net.nettape.dal.object.*;
import java.util.ArrayList;
import java.util.List;

public class Permissions implements Serializable
{
	public DosFileAttributes dosFileAttributes;
	public List<AclEntry> aclList;
	public PosixFileAttributes posixFileAttributes;
}


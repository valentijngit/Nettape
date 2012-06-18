package net.nettape;

import java.util.*;

import net.nettape.dal.object.Backupset;

import javassist.bytecode.stackmap.TypeData.ClassName;

public class Config {
	public static String getProperty(String key)
	{
		try
		{ 
			Properties configFile = new Properties();
			configFile.load(Config.class.getResourceAsStream("/nettape.config"));
			return configFile.getProperty(key);			
		}
		catch(Exception ex) {
			return null;
		}
		
	}
	
}

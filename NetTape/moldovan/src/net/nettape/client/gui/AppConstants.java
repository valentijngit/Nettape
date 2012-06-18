package net.nettape.client.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import net.nettape.client.gui.admin.INIFile;

import org.apache.log4j.Logger;


public class AppConstants {

	private static final Logger logger = Logger.getLogger(AppConstants.class);

	public static final String APP_DIR = System.getProperty("user.dir");

	public static final String LNG_DIR = APP_DIR + File.separator + "config"
			+ File.separator + "language" + File.separator;

	public static final String SETTINGS_DIR = APP_DIR + File.separator
			+ "config" + File.separator;

	public static final String DEFAULT_LANGUAGE = "ENGLISH(default)";

	private static TreeMap<String, TreeMap<String, String>> language = null;

	private static TreeMap<String, TreeMap<String, String>> settings = null;

	private static AppConstants instance = null;

	public static final int BUTTON_WIDTH = 85;

	public static ArrayList<String> arrayListIlegalCharacters = null;

	private AppConstants() {

	}

	public static AppConstants getInstance() {
		try {
			if (instance == null)
				instance = new AppConstants();
			return instance;
		} catch (Exception ex) {
			logger.fatal(ex, ex);
			return null;
		}
	}

	public static void setLanguage(String fileName) {
		try {
			INIFile ini = new INIFile(LNG_DIR + fileName);
			language = ini.getSections();
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public static void setLanguage(TreeMap<String, TreeMap<String, String>> language) {
		AppConstants.language = language;
	}

	public static TreeMap<String, TreeMap<String, String>> getLanguage() {
		return language;
	}

	public static void setSettings(String fileName) {
		try {
			INIFile ini = new INIFile(SETTINGS_DIR + fileName);
			ini.loadFile();
			settings = ini.getSections();
			if (settings == null)
				settings = new TreeMap<String, TreeMap<String, String>>();// if something is wrong
			// in
			// settings.ini file
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public static TreeMap<String, TreeMap<String, String>> getSettings() {
		if (settings == null)
			settings = new TreeMap<String, TreeMap<String, String>>();// if something is wrong in
		// settings.ini
		// file
		return settings;
	}

	/**
	 * @source http://support.grouplogic.com/?p=1607
	 * @return all illegal characters depending on OS
	 */
	public static ArrayList<String> getIlegalCharacters() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			String osName = System.getProperty("os.name");
			if (osName.indexOf("Windows") != -1) {
				result.add("\\");
				result.add("/");
				result.add(":");
//				result.add("*");
				result.add("?");
				result.add("\"");
				result.add(">");
				result.add("<");
				result.add("|");
			} else if (osName.indexOf("MacOS") != -1) {
				result.add(":");
			}
		} catch (Exception e) {
			logger.fatal(e, e);
			result = new ArrayList<String>();
		}
		return result;
	}

}

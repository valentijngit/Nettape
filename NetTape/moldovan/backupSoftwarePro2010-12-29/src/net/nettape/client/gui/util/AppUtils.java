package net.nettape.client.gui.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class AppUtils {

	private static final Logger logger = Logger.getLogger(AppUtils.class);

	public static final String WINDOWS_SF_ALLUSERSDESKTOP = "AllUsersDesktop";
	public static final String WINDOWS_SF_ALLUSERSSTARTMENU = "AllUsersStartMenu";
	public static final String WINDOWS_SF_ALLUSERSPROGRAMS = "AllUsersPrograms";
	public static final String WINDOWS_SF_ALLUSERSSTARTUP = "AllUsersStartup";
	public static final String WINDOWS_SF_DESKTOP = "Desktop";
	public static final String WINDOWS_SF_FAVORITES = "Favorites";
	public static final String WINDOWS_SF_MYDOCUMENT = "MyDocuments";
	public static final String WINDOWS_SF_PROGRAMS = "Programs";
	public static final String WINDOWS_SF_RECENT = "Recent";
	public static final String WINDOWS_SF_SENDTO = "SendTo";
	public static final String WINDOWS_SF_STARTMENU = "StartMenu";
	public static final String WINDOWS_SF_STARTUP = "Startup";

	private AppUtils() {}

	public static File getSpecialFoldeFileWindows(String folder) {
		File result = null;
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
					+ "wscript.echo WshShell.SpecialFolders(\""
					+ folder
					+ "\")\n" + "Set WSHShell = Nothing\n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo "
					+ file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
			String fileStr = input.readLine();
			input.close();
			result = new File(fileStr);
		} catch (Exception e) {
			logger.fatal(e, e);
			result = null;
		}
		return result;
	}

	public static Boolean isWindowsOS() {
		return System.getProperty("os.name").indexOf("Windows") != -1;
	}

	public static Boolean isMacOS() {
		return System.getProperty("os.name").indexOf("Mac") != -1;
	}

}

package net.nettape.client.gui;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.TreeMap;

import net.nettape.client.Global;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.admin.LoggerSetings;
import net.nettape.client.gui.ui.MainWindow;
import net.nettape.client.gui.ui.SplashWindow;
import net.nettape.client.gui.ui.UserProfileWindow;
import net.nettape.client.gui.ui.LoginWindow;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.nettape.client.command.LoginCommand;
import net.nettape.connection.Connection;
import java.io.*;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 10, 2009
 * @description
 */

	public class Main {

	public static LoggerSetings logs = LoggerSetings.getInstance();

	public static final Logger logger = Logger.getLogger(Main.class);

	protected static Main instance;

	private static boolean applicationFirstRun = false;

	private Connection connection;

	public Main() {
		try {
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {			
				public void uncaughtException(Thread t, Throwable exc) {
					logger.fatal("WARNING! An uncaught exception has occured...",
							exc);
					try {
						throw exc;
					} catch (Throwable ex) {
						ex.printStackTrace();
						logger.fatal("WARNING! The uncaught exception cannot be throwed further away...",
								ex);
						logger.fatal("WARNING! Most probably the application will be forced to close now. We are sorry for the inconvenience.",
								ex);
					}
				}
			});
		} catch (Exception exc) {
			logger.fatal(exc, exc);
		}
	}

	public void open() {
		SplashWindow splash = null;
		try {

			splash = new SplashWindow();
			splash.open();
			Language.getInstance();
			AppConstants.getInstance();
			AppConstants.setSettings("settings.ini");
			TreeMap<String, String> p = AppConstants.getSettings().get("SETTINGS");
			if (p != null) {
				String str = p.get("language");
				Language.getInstance().setLanguage(str);
			}
			AppImages.getInstance();
			
	
			// TODO set the applicationFirstRun
			TreeMap<String, String> tm = AppConstants.getSettings().get("USER");
			if(tm != null)
			{
				applicationFirstRun = false;
			}
			p = AppConstants.getSettings().get("LOGIN");
			if(p!= null)
			{
				Global.ClientGUID = p.get("ClientGUID");
			}

			if (applicationFirstRun) {
				UserProfileWindow dlg = new UserProfileWindow();
				dlg.open();
				if (dlg.getExitChoiceAction() == SWT.OK) {
					connection = dlg.connection;
				}
			} else {
				if (p != null) 
				{
					LoginWindow loginwindow = null;
					if(p.get("Password") != null)
					{
						loginwindow =  new LoginWindow(true);
					}
					else
					{
						loginwindow = new LoginWindow(false);
					}
						

					loginwindow.open();
					if (loginwindow.getExitChoiceAction() == SWT.OK) {
						connection = loginwindow.connection;
					}
				
				}
				else
				{
					UserProfileWindow dlg = new UserProfileWindow();
					dlg.open();
					if (dlg.getExitChoiceAction() == SWT.OK) {
						connection = dlg.connection;
					}
				}

				
			}
			/*
			Bridge.init(); 
		    Bridge.LoadAndRegisterAssembly("NettapeShadow.j4n.dll");
		    connection.shadowCopy = new ShadowCopy();
			connection.shadowCopy.StartVolume("c:\\");
			connection.shadowCopy.CopyFile("c:\\test\\NettapeShadow.dll", "d:\\");
			connection.shadowCopy.StopVolume();
			*/
/*
			Process prcs = Runtime.getRuntime().exec("D:\\Mijn documenten\\workspace\\NetTape\\required\\shadowcopyconsole\\shadowcopy");
			InputStream cmd_output = prcs.getInputStream();
			OutputStream cmd_input = prcs.getOutputStream();
			
			PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(cmd_input)));
			pw.println("start");
			pw.flush();
			pw.println("c:\\");
			pw.flush();
			pw.println("copy");
			pw.flush();
			pw.println("c:\\test\\nettapeshadow.dll");
			pw.flush();
			pw.println("d:\\");
			pw.flush();
			pw.println("stop");
			pw.flush();
	*/		
			if(!(connection == null) && connection.IsLoggedIn()) 
			{
				Global.connection = connection;
				splash.close();
				new MainWindow().open();
		}


		} catch (Throwable exc) {
			exc.printStackTrace();
			logger.fatal(exc, exc);
		} finally {
			logs.shutDown();
			if (splash != null)
				splash.close();
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();// just for initialization of display (static
			// part)...possible not needed...must check
			Main.instance = new Main();
			Main.instance.open();
		} catch (Throwable e) {
			logger.fatal(e, e);
		}
	}

	public static Main getInstance() {
		return Main.instance;
	}
}

package net.nettape.client.gui;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.TreeMap;

import net.nettape.client.gui.admin.*;
import net.nettape.client.gui.ui.MainWindow;
import net.nettape.client.gui.ui.SplashWindow;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;

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

	public Main() {
		try {
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				public void uncaughtException(Thread t, Throwable exc) {
					Main.logger.fatal("WARNING! An uncaught exception has occured...", exc);
					try {
						throw exc;
					} catch (Throwable ex) {
						ex.printStackTrace();
						Main.logger.fatal("WARNING! The uncaught exception cannot be throwed further away...",
								ex);
						Main.logger.fatal("WARNING! Most probably the application will be forced to close now. We are sorry for the inconvenience.",
								ex);
					}
				}
			});
		} catch (Exception exc) {
			Main.logger.fatal(exc, exc);
		}
	}

	public void open() {
		SplashWindow splash = null;
		try {

			splash = new SplashWindow();
			splash.open();
			Language.getInstance();
			AppConstants.getInstance();
			// AppConstants.setSettings("settings.ini");
			TreeMap<String, String> p = AppConstants.getSettings().get("SETTINGS");
			if (p != null) {
				String str = p.get("language");
				Language.getInstance().setLanguage(str);
			}
			AppImages.getInstance();

			splash.close();

			// TODO set the applicationFirstRun

			if (Main.applicationFirstRun) {} else {
				new MainWindow().open();
			}

		} catch (Throwable exc) {
			exc.printStackTrace();
			Main.logger.fatal(exc, exc);
		} finally {
			Main.logs.shutDown();
			if (splash != null) {
				splash.close();
			}
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
			Main.logger.fatal(e, e);
		}
	}

	public static Main getInstance() {
		return Main.instance;
	}
}

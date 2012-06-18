package net.nettape.client.gui.admin;

import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerSetings {

	private static final Logger logger = Logger.getLogger(LoggerSetings.class);

	private static LoggerSetings instance;
	
	private final Boolean logOnFile = Boolean.TRUE;

	private LoggerSetings() {
		try {
			this.configureLogs();
			logger.info("START APPLICATION - "
					+ Calendar.getInstance().getTime().toString());
		} catch (Exception e) {
			logger.fatal(e, e);
			BasicConfigurator.configure();
		}
	}

	public static LoggerSetings getInstance() {
		try {
			if (instance == null)
				instance = new LoggerSetings();
			return instance;
		} catch (Exception ex) {
			logger.fatal(ex, ex);
			return null;
		}
	}

	private void configureLogs() {
		try {
			Properties properties = new Properties();
			properties.put("log4j.rootLogger", "DEBUG, myConsole "+(this.logOnFile ? ", myLogFile" : ""));
			properties.put("log4j.appender.myConsole",
					"org.apache.log4j.ConsoleAppender");
			properties.put("log4j.appender.myConsole.layout",
					"org.apache.log4j.PatternLayout");
			properties.put("log4j.appender.myConsole.layout.ConversionPattern",
					"%-5p [%d{HH:mm:ss,SSS}] [%l] - %m%n");
			if (this.logOnFile){
			properties.put("log4j.appender.myLogFile",
					"org.apache.log4j.RollingFileAppender");
			properties.put("log4j.appender.myLogFile.File", "logs/logs.txt");
			properties.put("log4j.appender.myLogFile.MaxFileSize", "10240KB");
			properties.put("log4j.appender.myLogFile.MaxBackupIndex", "25");
			properties.put("log4j.appender.myLogFile.layout",
					"org.apache.log4j.PatternLayout");
			properties.put("log4j.appender.myLogFile.layout.ConversionPattern",
					"%-5p [%d{HH:mm:ss,SSS}] [%l] - %m%n");
			properties.put("log4j.appender.myLogFile.threshold", "DEBUG");
			}

			BasicConfigurator.resetConfiguration();
			PropertyConfigurator.configure(properties);
		} catch (Exception e) {
			e.printStackTrace();
			BasicConfigurator.configure();
		}
	}

	public void shutDown() {
		logger.info("STOP APPLICATION - "
				+ Calendar.getInstance().getTime().toString());
	}


}
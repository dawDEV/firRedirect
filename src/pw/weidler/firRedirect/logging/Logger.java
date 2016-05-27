package pw.weidler.firRedirect.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import pw.weidler.firRedirect.ConfigReader;

public class Logger {

	private static Logger instance = null;
	private PrintWriter logWriter = null;
	private LogLevel configuredLevel = null;
	
	public enum LogLevel{
		ERROR, INFO, DEBUG
	}

	private Logger() {
		File logFile = new File("log.txt");
		try {
			if(!logFile.exists()){
				logFile.createNewFile();
			}
			logWriter = new PrintWriter(new FileOutputStream(logFile, true));
		} catch (Exception e) {
			System.err.println("Log Datei konnte nicht erzeugt werden. Möglicherweise ein Rechteproblem?");
			System.exit(0);
		}
		
		// Read loglevel from config
		configuredLevel = ConfigReader.getInstance().getConfig().getLoglevel();
	}

	public static Logger getInstance() {
		if (instance == null)
			instance = new Logger();
		return instance;
	}
	
	public void log(LogLevel loglevel, String text){
		if(configuredLevel.ordinal() < loglevel.ordinal())
			return;
		String logText = String.format("%s [%s]: %s", (new SimpleDateFormat("d.M.Y H:m:s")).format(new Date()), loglevel.name(), text);
		System.out.println(logText);
		logWriter.println(logText);
		logWriter.flush();
	}
}

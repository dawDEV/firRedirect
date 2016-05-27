package pw.weidler.firRedirect;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import pw.weidler.firRedirect.dto.ConfigDTO;
import pw.weidler.firRedirect.logging.Logger.LogLevel;

public class ConfigReader {
	private static ConfigReader instance = null;
	private ConfigDTO config = null;
	
	private ConfigReader(){
		readConfig();
	}
	
	public static ConfigReader getInstance(){
		if(instance == null){
			instance = new ConfigReader();
		}
		return instance;
	}
	
	public ConfigDTO getConfig(){
		return config;
	}
	
	public void readConfig(){
		File configFile = new File("config.ini");
		if(!configFile.exists()){
			System.err.println("config.ini fehlt!");
		}
		try {
			Wini configIni = new Wini(configFile);
			String firEmergencyIP;
			int firEmergencyPort, waitUntilClose;
			LogLevel loglevel;
			
			String loglevelStr = configIni.get("Log", "loglevel");
			try{
				loglevel = LogLevel.valueOf(loglevelStr);
				config = new ConfigDTO(null, -1, loglevel, -1);
			} catch(IllegalArgumentException e){
				System.err.println("Loglevel der config.ini existiert nicht!");
				System.exit(0);
			}
			
			firEmergencyIP = configIni.get("Server", "firEmergencyIP");
			if(!firEmergencyIP.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
										"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
										"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
										"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")){
				System.err.println("firEmergencyIP in der config.ini ist keine IPv4 Adresse!");
				System.exit(0);
			}
			firEmergencyPort = configIni.get("Server", "firEmergencyPort", int.class);
			if(firEmergencyPort < 1){
				System.err.println("firEmergencyPort in der config.ini fehlt oder darf nicht kleiner 1 sein!");
				System.exit(0);
			}
			waitUntilClose = configIni.get("Etc", "waitUntilClose", int.class);
			if(waitUntilClose < 1){
				System.err.println("waitUntilClose in der config.ini fehlt oder darf nicht kleiner 1 sein!");
				System.exit(0);
			}
			
			config.setFirEmergencyIP(firEmergencyIP);
			config.setFirEmergencyPort(firEmergencyPort);
			config.setWaitUntilClose(waitUntilClose);
		} catch (InvalidFileFormatException e) {
			System.err.println("config.ini nicht im richtigen Format!");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}

package pw.weidler.firRedirect;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pw.weidler.firRedirect.dto.AlarmDTO;
import pw.weidler.firRedirect.dto.AlarmDataDTO;
import pw.weidler.firRedirect.logging.Logger;
import pw.weidler.firRedirect.logging.Logger.LogLevel;

public class Main {
	
	private static Logger logger = Logger.getInstance();
	
	public static void main(String[] args) throws IOException {
		alarmSenden(alarmAuswerten(args));
	}
	
	public static String alarmAuswerten(String[] args){
		StringBuilder sbAufrufparameter = new StringBuilder();

		for (String arg : args) {
			sbAufrufparameter.append(arg + " ");
		}
		String strParams = sbAufrufparameter.toString().trim();
		String params[] = strParams.split("\\|");

		String kodierung = params[0].trim().replace(System.getProperty("line.separator"), "%5Cn");
		String beschreibung = params[1].trim().replace(System.getProperty("line.separator"), "%5Cn");
		String alarmtext = params[2].trim().replace(System.getProperty("line.separator"), "%5Cn");
		AlarmDTO alarm = new AlarmDTO(alarmtext, "ALARM", "firRedirect", System.currentTimeMillis()+"", new AlarmDataDTO(kodierung, alarmtext, beschreibung));
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(alarm);
		
		return json;
	}

	public static void alarmSenden(String alarmData){
		try {
			Socket socket = new Socket(ConfigReader.getInstance().getConfig().getFirEmergencyIP(),
									   ConfigReader.getInstance().getConfig().getFirEmergencyPort());
			logger.log(LogLevel.DEBUG, String.format("Socket zu %s:%d geöffnet!", ConfigReader.getInstance().getConfig().getFirEmergencyIP(),
									   											  ConfigReader.getInstance().getConfig().getFirEmergencyPort()));
			OutputStream os = socket.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			
			bw.write(alarmData);
			bw.flush();
			logger.log(LogLevel.INFO, String.format("Daten an firEmergency übertragen. DATA=%s", alarmData)); 
			
			bw.close();
			os.close();
			socket.close();
			logger.log(LogLevel.DEBUG, "Socket geschlossen");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
	}
}

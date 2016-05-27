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
	
	public static void main(String[] args) throws IOException {
		Logger logger = Logger.getInstance();
		//File f = new File(System.getProperty("user.dir")+dat.getYear()+"-"+dat.getMonth()+"-"+dat.getDay()+"_"+dat.getHours()+"-"+dat.getMinutes()+"-"+dat.getSeconds()+"-"+dat.getTime()+".txt");

		//f.createNewFile();

		//PrintWriter pw = new PrintWriter(f);
		
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
		
		try {
			Socket socket = new Socket(ConfigReader.getInstance().getConfig().getFirEmergencyIP(),
									   ConfigReader.getInstance().getConfig().getFirEmergencyPort());
			logger.log(LogLevel.DEBUG, String.format("Socket zu %s:%d ge�ffnet!", ConfigReader.getInstance().getConfig().getFirEmergencyIP(),
									   											  ConfigReader.getInstance().getConfig().getFirEmergencyPort()));
			OutputStream os = socket.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			
			bw.write(json);
			bw.flush();
			logger.log(LogLevel.INFO, String.format("Daten an firEmergency �bertragen. DATA=%s", json)); 
			
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

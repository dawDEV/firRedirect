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

public class Main {

	public static void main(String[] args) throws IOException {
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

		System.out.println(json);
		//pw.println(json);
		//pw.flush();
		//pw.close();
		
		try {
			Socket socket = new Socket("localhost", 5566);
			OutputStream os = socket.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			
			bw.write(json);
			bw.flush();
			
			bw.close();
			os.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

}

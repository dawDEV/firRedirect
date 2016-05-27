package pw.weidler.firRedirect.dto;

import pw.weidler.firRedirect.logging.Logger.LogLevel;

public class ConfigDTO {
	
	// [Server] Section
	private String firEmergencyIP;
	private int firEmergencyPort;
	
	// [Log] Section
	private LogLevel loglevel;
	
	// [Etc] Section
	private int waitUntilClose;

	public ConfigDTO(String firEmergencyIP, int firEmergencyPort, LogLevel loglevel, int waitUntilClose) {
		super();
		this.firEmergencyIP = firEmergencyIP;
		this.firEmergencyPort = firEmergencyPort;
		this.loglevel = loglevel;
		this.waitUntilClose = waitUntilClose;
	}

	public final String getFirEmergencyIP() {
		return firEmergencyIP;
	}

	public final void setFirEmergencyIP(String firEmergencyIP) {
		this.firEmergencyIP = firEmergencyIP;
	}

	public final int getFirEmergencyPort() {
		return firEmergencyPort;
	}

	public final void setFirEmergencyPort(int firEmergencyPort) {
		this.firEmergencyPort = firEmergencyPort;
	}

	public final LogLevel getLoglevel() {
		return loglevel;
	}

	public final void setLoglevel(LogLevel loglevel) {
		this.loglevel = loglevel;
	}

	public final int getWaitUntilClose() {
		return waitUntilClose;
	}

	public final void setWaitUntilClose(int waitUntilClose) {
		this.waitUntilClose = waitUntilClose;
	}
}

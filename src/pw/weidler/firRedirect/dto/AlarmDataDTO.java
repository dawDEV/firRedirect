package pw.weidler.firRedirect.dto;

public class AlarmDataDTO{
	private String address;
	private String alarmtext;
	private String beschreibung;

	public AlarmDataDTO(String address, String alarmtext, String beschreibung) {
		super();
		this.address = address;
		this.alarmtext = alarmtext;
		this.beschreibung = beschreibung;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAlarmtext() {
		return alarmtext;
	}

	public void setAlarmtext(String alarmtext) {
		this.alarmtext = alarmtext;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
}

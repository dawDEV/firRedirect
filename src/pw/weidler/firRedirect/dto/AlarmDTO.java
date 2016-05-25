package pw.weidler.firRedirect.dto;

public class AlarmDTO {
	private String message;
	private String type;
	private String sender;

	private AlarmDataDTO data;
	private String timestamp;

	public AlarmDTO(String message, String type, String sender, String timestamp, AlarmDataDTO data) {
		super();
		this.message = message;
		this.type = type;
		this.sender = sender;
		this.timestamp = timestamp;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public AlarmDataDTO getData() {
		return data;
	}

	public void setData(AlarmDataDTO data) {
		this.data = data;
	}

}

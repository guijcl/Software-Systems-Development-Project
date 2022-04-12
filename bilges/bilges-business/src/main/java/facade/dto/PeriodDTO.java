package facade.dto;

import java.io.Serializable;

public class PeriodDTO implements Serializable {

	private static final long serialVersionUID = -4087131153704256744L;

	private final int id;
	private String date;
	private String startTime;
	private String endTime;

	public PeriodDTO(int id, String date, String startTime, String endTime) {
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
}
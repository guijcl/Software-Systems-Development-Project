package facade.dto;

import java.io.Serializable;

public class TicketDTO implements Serializable {

	private static final long serialVersionUID = -4087131153704256744L;
	
	private final int id;
	private final String eventName;
	private double price;
	private String date;

	public TicketDTO(int id, String eventName, String d, double p) {
		this.id = id;
		this.eventName = eventName;
		this.date = d;
		this.price = p;
	}
	
	public int getId() {
		return id;
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getDate() {
		return date;
	}
	
}
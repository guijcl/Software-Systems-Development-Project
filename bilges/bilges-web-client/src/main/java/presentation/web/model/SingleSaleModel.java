package presentation.web.model;

import java.util.ArrayList;
import java.util.LinkedList;

import facade.dto.PeriodDTO;
import facade.dto.SeatDTO;
import facade.exceptions.ApplicationException;
import facade.services.IClientServiceRemote;

public class SingleSaleModel extends Model {
	
	private String eventName;
	private String dates;
	private String price;
	private String email;
	private String seatsStr;
	
	private Iterable<SeatDTO> seats;
	private Iterable<PeriodDTO> periods;
	
	private IClientServiceRemote clientService;
	
	public void setClientService(IClientServiceRemote clientService) {
		this.clientService = clientService;
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getDates() {
		return dates;
	}
	
	public void setDates(String dates) {
		this.dates = dates;
	}
	
	public Iterable<SeatDTO> getSeats() {
		return seats;
	}
	
	public void setSeats(Iterable<SeatDTO> seats) {
		this.seats = seats;
	}
	
	public String getSeatsStr() {
		return seatsStr;
	}

	public void setSeatsStr(String seatsStr) {
		this.seatsStr = seatsStr;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void clearFields() {
		eventName = email = dates = price = "";
		seats = new ArrayList<>();
	}
	
	public Iterable<PeriodDTO> getPeriods() {
		try {
			periods = clientService.startBuySingleTickets(eventName);
			return periods;
		} catch (ApplicationException e) {
			return new LinkedList<>();
		}
	}
	
	public Iterable<SeatDTO> getClientSeats() {
		try {
			seats = clientService.chooseSingleDate(dates);
			return seats;
		} catch (ApplicationException e) {
			//System.out.println("ERRO");
			return new LinkedList<>();
		}
	}
	
}

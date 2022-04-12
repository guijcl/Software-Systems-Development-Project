package presentation.web.model;

import facade.exceptions.ApplicationException;
import facade.services.IClientServiceRemote;

public class PassSaleModel extends Model {
	
	private String eventName;
	private String quantity;
	private String email;
	private String price;
	
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getAvailablePasses() {
		try {
			quantity = String.valueOf(clientService.buyPassTickets(eventName));
			return quantity;
		} catch (ApplicationException e) {
			return "";
		}
	}

}

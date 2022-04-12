package business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import facade.dto.TicketDTO;
import facade.exceptions.ApplicationException;

@Stateless
public class TicketSaleHandler {
	
	@EJB
	private EventCatalog eventCatalog;
	
	@EJB
	private TicketCatalog ticketCatalog;
	
	@EJB
	private SeatCatalog seatCatalog;
	
	private Event event;
	
	private String seats;
	private String email;
	private String date;
	
	private List<Single> requestedTickets = new ArrayList<>();	
	
	private int quantity;
	
	public List<Period> startBuySingleTickets(String eventName) throws ApplicationException {
		try {
			this.event = eventCatalog.getEvent(eventName);
			
			if(!event.checkSeatingForEvent("seated"))
				throw new ApplicationException("Event does not have single seats");
			List<Period> dates = event.getAvailableDatesEvent(); 
			return dates;
		} catch (Exception e) {
			throw new ApplicationException("Error fetching Dates ", e);
		}
	}
	
	public List<Seat> chooseSingleDate(String strTicketDate) throws ApplicationException {
		try {
			this.date = strTicketDate;
			if(!event.checkIfEventOccursOnDate(strTicketDate))
				throw new ApplicationException("Event does not occur at this Date");
			List<Seat> availableSeats = seatCatalog.getAvailableSeats(false, event, event.getPeriodByDate(this.date));
			return availableSeats;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Error fetching Dates ", e);
		}
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public List<Single> chooseSingleSeats(String strTicketSeat, String strUserMail) throws ApplicationException {
		try {
			this.seats = strTicketSeat;
			this.email = strUserMail;
			
			Period p = event.getPeriodByDate(date);
			requestedTickets = event.getRequestedTickets(p, seats);
			
			ticketCatalog.updateBookedSingles(requestedTickets);
			
			return requestedTickets;
		} catch (Exception e) {
			throw new ApplicationException("Error fetching Seats ", e);
		}
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public List<Single> buySingleTickets() throws ApplicationException {
		try {						
			if(requestedTickets.isEmpty())
				throw new ApplicationException("Ticket already sold");
			
			ticketCatalog.updateSoldSingles(requestedTickets);
			
			System.out.println("SingleSale done");
			return requestedTickets;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Error buying Single Ticket(s) ", e);
		}
	}
	
	
	public int buyPassTickets(String eventName) throws ApplicationException {
		try {
			event = eventCatalog.getEvent(eventName);
			List<Pass> availablePasses = event.availablePasses();
			if(availablePasses.isEmpty())
				throw new ApplicationException("Event does not sell Pass tickets");
			this.quantity = availablePasses.size();
			return this.quantity;
		} catch (Exception e) {
			throw new ApplicationException("Error fetching Seats ", e);
		}
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public List<Pass> orderPassTicket(int n, String mail) throws ApplicationException {
		try {
			List<Pass> availablePasses = event.availablePasses();
			
			if(n > availablePasses.size())
				throw new ApplicationException("Not enough Pass Tickets are available for your request");
			
			ticketCatalog.updatePassTickets(availablePasses, n);
			
			System.out.println("PassSale done");
			return availablePasses;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Error buying Pass Ticket(s) ", e);
		}
	}
	
	public TicketDTO getTicketDetails(int id) throws ApplicationException {
		Ticket ticket = ticketCatalog.getTicketByID(id);
		return new TicketDTO(id, ticket.getEvent().getName(), date, ticket.getPrice());
	}
	
}

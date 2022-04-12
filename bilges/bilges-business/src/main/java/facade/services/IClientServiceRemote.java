package facade.services;

import java.util.Collection;

import javax.ejb.Remote;

import facade.dto.PeriodDTO;
import facade.dto.SeatDTO;
import facade.dto.TicketDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IClientServiceRemote {

	public Collection<PeriodDTO> startBuySingleTickets(String eventName) throws ApplicationException;

	public Collection<SeatDTO> chooseSingleDate(String strTicketDate) throws ApplicationException;

	public Collection<TicketDTO> chooseSingleSeats(String strTicketSeat, String strUserMail)
			throws ApplicationException;

	public Collection<TicketDTO> buySingleTickets() throws ApplicationException;

	public int buyPassTickets(String eventName) throws ApplicationException;

	public Collection<TicketDTO> orderPassTicket(int n, String mail) throws ApplicationException;

}

package application;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.Pass;
import business.Period;
import business.Seat;
import business.Single;
import business.TicketSaleHandler;
import facade.dto.PeriodDTO;
import facade.dto.SeatDTO;
import facade.dto.TicketDTO;
import facade.exceptions.ApplicationException;
import facade.services.IClientServiceRemote;

@Stateless
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class ClientService implements IClientServiceRemote {

	@EJB
	private TicketSaleHandler saleHandler;
	
	@Override
	public Collection<PeriodDTO> startBuySingleTickets(String eventName) throws ApplicationException {
		List<Period> periods = saleHandler.startBuySingleTickets(eventName);
		List<PeriodDTO> result = new LinkedList<>();
		SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat hours = new SimpleDateFormat("HH:mm");
		for(Period p : periods) {
			result.add(new PeriodDTO(p.getId(), day.format(p.getDate()), hours.format(p.getStartTime()), hours.format(p.getEndTime())));
		}
		return result;
	}
	
	@Override
	public Collection<SeatDTO> chooseSingleDate(String strTicketDate) throws ApplicationException {
		List<Seat> seats = saleHandler.chooseSingleDate(strTicketDate);
		List<SeatDTO> result = new LinkedList<>();
		for(Seat s : seats) {
			result.add(new SeatDTO(s.getId(), s.getRow(), s.getColumn()));
		}
		return result;		
	}
	
	@Override
	public Collection<TicketDTO> chooseSingleSeats(String strTicketSeat, String strUserMail) throws ApplicationException {
		List<Single> singles = saleHandler.chooseSingleSeats(strTicketSeat, strUserMail);
		List<TicketDTO> result = new LinkedList<>();
		SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
		for(Single s : singles) {
			result.add(new TicketDTO(s.getId(), s.getEvent().getName(), day.format(s.getPeriod().getDate()), s.getPrice()));
		}
		return result;	
	}
	
	@Override
	public Collection<TicketDTO> buySingleTickets() throws ApplicationException {
		List<Single> singles = saleHandler.buySingleTickets();
		List<TicketDTO> result = new LinkedList<>();
		SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
		for(Single s : singles) {
			result.add(new TicketDTO(s.getId(), s.getEvent().getName(), day.format(s.getPeriod().getDate()), s.getPrice()));
		}
		return result;
	}
	
	@Override
	public int buyPassTickets(String eventName) throws ApplicationException {
		return saleHandler.buyPassTickets(eventName);
	}
	
	@Override
	public Collection<TicketDTO> orderPassTicket(int n, String mail) throws ApplicationException {
		List<Pass> passes = saleHandler.orderPassTicket(n, mail);
		List<TicketDTO> result = new LinkedList<>();
		for(Pass p : passes) {
			result.add(new TicketDTO(p.getId(), p.getEvent().getName(), "", p.getPrice()));
		}
		return result;
	}
	
}

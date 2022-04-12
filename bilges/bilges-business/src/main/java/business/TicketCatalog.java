package business;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import facade.exceptions.ApplicationException;

@Stateless
public class TicketCatalog {
	
	@PersistenceContext
	private EntityManager em;
	
	public Ticket getTicketByID(int id) throws ApplicationException {
		Ticket ticket = em.find(Ticket.class, id);
		if(ticket == null) throw new ApplicationException("Error ticket not found");
		return ticket;
	}
	
	public List<Ticket> getTickets() throws ApplicationException {
		try {
			TypedQuery<Ticket> query = em.createNamedQuery(Ticket.FIND_ALL, Ticket.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Error obtaining the tickets list", e);
		}
	}
	
	public List<Single> getRequestedTickets(Event e, Period p, Seat s) throws ApplicationException {
		try {
			TypedQuery<Single> query = em.createNamedQuery(Ticket.FIND_REQUESTED_TICKETS, Single.class);
			query.setParameter(Ticket.FIND_BY_EVENT, e);
			query.setParameter(Ticket.FIND_BY_PERIOD, p);
			query.setParameter(Ticket.FIND_BY_SEAT, s);
			return query.getResultList();
		} catch (Exception exception) {
			throw new ApplicationException ("Error obtaining the tickets list", exception);
		}
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Single addSingleTicket(Event e, double p, boolean b, boolean sold, Date d, Seat s, Period pe) {
		Single t = new Single(e, p, b, sold, d, s, pe);
		em.persist(t);
		return t;
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Pass addPassTicket(Event e, double p, boolean b, boolean sold, Date d, List<Single> t) {
		Pass ticket = new Pass(e, p, b, sold, d, t);
		em.merge(ticket);
		em.flush();
		return ticket;
	}
	
	public void updateBookedSingles(List<Single> requestedTickets) {
		for(Single s : requestedTickets) {
			s.setBooked(true);
			em.merge(s);
		}
	}
	
	public void updateSoldSingles(List<Single> requestedTickets) {
		for(Single s : requestedTickets) {
			s.setSold(true);
			em.merge(s);
		}
	}

	public void updatePassTickets(List<Pass> availablePasses, int n) {
		for(int i = 0; i < n; i++) {
			availablePasses.get(i).setBooked(true);
			em.merge(availablePasses.get(i));
            for(Single si : availablePasses.get(i).getDailyTickets()) {
                si.setBooked(true);
                si.setSold(true);
                em.merge(si);
            }
        }
	}

}

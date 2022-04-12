package business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

@Stateless
public class SeatCatalog {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Seat> getSeats() throws ApplicationException {
		try {
			TypedQuery<Seat> query = em.createNamedQuery(Seat.FIND_ALL, Seat.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Error obtaining the seats list", e);
		}
	}
	
	public List<Seat> getAvailableSeats(boolean boo, Event event, Period p) throws ApplicationException {
		try {
			TypedQuery<Seat> query = em.createNamedQuery(Seat.FIND_ALL_AVAILABLE, Seat.class);
			query.setParameter(Ticket.FIND_BY_BOOKED, boo);
			query.setParameter(Ticket.FIND_BY_EVENT, event);
			query.setParameter(Ticket.FIND_BY_PERIOD, p);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Error obtaining the available seats list", e);
		}
	}

}

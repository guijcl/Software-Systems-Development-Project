package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import facade.exceptions.ApplicationException;

@Stateless
public class EventCatalog {

	@PersistenceContext
	private EntityManager em;
	
	public Event getEventByID(int id) throws ApplicationException {
		Event event = em.find(Event.class, id);
		if(event == null) throw new ApplicationException("Error ticket not found");
		return event;
	}
	
	public Event getEvent(String name) throws ApplicationException {
		TypedQuery<Event> query = em.createNamedQuery(Event.FIND_BY_NAME, Event.class);
		query.setParameter(Event.ENAME, name);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException ("Event not found.", e);
		}
	}
	
	public List<Event> getEvents() throws ApplicationException {
		try {
			TypedQuery<Event> query = em.createNamedQuery(Event.FIND_ALL, Event.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new ApplicationException ("Error obtaining the events list", e);
		}
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Event addEvent (Installation i, String n, EType t, Producer p, List<Period> eD, List<Ticket> eT) {
		Event c = new Event(i, n, t, p, eD, eT);
		em.persist(c);
		return c;
	}
	
	public boolean uniqueName(String name) throws ApplicationException {
		List<Event> listEvents = getEvents();
		for(Event e : listEvents)
			if(e.getName().equals(name)) return false;
		return true;
	}
	
	public boolean validHours(List<String> hours, List<Date> startHours, List<Date> endingHours) throws ParseException {
		SimpleDateFormat hour = new SimpleDateFormat("hh:mm");

		for(String str : hours) {
			String[] currentHour = str.split("/");
			Date startH = hour.parse(currentHour[0]);
			Date endingH = hour.parse(currentHour[1]);
			startHours.add(startH);
			endingHours.add(endingH);
			if(startH.after(endingH))
				return true;
		}
		return false;
	}
	
	public boolean checkConsecutiveDays(List<String> dates, SimpleDateFormat day) throws ParseException {
		Date today = day.parse(day.format(new Date()));
		for(int i = 0; i < dates.size(); i++) {
			Date dateDay = day.parse(dates.get(i));
			LocalDate ld = dateDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if(i < dates.size() - 1) {
				Date dateDay2 = day.parse(dates.get(i + 1));
				LocalDate ld2 = dateDay2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if(!ld2.minusDays(1).isEqual(ld))
					return true;
			}
			if(dateDay.before(today))
				return true;
		}
		return false;
	}
	
}

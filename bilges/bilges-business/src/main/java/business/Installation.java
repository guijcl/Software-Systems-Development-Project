package business;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name=Installation.FIND_BY_NAME, query="SELECT i FROM Installation i WHERE i.name = :" + Installation.INAME),
	@NamedQuery(name=Installation.FIND_ALL, query="SELECT i FROM Installation i")
})

public class Installation implements Serializable {
	
	public static final String FIND_BY_NAME = "Installation.findByName";
	public static final String INAME = "Name";
	public static final String FIND_ALL = "Installation.findAll";
	   
	@Id @GeneratedValue private int id;
	@OneToMany(mappedBy = "installation") private List<Event> eventCalendar;
	private String name;
	private int capacity;
	@OneToMany(mappedBy = "installation") private List<Seat> seats;
	private static final long serialVersionUID = 1L;

	public Installation() {
		super();
	} 
	
	public Installation(List<Event> e, String n, int c) {
		this.eventCalendar = e;
		this.name = n;
		this.capacity = c;
	} 
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	
	public boolean checkSeatingType(String t) {
		if(this.seats.isEmpty())
			return t.equals("open");
		return t.equals("individual");
	}
  
	public List<Event> getEventCalendar() {
		return this.eventCalendar;
	}

	public void setEventCalendar(List<Event> eventCalendar) {
		this.eventCalendar = eventCalendar;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	
	public void addEventToCalendar(Event e) {
		this.eventCalendar.add(e);
	}
	
	public boolean checkIfAvailable(List<Period> listPeriods) {
		List<Event> listEvents = this.eventCalendar;
		for(Event event : listEvents) {
			List<Period> eventPeriods = event.getEventDuration();
			for(Period pI : eventPeriods) {
				for(Period pE : listPeriods) {
					if((pI.getDate().equals(pE.getDate())) && 
						(((pI.getStartTime().after(pE.getStartTime()) && pI.getStartTime().before(pE.getEndTime()))
							|| (pI.getEndTime().after(pE.getStartTime()) && pI.getEndTime().before(pE.getEndTime()))) ||
						((pE.getStartTime().after(pI.getStartTime()) && pE.getStartTime().before(pI.getEndTime()))
							|| (pE.getEndTime().after(pI.getStartTime()) && pE.getEndTime().before(pI.getEndTime()))))) {
						return false;
					}
				}
			}
		}
		return true;
	}
   
}

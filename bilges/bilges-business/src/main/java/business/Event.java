package business;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
	@NamedQuery(name=Event.FIND_BY_NAME, query="SELECT e FROM Event e WHERE e.name = :" + Event.ENAME),
	@NamedQuery(name=Event.FIND_ALL, query="SELECT e FROM Event e")
})

public class Event implements Serializable {
	
	public static final String FIND_BY_NAME = "Event.findByName";
	public static final String ENAME = "name";
	public static final String FIND_ALL = "Event.findAll";
	   
	@Id @GeneratedValue private int id;
	@ManyToOne @JoinColumn private Installation installation;
	@OneToOne @JoinColumn private EType type;
	@OneToOne @JoinColumn private Producer producer;
	@OneToMany(fetch = FetchType.EAGER) @JoinColumn private List<Period> eventDuration;
	@OneToMany(mappedBy = "event") private List<Ticket> eventTickets;
	private String name;
	private static final long serialVersionUID = 1L;

	public Event() {
		super();
	}   
	
	public Event(Installation i, String n, EType t, Producer p, List<Period> eD, List<Ticket> eT) {
		this.installation = i;
		this.type = t;
		this.producer = p;
		this.eventDuration = eD;
		this.eventTickets = eT;
		this.name = n;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}  
	
	public Installation getInstallation() {
		return this.installation;
	}

	public void setInstallation(Installation installation) {
		this.installation = installation;
	} 
	
	public EType getType() {
		return this.type;
	}

	public void setType(EType type) {
		this.type = type;
	}  
	
	public Producer getProducer() {
		return this.producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	} 
	
	public List<Period> getEventDuration() {
		return this.eventDuration;
	}

	public void setEventDuration(List<Period> eventDuration) {
		this.eventDuration = eventDuration;
	}
	
	public List<Ticket> getEventTickets() {
		return eventTickets;
	}
	
	public void setEventTickets(List<Ticket> eventTickets) {
		this.eventTickets = eventTickets;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean checkForInstallation() {
		return this.installation != null;
	}
	
	public boolean checkSeatingForEvent(String seating) {
		return this.type.getSeatingType().equals(seating);
	}
	
	public boolean checkIfEventOccursOnDate(String strDate) throws ParseException {
		SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
		Date newDate = day.parse(strDate);
		
		for(Period p : eventDuration) {
			if(p.getDate().equals(newDate))
				return true;
		}
		return false;
	}
	
	public List<Period> getAvailableDatesEvent() {
		List<Period> availableDates = new ArrayList<>();
		for(Ticket t : this.eventTickets) {	
			if(t instanceof Single && !t.getBooked()) {
				Single singleTicket = (Single) t;
				if(!availableDates.contains(singleTicket.getPeriod()))
					availableDates.add(singleTicket.getPeriod());
			}
		}
		return availableDates;
	}
	
	public List<Ticket> getAvailableSingleTickets(Period p) {
		List<Ticket> availableTickets = new ArrayList<>();
		for(Ticket t : this.eventTickets) {	
			if(t instanceof Single && !t.getBooked()) {
				Single singleTicket = (Single) t;
				if(!availableTickets.contains(singleTicket) && singleTicket.getPeriod().equals(p))
					availableTickets.add(singleTicket);
			}
		}
		return availableTickets;
	}
	
	public Period getPeriodByDate(String date) throws ParseException {
		SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
		Date newDate = day.parse(date);
		
		for(Period p : eventDuration) {
			if(p.getDate().equals(newDate))
				return p;
		}
		return null;
	}
	
	public List<Single> getRequestedTickets(Period p, String seats) {
		List<Ticket> ticketList = this.getAvailableSingleTickets(p);
		List<String> seatsList = Arrays.asList(seats.split("/"));
		
		int count = 0;
		List<Single> boughtTickets = new ArrayList<>();
		for(Ticket ticket : ticketList) {
			for(String seat : seatsList) {
				List<String> seatInfo = Arrays.asList(seat.split(" "));
				String row = seatInfo.get(0);
				String column = seatInfo.get(1);
				Single singleTicket = (Single) ticket;
				if(row.equals(singleTicket.getSeat().getRow()) && column.equals(singleTicket.getSeat().getColumn())) {
					boughtTickets.add(singleTicket);
					count++;
				} 
			}
		}
		if(count == seatsList.size())
			return boughtTickets;
		return new ArrayList<>();
	}
	
	public List<Pass> availablePasses() {
		List<Pass> availableTickets = new ArrayList<>();
		
		for(Ticket t : this.getEventTickets()) {
			if(t instanceof Pass && !t.getBooked()) {
				Pass passTicket = (Pass) t;
				boolean checkIfPassIsAvailable = true;
				for(Single si : passTicket.getDailyTickets()) {
					if(si.getBooked())
						checkIfPassIsAvailable = false;
				}
				if(checkIfPassIsAvailable)
					availableTickets.add(passTicket);
			}
		}
		return availableTickets;
	}
   
}

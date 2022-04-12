package business;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Ticket
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@NamedQueries({
	@NamedQuery(name=Ticket.FIND_REQUESTED_TICKETS, query="SELECT t FROM Single t WHERE t.event = :" + Ticket.FIND_BY_EVENT + 
			" AND t.period = :" + Ticket.FIND_BY_PERIOD + " AND t.seat = :" + Ticket.FIND_BY_SEAT),
	@NamedQuery(name=Ticket.FIND_ALL, query="SELECT t FROM Ticket t")
})

public class Ticket implements Serializable {
	
	public static final String FIND_ALL = "Ticket.findAll";
	public static final String FIND_BY_TYPE = "Ticket.findByType";
	public static final String FIND_BY_EVENT = "event";
	
	public static final String FIND_REQUESTED_TICKETS = "Ticket.findRequestTickets";
	public static final String FIND_BY_PERIOD = "period";
	public static final String FIND_BY_SEAT = "seat";
	public static final String FIND_BY_BOOKED = "booked";
	
	@Id @GeneratedValue private int id;
	@ManyToOne private Event event;
	private double price;
	private boolean booked;
	private boolean sold;
	@Temporal(TemporalType.DATE) private Date saleStart;
	private static final long serialVersionUID = 1L;

	public Ticket() {
		super();
	}   
	
	public Ticket(Event e, double p, boolean b, boolean sold, Date s) {
		this.event = e;
		this.price = p;
		this.booked = b;
		this.sold = sold;
		this.saleStart = s;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   

	public Event getEvent() {
		return this.event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}   
	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}   
	public boolean getBooked() {
		return this.booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}   
	public Date getSaleStart() {
		return this.saleStart;
	}

	public void setSaleStart(Date saleStart) {
		this.saleStart = saleStart;
	}
	
	public boolean checkTicketSeatingType(String seating) {
		return this.getEvent().checkSeatingForEvent(seating);
	}

	public boolean isSold() {
		return sold;
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}
}

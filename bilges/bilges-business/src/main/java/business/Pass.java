package business;

import static javax.persistence.CascadeType.ALL;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity

public class Pass extends Ticket {
	
	@OneToMany @JoinColumn
	private List<Single> dailyTickets;
	private static final long serialVersionUID = 1L;

	public Pass() {
		super();
	} 
	
	public Pass(Event e, double p, boolean b, boolean sold, Date d, List<Single> t) {
		super(e, p, b, sold, d);
		this.dailyTickets = t;
	}
 
	public List<Single> getDailyTickets() {
		return this.dailyTickets;
	}

	public void setDailyTickets(List<Single> dailyTickets) {
		this.dailyTickets = dailyTickets;
	}
   
}

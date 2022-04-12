package business;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity

public class Single extends Ticket {
	
	public static final String FIND_AVAILABLE = "Single.findAvailable";
	
	@OneToOne private Seat seat;
	@OneToOne @JoinColumn private Period period;
	private static final long serialVersionUID = 1L;

	public Single() {
		super();
	}
	
	public Single(Event e, double p, boolean b, boolean sold, Date d, Seat s, Period pe) {
		super(e, p, b, sold, d);
		this.seat = s;
		this.period = pe;
	}
	
	public Seat getSeat() {
		return this.seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}
   
}

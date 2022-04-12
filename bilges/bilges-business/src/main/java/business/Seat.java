package business;

import static javax.persistence.CascadeType.MERGE;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
	@NamedQuery(name=Seat.FIND_ALL, query="SELECT s FROM Seat s"),
	@NamedQuery(name=Seat.FIND_ALL_AVAILABLE, query=/**"SELECT DISTINCT s FROM Seat s INNER JOIN Single t "**/"SELECT DISTINCT s FROM Single as t INNER JOIN t.seat as s "
			+ "WHERE t.booked = :" + Ticket.FIND_BY_BOOKED + " AND t.event = :" + Ticket.FIND_BY_EVENT + " AND t.period = :" + Ticket.FIND_BY_PERIOD + " ORDER BY s.rw, s.col")
})

public class Seat implements Serializable {

	public static final String FIND_ALL = "Seat.findAll";
	public static final String FIND_ALL_AVAILABLE = "Seat.findAllAvailable";
	public static final String SID = "id";
	
	@Id @GeneratedValue private int id;
	@ManyToOne(cascade = MERGE) @JoinColumn private Installation installation;
	private String rw;
	private String col;
	private static final long serialVersionUID = 1L;

	public Seat() {
		super();
	} 
	
	public Seat(Installation i, String r, String c) {
		this.installation = i;
		this.rw = r;
		this.col = c;
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
	public String getRow() {
		return this.rw;
	}

	public void setRow(String row) {
		this.rw = row;
	}   
	public String getColumn() {
		return this.col;
	}

	public void setColumn(String column) {
		this.col = column;
	}
   
}

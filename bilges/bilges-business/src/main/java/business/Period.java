package business;

import static javax.persistence.TemporalType.TIME;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity

public class Period implements Serializable {

	   
	@Id @GeneratedValue private int id;
	@Temporal(TemporalType.DATE) private Date pdate;
	@Temporal(TIME) private Date startTime;
	@Temporal(TIME) private Date endTime;
	private static final long serialVersionUID = 1L;

	public Period() {
		super();
	} 
	
	public Period(Date d, Date s, Date e) {
		this.pdate = d;
		this.startTime = s;
		this.endTime = e;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
  
	public Date getDate() {
		return this.pdate;
	}

	public void setDate(Date date) {
		this.pdate = date;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
   
}

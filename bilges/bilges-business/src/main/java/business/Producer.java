package business;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name=Producer.FIND_BY_ID, query="SELECT p FROM Producer p WHERE p.id = :" + Producer.PID),
	@NamedQuery(name=Producer.FIND_ALL, query="SELECT p FROM Producer p")
})

public class Producer implements Serializable {
	public static final String FIND_BY_ID = "Producer.findById";
	public static final String PID = "id";
	public static final String FIND_ALL = "Producer.findAll";
	   
	@Id @GeneratedValue private int id;
	@ManyToMany
	@JoinTable(name = "ALLOWS_TYPES")
	private List<EType> eventTypeLicence;
	private String name;
	private static final long serialVersionUID = 1L;

	public Producer() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public List<EType> getEventTypeLicence() {
		return this.eventTypeLicence;
	}

	public void setEventTypeLicence(List<EType> eventTypeLicence) {
		this.eventTypeLicence = eventTypeLicence;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}

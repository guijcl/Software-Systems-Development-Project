package business;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name=EType.FIND_BY_NAME, query="SELECT t FROM EType t WHERE t.typeName = :" + EType.NAME),
	@NamedQuery(name=EType.FIND_ALL, query="SELECT t FROM EType t")
})

public class EType implements Serializable {
	
	public static final String FIND_ALL = "EType.findAll";
	public static final String FIND_BY_NAME = "EType.findByName";
	public static final String NAME = "typeName";
	   
	@Id @GeneratedValue private int id;
	private String typeName;
	private String seatingType;
	private int maxCap;
	private static final long serialVersionUID = 1L;

	public EType() {
		super();
	}
	
	public EType(String typeName, String seating, int maxCap) {
		this.typeName = typeName;
		this.seatingType = seating;
		this.maxCap = maxCap;
	}  
	
	public int getID() {
		return this.id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	public String getSeatingType() {
		return this.seatingType;
	}

	public void setSeatingType(String seatingType) {
		this.seatingType = seatingType;
	}
	
	public int getmaxCap() {
		return this.maxCap;
	}

	public void setmaxCap(int maxCap) {
		this.maxCap = maxCap;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
   
}

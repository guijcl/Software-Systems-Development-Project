package business;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name=EUser.FIND_BY_NAME, query="SELECT u FROM EUser u WHERE u.name = :" + 
		EUser.UNAME)

public class EUser implements Serializable {
	
	public static final String FIND_BY_NAME = "EUser.findByName";
	public static final String UNAME = "Name";
	   
	@Id @GeneratedValue private int id;
	private String name;
	private static final long serialVersionUID = 1L;

	public EUser() {
		super();
	}
	
	public EUser(String name) {
		this.name = name;
	} 
	
	public int getID() {
		return this.id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}

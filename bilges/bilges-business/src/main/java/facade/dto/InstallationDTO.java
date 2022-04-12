package facade.dto;

import java.io.Serializable;

public class InstallationDTO implements Serializable {
	
	private static final long serialVersionUID = -4087131153704256744L;
	
	private int id;
	private String name;
	private int capacity;
	
	public InstallationDTO(int id, String name, int capacity) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCapacity() {
		return capacity;
	}

}

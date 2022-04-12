package facade.dto;

import java.io.Serializable;

public class EventDTO implements Serializable {
	
	private static final long serialVersionUID = -4087131153704256744L;
	
	private int id;
	private String name;
	private String type;
	private String installation;
	
	public EventDTO(int id, String name, String type, String installation) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.installation = installation;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getInstallation() {
		return installation;
	}

}

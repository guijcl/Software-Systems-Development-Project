package facade.dto;

import java.io.Serializable;

public class TypeDTO implements Serializable {
	
	private static final long serialVersionUID = -4087131153704256744L;
	
	private int id;
	private String typeName;
	private String seatingType;
	private int maxCap;
	
	public TypeDTO(int id, String typeName, String seatingType, int maxCap) {
		this.id = id;
		this.typeName = typeName;
		this.seatingType = seatingType;
		this.maxCap = maxCap;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public String getSeatingType() {
		return seatingType;
	}
	
	public int getMaxCap() {
		return maxCap;
	}
}

package facade.dto;

import java.io.Serializable;

public class SeatDTO implements Serializable {

	private static final long serialVersionUID = -4087131153704256744L;
	private int id;
	private String rw;
	private String col;
	private String seatd;
	
	public SeatDTO(int id, String rw, String col) {
		this.id = id;
		this.rw = rw;
		this.col = col;
		this.seatd = rw + " " + col;
	}

	public int getId() {
		return id;
	}
	
	public String getRw() {
		return rw;
	}
	
	public String getCol() {
		return col;
	}
	
	public String getSeatd() {
		return seatd;
	}
}
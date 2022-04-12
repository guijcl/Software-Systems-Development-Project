package presentation.fx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Type {

	private final StringProperty eventType = new SimpleStringProperty();
	private final int typeID;
	
	public Type(String eventType, int typeID) {
		setEventType(eventType);
		this.typeID = typeID;
	}
	
	public final StringProperty eventTypeProperty() {
		return this.eventType;
	}
	
	public final String getEventType() {
		return this.eventTypeProperty().get();
	}
	
	public final void setEventType(final String eventType) {
		this.eventTypeProperty().set(eventType);
	}
	
	public int getTypeID() {
		return typeID;
	}
	
	@Override
	public String toString() {
		return getEventType();
	}
	
}

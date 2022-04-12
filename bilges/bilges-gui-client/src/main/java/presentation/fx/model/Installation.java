package presentation.fx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Installation {
	
	private final StringProperty installationName = new SimpleStringProperty();
	private final int installationID;
	
	public Installation(String installationName, int installationID) {
		setEventType(installationName);
		this.installationID = installationID;
	}
	
	public final StringProperty installationNameProperty() {
		return this.installationName;
	}
	
	public final String getInstallationName() {
		return this.installationNameProperty().get();
	}
	
	public final void setEventType(final String eventType) {
		this.installationNameProperty().set(eventType);
	}
	
	public int getInstallationID() {
		return installationID;
	}
	
	@Override
	public String toString() {
		return getInstallationName();
	}

}

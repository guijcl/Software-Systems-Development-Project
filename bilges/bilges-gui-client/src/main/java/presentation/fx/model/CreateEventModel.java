package presentation.fx.model;

import java.time.LocalDate;

import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateEventModel {
	
	private final StringProperty eventName;
	private final Property<LocalDate> dates;
	private final StringProperty startTime;
	private final StringProperty endTime;
	private final StringProperty producer;
	private final ObjectProperty<Type> selectedType;
	private final ObservableList<Type> types;
	
	public CreateEventModel(IEventServiceRemote es) {		
		eventName = new SimpleStringProperty();
		dates = new SimpleObjectProperty<>(null);
		startTime = new SimpleStringProperty();
		endTime = new SimpleStringProperty();
		producer = new SimpleStringProperty();
		selectedType = new SimpleObjectProperty<>(null);
		this.types = FXCollections.observableArrayList();
		try {
			es.createEvent().forEach(t -> types.add(new Type(t.getTypeName(), t.getId())));
		} catch (ApplicationException e) {
			// no types added
		}
	}
	
	public ObjectProperty<Type> selectedTypeProperty() {
		return selectedType;
	}

	public final Type getSelectedType() {
		return selectedType.get();
	}

	public final void setSelectedType(Type t) {
		this.selectedType.set(t);
	}

	public ObservableList<Type> getTypes() {
		return types;
	}

	public String getEventName() {
		return eventName.get();
	}

	public StringProperty eventNameProperty() {
		return eventName;
	}
	
	public Property<LocalDate> datesProperty() {
		return dates;
	}

	public String getStartTime() {
		return startTime.get();
	}

	public StringProperty startTimeProperty() {
		return startTime;
	}	
	
	public String getEndTime() {
		return endTime.get();
	}

	public StringProperty endTimeProperty() {
		return endTime;
	}
	
	public String getProducer() {
		return producer.get();
	}

	public StringProperty producerProperty() {
		return producer;
	}

	public void clearProperties() {
		eventName.set("");
		dates.setValue(null);
		startTime.set("");
		endTime.set("");
		selectedType.set(null);
	}

}

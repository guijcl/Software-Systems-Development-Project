package presentation.fx.model;

import java.time.LocalDate;

import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AssignInstallationModel {
	
	private final StringProperty eventName;
	private final Property<LocalDate> startSale;
	private final Property<LocalDate> endSale;
	private final DoubleProperty singlePrice;
	private final DoubleProperty passPrice;
	private final ObjectProperty<Installation> selectedInstallation;
	private final ObservableList<Installation> installations;
	
	public AssignInstallationModel(IEventServiceRemote es) {
		eventName = new SimpleStringProperty();
		startSale = new SimpleObjectProperty<>(null);
		endSale = new SimpleObjectProperty<>(null);
		singlePrice = new SimpleDoubleProperty();
		passPrice = new SimpleDoubleProperty();
		selectedInstallation = new SimpleObjectProperty<>(null);
		this.installations = FXCollections.observableArrayList();
		try {
			es.startInstallation().forEach(i -> installations.add(new Installation(i.getName(), i.getID())));
		} catch (ApplicationException e) {
			// no types added
		}
	}
	
	public ObjectProperty<Installation> selectedInstallationProperty() {
		return selectedInstallation;
	}

	public final Installation getSelectedInstallation() {
		return selectedInstallation.get();
	}

	public final void setSelectedInstallation(Installation i) {
		this.selectedInstallation.set(i);
	}

	public ObservableList<Installation> getInstallations() {
		return installations;
	}

	public String getEventName() {
		return eventName.get();
	}

	public StringProperty eventNameProperty() {
		return eventName;
	}
	
	public Property<LocalDate> getStartSale() {
		return startSale;
	}
	
	public Property<LocalDate> getEndSale() {
		return endSale;
	}

	public Double getSinglePrice() {
		return singlePrice.get();
	}

	public DoubleProperty singlePriceProperty() {
		return singlePrice;
	}	
	
	public Double getPassPrice() {
		return passPrice.get();
	}

	public DoubleProperty passPriceProperty() {
		return passPrice;
	}	

	public void clearProperties() {
		eventName.set("");
		startSale.setValue(null);
		endSale.setValue(null);
		singlePrice.set(0.0);
		passPrice.set(0.0);
		selectedInstallation.set(null);
	}

}

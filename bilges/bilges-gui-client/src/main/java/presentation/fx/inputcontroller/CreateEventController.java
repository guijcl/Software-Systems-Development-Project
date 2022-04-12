package presentation.fx.inputcontroller;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.fx.model.CreateEventModel;
import presentation.fx.model.Type;

public class CreateEventController extends BaseController {
	
	@FXML
	private TextField eventNameTextField;
	
	@FXML
	private DatePicker dateDatePicker;
	
	@FXML
	private TextField startTimeTextField;
	
	@FXML
	private TextField endTimeTextField;
	
	@FXML
	private TextField producerTextField;
	
	@FXML
	private ComboBox<Type> typeComboBox;
	
	@FXML
	private ListView<String> dateListView;
	
	private CreateEventModel model;
	
	private IEventServiceRemote eventService;
	private Stage stage;
	
	private List<String> all_dates = new ArrayList<>();
	private List<String> hours = new ArrayList<>();
	private ObservableList<String> result_dates = FXCollections.observableArrayList();
	
	public void setModel(CreateEventModel model) {
		this.model = model;
		eventNameTextField.textProperty().bindBidirectional(model.eventNameProperty());
		dateDatePicker.valueProperty().bindBidirectional(model.datesProperty());
		startTimeTextField.textProperty().bindBidirectional(model.startTimeProperty());
		endTimeTextField.textProperty().bindBidirectional(model.endTimeProperty());   
		producerTextField.textProperty().bindBidirectional(model.producerProperty());
		typeComboBox.setItems(model.getTypes());   
		typeComboBox.setValue(model.getSelectedType());
		dateListView.setItems(result_dates);
	}
	
	@FXML
	public void createEventAction(ActionEvent event) throws NumberFormatException, ParseException {
		String errorMessages = validateInput();
		

		if (errorMessages.length() == 0) {
			try {
				eventService.addEvent(model.getSelectedType().getEventType(), model.getEventName(), all_dates, hours, Integer.parseInt(model.getProducer())); 
				model.clearProperties();
				result_dates.clear();
				showInfo(i18nBundle.getString("create.event.success"));
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("create.event.error.adding") + ": " + e.getMessage());
			}
		} else
			showError(i18nBundle.getString("create.event.error.validating") + ":\n" + errorMessages);
	}
	
	@FXML
	public void addDateAction(ActionEvent event) throws NumberFormatException, ParseException {
		DateTimeFormatter day = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		all_dates.add(model.datesProperty().getValue().format(day));
		hours.add(model.getStartTime() + "/" + model.getEndTime());
		result_dates.add(model.datesProperty().getValue().format(day) + " " + model.getStartTime() + "/" + model.getEndTime());
	}
	
	@FXML
	public void goBackAction(ActionEvent event) throws ParseException, IOException {
		// This line to resolve keys against Bundle.properties
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
        // ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
    	FXMLLoader startupLoader = new FXMLLoader(getClass().getResource("/fxml/startMenu.fxml"), i18nBundle);
    	Parent root = startupLoader.load();
    	StartupController startupController = startupLoader.getController();    	
    	
    	startupController.setCustomerService(eventService);
    	startupController.setI18NBundle(i18nBundle);
    	startupController.setStage(stage);
    	
        Scene scene = new Scene(root, 600, 400);
       
        stage.setTitle(i18nBundle.getString("application3.title"));
        stage.setScene(scene);
        stage.show();
	}
	
	private String validateInput() {	
		StringBuilder sb = new StringBuilder();
		String eventName = model.getEventName();
		String startTime = model.getStartTime();
		String endTime = model.getEndTime();
		String producer = model.getProducer();
		if (eventName == null || eventName.length() == 0)
			sb.append(i18nBundle.getString("create.event.invalid.eventName"));
		if (startTime == null || startTime.length() == 0)
			sb.append(i18nBundle.getString("create.event.invalid.startTime"));
		if (endTime == null || endTime.length() == 0)
			sb.append(i18nBundle.getString("create.event.invalid.endTime"));
		if (producer == null || producer.length() == 0)
			sb.append(i18nBundle.getString("create.event.invalid.producer"));
		if (model.getSelectedType() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("create.event.invalid.selectedType"));
		}

		return sb.toString();
	}
	
	@FXML
	public void eventTypeSelected(ActionEvent event) {
		model.setSelectedType(typeComboBox.getValue());
	}

	public void setCustomerService(IEventServiceRemote eventService) {
		this.eventService = eventService;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

}

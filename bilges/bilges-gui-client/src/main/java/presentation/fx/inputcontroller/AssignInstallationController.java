package presentation.fx.inputcontroller;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.AssignInstallationModel;
import presentation.fx.model.Installation;

public class AssignInstallationController extends BaseController {
	
	@FXML
	private TextField eventNameTextField;
	
	@FXML
	private DatePicker startSaleDatePicker;
	
	@FXML
	private DatePicker endSaleDatePicker;
	
	@FXML
	private TextField singlePriceTextField;
	
	@FXML
	private TextField passPriceTextField;
	
	@FXML
	private ComboBox<Installation> installationsComboBox;
	
	private AssignInstallationModel model;
	
	private IEventServiceRemote eventService;
	private Stage stage;
	
	public void setModel(AssignInstallationModel model) {
		this.model = model;
		eventNameTextField.textProperty().bindBidirectional(model.eventNameProperty());
		startSaleDatePicker.valueProperty().bindBidirectional(model.getStartSale());
		endSaleDatePicker.valueProperty().bindBidirectional(model.getEndSale());
		singlePriceTextField.textProperty().bindBidirectional(model.singlePriceProperty(), new NumberStringConverter());
		passPriceTextField.textProperty().bindBidirectional(model.passPriceProperty(), new NumberStringConverter());   
		installationsComboBox.setItems(model.getInstallations());   
		installationsComboBox.setValue(model.getSelectedInstallation());
	}
	
	@FXML
	private void initialize() {
		UnaryOperator<Change> doubleFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*.[0-9]*")) { 
				return change;
			}
			return null;
		};

		singlePriceTextField.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter(),
				0.0, doubleFilter));
		passPriceTextField.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter(),
				0.0, doubleFilter));
	}
	
	@FXML
	public void assignInstallationAction(ActionEvent event) throws ParseException {
		String errorMessages = validateInput();
		

		if (errorMessages.length() == 0) {
			try {
				DateTimeFormatter day = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				eventService.assignInstallation(model.getEventName(), model.getSelectedInstallation().getInstallationName()
						, model.getStartSale().getValue().format(day), model.getSinglePrice(), model.getPassPrice());
				model.clearProperties();
				showInfo(i18nBundle.getString("assign.installation.success"));
			} catch (ApplicationException e) {
				e.printStackTrace();
				showError(i18nBundle.getString("assign.installation.error.adding") + ": " + e.getMessage());
			}
		} else
			showError(i18nBundle.getString("assign.installation.error.validating") + ":\n" + errorMessages);
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
		if (eventName == null || eventName.length() == 0)
			sb.append(i18nBundle.getString("assign.installation.invalid.eventName"));
		if (model.getSinglePrice() == 0.0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("assign.installation.invalid.SinglePrice"));
		}
		if (model.getPassPrice() == 0.0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("assign.installation.invalid.PassPrice"));
		}
		if (model.getSelectedInstallation() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("assign.installation.invalid.selectedType"));
		}

		return sb.toString();
	}
	
	@FXML
	public void installationSelected(ActionEvent event) {
		model.setSelectedInstallation(installationsComboBox.getValue());
	}

	public void setCustomerService(IEventServiceRemote eventService) {
		this.eventService = eventService;		
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

}

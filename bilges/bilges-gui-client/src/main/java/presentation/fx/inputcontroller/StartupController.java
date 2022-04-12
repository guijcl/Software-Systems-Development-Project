package presentation.fx.inputcontroller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.services.IEventServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.model.AssignInstallationModel;
import presentation.fx.model.CreateEventModel;

public class StartupController extends BaseController {
	
	private IEventServiceRemote eventService;
	private Stage stage;
	
	@FXML
	public void createEventStartAction(ActionEvent event) throws ParseException, IOException {
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
        // ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
		FXMLLoader createEventLoader = new FXMLLoader(getClass().getResource("/fxml/createEvent.fxml"), i18nBundle);
    	Parent root = createEventLoader.load();
    	
    	CreateEventController createEventController = createEventLoader.getController();
    	CreateEventModel createEventModel = new CreateEventModel(eventService);
    	createEventController.setModel(createEventModel);
    	createEventController.setCustomerService(eventService);
    	createEventController.setI18NBundle(i18nBundle);
    	createEventController.setStage(stage);
    	
        Scene scene = new Scene(root, 1280, 720);
       
        stage.setTitle(i18nBundle.getString("application.title"));
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
	public void assignInstallationStartAction(ActionEvent event) throws ParseException, IOException {
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
        // ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
    	FXMLLoader assignInstallationLoader = new FXMLLoader(getClass().getResource("/fxml/assignInstallation.fxml"), i18nBundle);
    	Parent root = assignInstallationLoader.load();
    	
    	AssignInstallationController assignInstallationController = assignInstallationLoader.getController();    	
    	AssignInstallationModel assignInstallationModel = new AssignInstallationModel(eventService);
    	assignInstallationController.setModel(assignInstallationModel);
    	assignInstallationController.setCustomerService(eventService);
    	assignInstallationController.setI18NBundle(i18nBundle);
    	assignInstallationController.setStage(stage);
    	
        Scene scene = new Scene(root, 1280, 720);
       
        stage.setTitle(i18nBundle.getString("application2.title"));
        stage.setScene(scene);
        stage.show();
	}
	
	public void setCustomerService(IEventServiceRemote eventService) {
		this.eventService = eventService;		
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

}

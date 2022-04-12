package presentation.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.services.IEventServiceRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.CreateEventController;
import presentation.fx.model.CreateEventModel;

public class CreateEventStartup extends Application {
    
	private static IEventServiceRemote eventService;

	@Override 
    public void start(Stage stage) throws IOException {
    
		// This line to resolve keys against Bundle.properties
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
        // ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
    	FXMLLoader createEventLoader = new FXMLLoader(getClass().getResource("/fxml/createEvent.fxml"), i18nBundle);
    	Parent root = createEventLoader.load();
    	CreateEventController createEventController = createEventLoader.getController();    	
    	
    	CreateEventModel createEventModel = new CreateEventModel(eventService);
    	createEventController.setModel(createEventModel);
    	createEventController.setCustomerService(eventService);
    	createEventController.setI18NBundle(i18nBundle);
    	
        Scene scene = new Scene(root, 600, 350);
       
        stage.setTitle(i18nBundle.getString("application.title"));
        stage.setScene(scene);
        stage.show();
    }
	
	public static void startGUI(IEventServiceRemote createEventHandler) {
		CreateEventStartup.eventService = createEventHandler;
        launch();
	}
}

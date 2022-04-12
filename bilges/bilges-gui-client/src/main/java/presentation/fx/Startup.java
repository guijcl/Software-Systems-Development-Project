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
import presentation.fx.inputcontroller.StartupController;

public class Startup extends Application {
	
	private static IEventServiceRemote eventService;

	@Override 
    public void start(Stage stage) throws IOException {
    
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
	
	public static void startGUI(IEventServiceRemote startupHandler) {
		Startup.eventService = startupHandler;
        launch();
	}

}

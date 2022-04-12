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
import presentation.fx.inputcontroller.AssignInstallationController;
import presentation.fx.model.AssignInstallationModel;

public class AssignInstallationStartup extends Application {
	
	private static IEventServiceRemote eventService;

	@Override 
    public void start(Stage stage) throws IOException {
    
		// This line to resolve keys against Bundle.properties
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
        // ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
    	FXMLLoader assignInstallationLoader = new FXMLLoader(getClass().getResource("/fxml/assignInstallation.fxml"), i18nBundle);
    	Parent root = assignInstallationLoader.load();
    	AssignInstallationController assignInstallationController = assignInstallationLoader.getController();    	
    	
    	AssignInstallationModel assignInstallationModel = new AssignInstallationModel(eventService);
    	assignInstallationController.setModel(assignInstallationModel);
    	assignInstallationController.setCustomerService(eventService);
    	assignInstallationController.setI18NBundle(i18nBundle);
    	
        Scene scene = new Scene(root, 600, 350);
       
        stage.setTitle(i18nBundle.getString("application2.title"));
        stage.setScene(scene);
        stage.show();
    }
	
	public static void startGUI(IEventServiceRemote assignInstallationHandler) {
		AssignInstallationStartup.eventService = assignInstallationHandler;
        launch();
	}

}

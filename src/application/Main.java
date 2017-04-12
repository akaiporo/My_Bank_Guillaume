package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private static Stage pStage;
	 
	public static Stage getPrimaryStage() {
	        return pStage;
	}

	private void setPrimaryStage(Stage pStage) {
	     Main.pStage = pStage;
	     Main.pStage.setResizable(false);
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			setPrimaryStage(primaryStage);
			Parent root = FXMLLoader.load(getClass().getResource("/login/login_view.fxml")); //The first screen will always be the login screen
			Scene scene = new Scene(root,800,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			pStage.setScene(scene);
			pStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(Main.class, args);
	}

}

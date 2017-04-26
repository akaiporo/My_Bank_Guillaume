package application;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import AddAccount.AddAccountController;
<<<<<<< HEAD
import compteCourant.CompteCourantController;
=======
>>>>>>> branch 'master' of https://github.com/akaiporo/my_bank_Guillaume.git
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

public class MainWindowController extends ControllerBase {
/*	
	@Inject
	private EntityManagerFactory emf;
*/	
	@FXML
	private StackPane content;
	
	@Override
	public void initialize(Mediator mediator) {
		try {
			content.getChildren().setAll(loadFxml("../compteCourant/CompteCourantList.fxml")); // Le mettre dans 'content'
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public StackPane getStackPane(){
		return this.content;
	}
	
	@FXML
	private void handleMenuFileQuit(ActionEvent event) {
		Alert alert = new Alert(
				AlertType.CONFIRMATION,
				"Vous �tes s�r de vouloir quitter ?",
				ButtonType.OK,
				ButtonType.CANCEL
		);
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.isPresent() && result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}
	
	@FXML
	private void handleButtonAddAccount(){
		try {
			FXMLLoader loader = new FXMLLoader(AddAccountController.class.getResource("../AddAccount/AddAccountView.fxml"));
			AddAccountController accountController = loader.getController();
			accountController.setParentContent(this.content);
			content.getChildren().setAll(loadFxml("../AddAccount/AddAccountView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}
	}
	
	@FXML
	private void handleButtonAddUser(){
		try {
			content.getChildren().setAll(loadFxml("../AddUser/AddUserView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}
	}
	
	@FXML
	private void handleButtonConnexion(){
		try {
			content.getChildren().setAll(loadFxml("../connexion/ConnexionView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}
	}
	
	@FXML
	private void handleButtonForgottenpwd(){
		try {
			content.getChildren().setAll(loadFxml("../connexion/ConnexionView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}
	}
	
	@FXML
	private void handleButtonIsncription(){
		try {
			content.getChildren().setAll(loadFxml("../connexion/ConnexionView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}
	}
	
}

package application;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import metier.Owner;

public class MainWindowController extends ControllerBase {
/*	
	@Inject
	private EntityManagerFactory emf;
*/	
	@FXML
	private StackPane content;
	public static StackPane contentPane;

	
	public static Owner currentOwner;
	
	@Override
	public void initialize(Mediator mediator) {
		try {
			content.getChildren().setAll(loadFxml("../authentification/AuthentificationView.fxml")); // Le mettre dans 'content'
			contentPane = content;
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
				"Vous ï¿½tes sï¿½r de vouloir quitter ?",
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
	private void handleButtonDeconnexion(){
		try {


			Alert alert  = new Alert(AlertType.CONFIRMATION, "Voulez-vous vraiment vous déconnecter ?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			ButtonType result = alert.getResult();
			if(result == ButtonType.NO) {
				return;			
			}
			this.currentOwner = null;
			content.getChildren().setAll(loadFxml("../authentification/AuthentificationView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}
	}
	
}

package application;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
		System.out.print(mediator);
		try {
			content.getChildren().setAll(loadFxml("../compteCourant/CompteCourantList.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*@FXML
	private void handleMenuFileOpen(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		File choix = chooser.showOpenDialog(null);
		String err ="";
		
		if(choix!=null) {			
			try {
				// Charger le fichier
				FileReader fr = new FileReader(choix);
				try {
					TaskCollection coll = TaskCollection.fromCsv(fr);
					
					try {
						// Charger le FXML (et son controleur)
						FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskList.fxml"));
						Node root = (Node)loader.load();
										
						content.getChildren().setAll(root); // Le mettre dans 'content'
					}
					catch(IOException e) {
						err = "Erreur lors de la pr�paration de l'affichage de la liste: "+e.getMessage();
					}
				}
				catch(IOException e) {
					err = "Erreur lors de la lecture du csv : "+e.getMessage();
				}
				catch(InvalidCsvFormatException e) {
					err = "Erreur de format csv : "+e.getMessage();
				}
				fr.close();
			}
			catch(IOException e) {
				err = "Erreur d'ouverture du csv : "+e.getMessage();
			}
			if(!err.isEmpty()) {
				new Alert(AlertType.ERROR, err).showAndWait();
			}
		}
	}*/
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
			content.getChildren().setAll(loadFxml("../AddAccount/AddAccountView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}
	}
	
	@FXML
	private void handleButtonAddUser(){
		try {
			content.getChildren().setAll(loadFxml("../inscriptionView/InscriptionView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}
	}
}

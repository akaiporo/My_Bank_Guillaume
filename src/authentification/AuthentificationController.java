package authentification;

import java.awt.Label;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.mindrot.jbcrypt.BCrypt;

import application.ControllerBase;
import application.Main;
import application.MainWindowController;
import application.Mediator;
import application.Tools;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import metier.Account;
import metier.Owner;

public class AuthentificationController extends ControllerBase {
	private EntityManager em;
	Owner owner = new Owner();
	
	@FXML private TextField login;
	@FXML private TextField pwd;
	@FXML private Button btn_connexion;
	@FXML private Button btn_forgottenpwd;
	@FXML private Button btn_inscription;
	
	@FXML private Label errlogin;
	@FXML private Label errpwd;

	@Override
	public void initialize(Mediator mediator) {
		try {	
			em = mediator.createEntityManager();	
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
		}	
	}

	@FXML 
	private void handleButtonConnexion(ActionEvent Event) {
		
		try {
			owner.setLogin(login.getText());
		}
		catch  (IllegalArgumentException e) {
			errlogin.setText(" The login cannot be empty");
		}
		try {
			owner.setPwd(pwd.getText());
		}
		catch  (IllegalArgumentException e) {
			errpwd.setText(" The password cannot be empty");
		}
	
		
		em.getTransaction().begin();
		em.persist(login);
		em.persist(pwd);
		
		try {
			em.getTransaction().commit();
		}
		catch(Exception e) {
			em.getTransaction().rollback();
		}
	}
		
	/*try {
	owner.setLogin(login.getText());
			if
	}
	catch (IllegalArgumentException e) {
			rrlogin.setText(" The login does not exist! ");
	}
	try {
		owner.setPwd(login.getText());	
	}
	catch (IllegalArgumentException e) {
		errpwd.setText(" The password is not correct! ");
	}*/

	
	@FXML 
	private void handleButtonForgottenpwd(ActionEvent Event) {
		
		/*
		 * Ce bouton doit rediriger vers la page pour récupérer le mot de passe (passwordRecupView)
		 */
		try{ 
			System.out.print("Salut");
			MainWindowController.contentPane.getChildren().setAll(loadFxml("../passwordRecup/passwordRecupView.fxml"));
		}
		catch (IOException e){
		}
	}
	
	@FXML 
	private void handleButtonInscription(ActionEvent Event) {
		
		/*
		 * Ce bouton doit rediriger vers la page pour ajouter un nouveau user (AddUserView)
		 */
		
		try{ 
			MainWindowController.contentPane.getChildren().setAll(loadFxml("../AddUser/AddUserView.fxml"));
		}
		catch (IOException e){
		}
	}
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}

}

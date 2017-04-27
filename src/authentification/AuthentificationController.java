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
import javax.persistence.Query;

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
		try {
			this.isSameCredentials();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		em.getTransaction().begin();
		em.persist(owner);
		try {
			em.getTransaction().commit();
		}
		catch(Exception e) {
			em.getTransaction().rollback();
		}
	}
	
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
	
	public boolean isSameCredentials() throws SQLException{
		
		String inputpwd = this.pwd.getText();
		String inputlogin = this.login.getText();
		String hashed = "";
		
		Query q = em.createQuery("SELECT l FROM Owner l WHERE l.login = : inputlogin "); 
		q.setParameter("login", inputlogin);
		List lg = q.getResultList();
			
		if (login.equals(lg)) {
				
				Query u = em.createQuery("SELECT p FROM Owner p WHERE p.pwd = : inputpwd "); 
				u.setParameter("pwd", inputpwd);
				String pd = u.getResultList();
				
					if(BCrypt.checkpw(inputpwd, pd)) {
						
						System.out.print(String.format("Welcome to your personnal bank, %s!"));
						return true;
					}
					else {
						System.out.print("Credentials dot not match. Please try again.");
						return false;
					}
			}
			else {
				System.out.print(" The login is incorrect ! Please try again! ");
			}			
		
	}

}

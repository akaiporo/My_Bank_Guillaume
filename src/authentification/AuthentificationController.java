package authentification;


import java.io.IOException;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.mindrot.jbcrypt.BCrypt;

import application.ControllerBase;
import application.MainWindowController;
import application.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
		
		/*em.getTransaction().begin();
		em.persist(owner);
		try {
			em.getTransaction().commit();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			em.getTransaction().rollback();
		}*/
	}
	
	@FXML 
	private void handleButtonForgottenpwd(ActionEvent Event) {
		
		/*
		 * Ce bouton doit rediriger vers la page pour r�cup�rer le mot de passe (passwordRecupView)
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
	
	public boolean isSameCredentials() throws SQLException {
		
		String inputpwd = this.pwd.getText();
		String inputlogin = this.login.getText();
		String hashed = "";
		
		/* Dans un premier temps on va v�rifier que le login qui a �t� saisi existe dans la BDD
		 * Si elle existe, on va ensuite v�rifier que le mot de passe saisi existe dans la BDD et correspond au login saisi
		 * Si c'est le cas l'authentification est ok, sinon le mot de passe est incorrect!
		 * Si le login n'existe pas dansla BDD il ya un message d'errer "login incorrect"
		 */
		Query q = em.createQuery("SELECT l FROM Owner l WHERE l.login = :inputlogin", String.class); 
		q.setParameter("inputlogin", inputlogin);
		Owner lg = (Owner)q.getSingleResult();
		if (lg != null) {
				/*Query u = em.createQuery("SELECT p FROM Owner p WHERE p.pwd = :inputpwd", String.class); 
				u.setParameter("pwd", inputpwd);
				hashed = (String)u.getSingleResult();*/
				hashed = lg.getPwd();
					if(BCrypt.checkpw(inputpwd, hashed)) {
						System.out.print(String.format("Welcome to your personnal bank, %s!", lg.getFirstName()));
						MainWindowController.currentOwner = lg;
						try{ 
							MainWindowController.contentPane.getChildren().setAll(loadFxml("../compteCourant/CompteCourantList.fxml"));
						}
						catch (IOException e){
						}
						return true;
					}
					else {
						System.out.print("Credentials dot not match. Please try again.");
						return false;
					}
		}
		else {
			System.out.print(" The login is incorrect ! Please try again! ");
			return false;
		}			
		
	}

}

package connexion;

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
import application.Mediator;
import application.Tools;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import metier.Account;
import metier.Owner;

public class ConnexionController extends ControllerBase {
	private EntityManager em;
	Owner owner = new Owner();
	
	@FXML private TextField login;
	@FXML private TextField pwd;
	@FXML private Button btn_connexion;
	@FXML private Button btn_forgottenpwd;
	@FXML private Button btn_inscription;
	
	@FXML private Label errlogin;
	@FXML private label errpwd;

	@Override
	public void initialize(Mediator mediator) {
		
		try {	
			em = mediator.createEntityManager();	
		}
		catch(PersistenceException e) {
		}
	}

	@FXML 
	private void handleButtonConnexion(ActionEvent Event) {
	
		/*public boolean isSameCredentials(String login,String pwd) throws SQLException{
			
			try{
			  connexion = DriverManager.getConnection(Tools.getUrl(), "root", "");
			  String query = String.format("SELECT * FROM owner WHERE owner_firstname = \'%s\'", login);
		      Statement st = con.createStatement();
		      ResultSet rs = st.executeQuery(query);
		      
		      // iterate through the java resultset
		      while (rs.next())
		      {
		       
		        firstname = rs.getString("owner_firstname");
		        hashed = rs.getString("pwd");
		      }
		      st.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			// TODO : Connect to database to get password
			String db_login = firstname; //String type
			String db_pwd = hashed;   //String type, but hashed
		
			String password = pwd;
			BCrypt.hashpw(password, BCrypt.gensalt(15));
			password = BCrypt.hashpw(password, BCrypt.gensalt(15));
			if(BCrypt.checkpw(pwd, db_pwd)){
				System.out.print(String.format("Welcome to your personnal bank, %s!", firstname));
				return true;
			}
			else{
				System.out.print("Credentials dot not match. Please try again.");
				return false;
			}
		}

	}*/
		
		
		/*try {
			owner.setLogin(login.getText());
			if
		}
		catch (IllegalArgumentException e) {
			errlogin.setText(" The login does not exist! ");
		}
		try {
			owner.setPwd(login.getText());	
		}
		catch (IllegalArgumentException e) {
			errpwd.setText(" The password is not correct! ");
		}
	}*/
	
	@FXML 
	private void handleButtonForgottenpwd(ActionEvent Event) {
		/*
		try {
			content.getChildren().setAll(loadFxml("../AddUser/AddUserView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}  il faudra changer le lien Fxml quand la page forgottenpassword aura sera créée*/
		
	}
	
	@FXML 
	private void handleButtonInscription(ActionEvent Event) {
		
		try {
			content.getChildren().setAll(loadFxml("../AddUser/AddUserView.fxml")); // Le mettre dans 'content'
		}
		catch(IOException e) {
			// TODO alert
		}
		
	}

}

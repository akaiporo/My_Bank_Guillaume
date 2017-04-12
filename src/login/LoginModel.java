package login;
import org.mindrot.jbcrypt.BCrypt;

import application.Tools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;


/**
 * Gère les accès aux données relatives à la connexion : 
 * -> Check si le login passé existe, et si oui, check si le mot de passe est le bon
 * @author Guillaume
 *
 */
@SuppressWarnings("unused")
public class LoginModel {
	
	public LoginModel() {
		
	}
	

	/*
	 * Récupère le mdp passer par l'utilisateur(String) et le compare avec le mpd stocker côté base(hasher)
	 * Comparaison via la methode checkpw de BCrypt
	 */
	public boolean isSameCredentials(String login,String pwd) throws SQLException{
		String firstname = "";
		String hashed = "";
		try{
		  Connection con = DriverManager.getConnection(Tools.getUrl(), "root", "");
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

}

/*@SuppressWarnings("resource")
	public login_view() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Pour retourner en arrière, tapez 'accueil'. Si non, appuye sur entrée");
		String choice = scanner.nextLine();
		if(choice.equals("accueil")){
			new accueil();
		}
		else{
			System.out.println("Veuillez saisir votre login : ");
			String login = scanner.nextLine();
			System.out.println("Veuillez saisir votre mot de passe : ");
			String pwd = scanner.nextLine();
			
			new login_controller().isSameCredentials(login, pwd);
		}
	}*/

package login;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Will be called  in the javafx UI to create a new login object with the InputTextFields
 * @author Guillaume
 *
 */


public class LoginController implements Initializable{

			 //  fx:id="forgotPassword"
			@FXML private Button forgotPassword; // Value injected by FXMLLoader 
			@FXML private TextField username;
			@FXML private TextField password;
		    
		    @Override // This method is called by the FXMLLoader when initialization is complete
		    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		        assert forgotPassword != null : "fx:id=\"forgotPassword\" was not injected: check your FXML file '/Forgotten_password/forgotten_password_view.fxml'.";

		        // initialize your logic here: all @FXML variables will have been injected

		    }
	
	
	public boolean isSameCredentials(){
		
		LoginModel Model = new LoginModel();
		
		try {
			return Model.isSameCredentials(this.username.getText(), this.password.getText());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}
	@FXML
	public void onClick(ActionEvent event) throws IOException{
		System.out.print(username.getText());
		  try{
	            Parent root = FXMLLoader.load(getClass().getResource("/Forgotten_password/forgotten_password_view.fxml")); 
	            Scene scene = new Scene(root,800,400);
	            Main.getPrimaryStage().setScene(scene);
		  }
		  catch(IllegalArgumentException e){
			  
		  }
	}
	
	@FXML
	public void setText(){
		
	}
}

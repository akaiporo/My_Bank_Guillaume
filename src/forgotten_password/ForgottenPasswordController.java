package forgotten_password;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.Tools;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ForgottenPasswordController implements Initializable{

	 //  fx:id="forgotPassword"
	@FXML private Button sendPassword; // Value injected by FXMLLoader 
	@FXML private TextField email;
	@FXML private TextField confirmEmail;
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert sendPassword != null : "fx:id=\"sendPassword\" was not injected: check your FXML file 'simple.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }

	public ForgottenPasswordController() {
		// TODO Auto-generated constructor stub
	}
	
	@FXML
	public void sendPassword() throws IOException{
		
		if(email.getText().equals(confirmEmail.getText()) && Tools.checkMail(email.getText())){
			System.out.print(String.format("New password sent to %s", confirmEmail.getText()));
			 try{
		            Parent root = FXMLLoader.load(getClass().getResource("/login/login_view.fxml"));
		            Scene scene = new Scene(root,800,400);
		            Main.getPrimaryStage().setScene(scene);
			  }
			  catch(IllegalArgumentException e){
				  
			  }
		}
		else System.out.print("Emails aren't matching");

	}

}

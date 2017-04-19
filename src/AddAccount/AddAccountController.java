package AddAccount;

import javax.persistence.EntityManager;

import application.ControllerBase;
import application.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import metier.Bank;

public class AddAccountController extends ControllerBase {
	
	@FXML private TextField bank_name;
	@FXML private TextField bank_code;
	@FXML private TextField agency_name;
	@FXML private TextField line1;
	@FXML private TextField line2;
	@FXML private TextField postal_code;
	@FXML private TextField city;
	@FXML private TextField advisor_name;
	@FXML private TextField advisor_firstname;
	@FXML private TextField advisor_phonenumber;
	@FXML private TextField advisor_email;
	@FXML private Button confirm_addaccount;
	@FXML private Button cancel_addaccount;
	
	@Override 
	public void initialize (Mediator mediator){
		EntityManager em = mediator.createEntityManager();
	}

	private void handleButtonOK(ActionEvent event){
		Bank bank=new Bank (bank_code.getText(),bank_name.getText());
	}

}

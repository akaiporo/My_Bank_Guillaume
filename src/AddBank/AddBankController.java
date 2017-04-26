package AddBank;

import javax.persistence.EntityManager;

import application.ControllerBase;
import application.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import metier.Bank;


public class AddBankController extends ControllerBase {
	private EntityManager em;
	Bank currentBank=new Bank();
	
	@FXML private TextField bank_name;
	@FXML private TextField bank_code;
	@FXML private Label bank_error;
	@FXML private Button OK;
	@FXML private Button cancel;
	
	
	@Override
	public void initialize(Mediator mediator) {
		em = mediator.createEntityManager();
	}
	
	@FXML
	private void handleButtonOK (ActionEvent event){
		try{
			currentBank.setBankName(bank_name.getText());
		}
		catch (IllegalArgumentException e){
			bank_error.setText(e.getMessage());
		}
		try{
			currentBank.setBankCode(bank_code.getText());
		}
		catch (IllegalArgumentException e){
			bank_error.setText(e.getMessage());
		}
		
		em.getTransaction().begin();
		em.persist(currentBank);
		try{
			em.getTransaction().commit();
		}
		catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		//load le fxml AddAccount
		
	}
	@FXML
	private void handleButtonCancel (ActionEvent event){
		//load le fxml AddAccount
	}
}

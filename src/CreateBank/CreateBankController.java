package CreateBank;

import javax.persistence.EntityManager;

import application.ControllerBase;
import application.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import metier.Bank;
import metier.CpCity;
import metier.Address;
import metier.Agency;


public class CreateBankController extends ControllerBase {
	private EntityManager em;
	
	@FXML private TextField bank_name;
	@FXML private TextField bank_code;
	@FXML private TextField agency_name;
	@FXML private TextField counter_code;
	@FXML private TextField line1;
	@FXML private TextField line2;
	@FXML private TextField postal_code;
	@FXML private TextField city;
	@FXML private TextField advisor_name;
	@FXML private TextField advisor_firstname;
	@FXML private TextField advisor_phonenumber;
	@FXML private TextField advisor_email;
	@FXML private Button OK;
	@FXML private Button cancel;

	
	@Override 
	public void initialize (Mediator mediator){
		EntityManager em = mediator.createEntityManager();
	}

	private void handleButtonOK (ActionEvent event){
		Bank bank=new Bank (bank_code.getText(),bank_name.getText());
		CpCity cpcity=new CpCity (postal_code.getText(), city.getText());
		Address address=new Address (line1.getText(),line2.getText(),cpcity);
		Agency agency=new Agency (agency_name.getText(), counter_code.getText(), address,bank);
		
		em.getTransaction().begin();
		em.persist(bank);
		em.getTransaction().commit();
	}

}

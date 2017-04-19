package inscriptionView;

import javax.persistence.EntityManager;

import application.ControllerBase;
import application.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class InscriptionViewController extends ControllerBase {
	
	@FXML private TextField login;
	@FXML private TextField pwd;
	@FXML private TextField confirm_pwd;
	@FXML private TextField owner_name;
	@FXML private TextField owner_firstname;
	@FXML private TextField owner_mail;
	@FXML private TextField owner_id_address1;
	@FXML private TextField owner_id_address2;
	@FXML private ComboBox postalcode;
	@FXML private ComboBox city;
	@FXML private TextField owner_phonenumber;
	@FXML private DatePicker owner_birthdate;
	@FXML private Button btn_ok;
	@FXML private Button btn_cancel;


	public InscriptionViewController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(Mediator mediator) {
		EntityManager em = mediator.createEntityManager();
		//@SuppressWarnings("unchecked");
		
	}
	@FXML
	private void handleButtonOk(ActionEvent event) {
		
		/*Owner owner = new Owner(login.getText(),pwd.getText(),confirm_pwd.getText(),owner_name.getText(),
								owner_firstname.getText(), 
								owner_mail.getText(),owner_phonenumber.getText(),
								owner_birthdate.getValue());
		
		Address address = new Address(owner_id_address1.getText(),owner_id_address2.getText());

		CpCity cpcity = new CpCity(postalcode.getText(),city.getText());*/

	}
	@FXML
	private void handleBtnCancel(ActionEvent event) {

	}
	
	/*
	 @Override
	public void initialize(Mediator mediator) {
		EntityManager em = mediator.createEntityManager();
		@SuppressWarnings("unchecked")
		List<PeriodicTransaction> Transactions = em.createNamedQuery("PeriodicTransaction.findAll").getResultList();
		// Remplissage du tableview avec tasks
		this.listTransactions.setItems(FXCollections.observableList(Transactions));
	}
	  */
	 
}

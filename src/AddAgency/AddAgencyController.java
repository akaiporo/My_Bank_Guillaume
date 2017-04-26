package AddAgency;

import java.util.List;

import javax.persistence.EntityManager;

import application.ControllerBase;
import application.Mediator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import metier.Bank;

public class AddAgencyController extends ControllerBase {
	
	private EntityManager em;
	private Bank newBank= new Bank();
	
	@FXML private TextField agency_name;
	@FXML private TextField counter_code;
	@FXML private TextField address_line1;
	@FXML private TextField address_line2;
	@FXML private TextField postal_code;
	@FXML private TextField new_city;
	@FXML private ChoiceBox <Bank> choiceBank;
	@FXML private ChoiceBox <String> choiceCity;
	@FXML private Button OK;
	@FXML private Button Cancel;
	
	public void initialize(Mediator mediator){
		em = mediator.createEntityManager();
		
		List<Bank> banks = em.createNamedQuery("Bank.findAll", Bank.class).getResultList();
		newBank.setBankName("(new bank)");
		banks.add(newBank);
		this.choiceBank.setItems(FXCollections.observableList(banks));
	}
	

}

package AddAccount;
/*
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.capgemini.training.java.jfx.biz.Project;
import com.capgemini.training.java.jfx.biz.Task;
import com.capgemini.training.java.jfx.biz.User;

import application.ControllerBase;
import application.Mediator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import metier.AccountType;
import metier.Advisor;
import metier.Agency;
import metier.Bank;
import metier.CpCity;

public class AddAccountController extends ControllerBase {
	private EntityManager em;
	
	@FXML private TextField first_total;
	@FXML private TextField overdraft;
	@FXML private TextField account_number;
	@FXML private TextField interest_rate;
	@FXML private ChoiceBox <Agency> choiceAgency;
	@FXML private ChoiceBox <Bank> choiceBank;
	@FXML private ChoiceBox <Advisor> choiceAdvisor;
	@FXML private ChoiceBox <AccountType> choiceAccountType;
	@FXML private DatePicker date_creation;
	@FXML private Button OK;
	@FXML private Button cancel;
	@FXML private Button new_bank;
	@FXML private Button new_agency;
	@FXML private Button new_advisor;
	
	private void initialize(Mediator mediator){
		try {	
			List<Agency> agencies = em.createNamedQuery("Agency.findAll", Agency.class).getResultList();
			List<Bank> banks = em.createNamedQuery("Bank.findAll", Bank.class).getResultList();
			List<Advisor> advisors = em.createNamedQuery("Advisor.findAll", Advisor.class).getResultList();
			List<AccountType> accounttypes = em.createNamedQuery("AccountType.findAll", AccountType.class).getResultList();
			this.choiceAgency.setItems(FXCollections.observableList(agencies));
			this.choiceBank.setItems(FXCollections.observableList(banks));
			this.choiceAdvisor.setItems(FXCollections.observableList(advisors));
			this.choiceAccountType.setItems(FXCollections.observableList(accounttypes));
		}
		catch(PersistenceException e) {
		}
	}
}
*/
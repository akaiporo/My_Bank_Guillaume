package AddAccount;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import application.ControllerBase;
import application.Mediator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import metier.Account;
import metier.AccountType;
import metier.Advisor;
import metier.Agency;
import metier.CountryCode;
import metier.DateUtils;

public class AddAccountController extends ControllerBase {
	private EntityManager em;
	Account currentAccount=new Account();
	
	@FXML private TextField first_total;
	@FXML private TextField overdraft;
	@FXML private TextField account_number;
	@FXML private TextField interest_rate;
	@FXML private ChoiceBox <Agency> choiceAgency;
	@FXML private ChoiceBox <Advisor> choiceAdvisor;
	@FXML private ChoiceBox <AccountType> choiceAccountType;
	@FXML private ChoiceBox <CountryCode> choiceCountryCode;
	@FXML private DatePicker date_creation;
	@FXML private Button OK;
	@FXML private Button cancel;

	
	@Override
	public void initialize(Mediator mediator){
		try {	
			em = mediator.createEntityManager();
			
			List<Agency> agencies = em.createNamedQuery("Agency.findAll", Agency.class).getResultList();
			this.choiceAgency.setItems(FXCollections.observableList(agencies));
			
			List<Advisor> advisors = em.createNamedQuery("Advisor.findAll", Advisor.class).getResultList();
			this.choiceAdvisor.setItems(FXCollections.observableList(advisors));
			
			List<AccountType> accounttypes = em.createNamedQuery("AccountType.findAll", AccountType.class).getResultList();
			this.choiceAccountType.setItems(FXCollections.observableList(accounttypes));
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
		}
		
	}
	@FXML
	private void handleButtonOK (ActionEvent event){
		try{
			currentAccount.setAgency(choiceAgency.getValue());
		}
		catch (NullPointerException e){//display "Please choose an existing agency or create one"
		}
		try{
			currentAccount.setAccountNumber(this.account_number.getText());
		}
		catch (IllegalArgumentException e){//display error
		}
		try{
			currentAccount.setCreationDate(DateUtils.LocalDateToDate(this.date_creation.getValue()));
		}
		catch (IllegalArgumentException e){//display error
		}
		try{
			currentAccount.setFirstTotal(Double.parseDouble(this.first_total.getText()));
		}
		catch (IllegalArgumentException e){//display error
		}
		try{
			currentAccount.setOverdraft(Integer.parseInt(this.overdraft.getText()));
		}
		catch(IllegalArgumentException e){//display error
		}
		try{
			currentAccount.setInterestRate(Double.parseDouble(this.interest_rate.getText()));
		}
		catch(IllegalArgumentException e){//display error
		}
		try{
			currentAccount.setCountryCode(choiceCountryCode.getValue());
		}
		catch (NullPointerException e){//display "Please choose existing country code"
		}
		try{
			currentAccount.setAccountType(choiceAccountType.getValue());
		}
		catch (NullPointerException e){//display "Please choose existing account type"
		}
		
		em.getTransaction().begin();
		em.persist(currentAccount);
		try{
			em.getTransaction().commit();
		}
		catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		/*try {
			MainWindowController.content.getChildren().setAll(loadFxml("../main_view/TransactionList.fxml"));
		}
		catch(IOException e) {
			// TODO alert
		}*/
		
		
		//loader.getController()....   pour conserver l'info currentAccount
		
	}
	@FXML
	private void handleButtonCancel (ActionEvent event){
		
	}
	
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	
	
}

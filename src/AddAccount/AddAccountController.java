package AddAccount;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import application.ControllerBase;
import application.MainWindowController;
import application.Mediator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import metier.Account;
import metier.AccountType;
import metier.Address;
import metier.Advisor;
import metier.Agency;
import metier.Bank;
import metier.CountryCode;
import metier.DateUtils;


public class AddAccountController extends ControllerBase {
	private EntityManager em;
	protected Account currentAccount=new Account();
	private Agency newAgency=new Agency();
	
	private Advisor newAdvisor=new Advisor();
	
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
	@FXML private AnchorPane accountPane;
	@FXML private Label account_error;

	@Override
	public void initialize(Mediator mediator){
		try {	
			em = mediator.createEntityManager();
			
			List<Agency> agencies = em.createNamedQuery("Agency.findAll", Agency.class).getResultList();
			newAgency.setAgencyName("(new agency)");
			agencies.add(newAgency); //permettra d'ajouter une nouvelle agence
			this.choiceAgency.setItems(FXCollections.observableList(agencies));
			
			List<Advisor> advisors = em.createNamedQuery("Advisor.findAll", Advisor.class).getResultList();
			newAdvisor.setName("(new");
			newAdvisor.setFirstName("advisor)");
			advisors.add(newAdvisor);
			this.choiceAdvisor.setItems(FXCollections.observableList(advisors));
		
			List<AccountType> accounttypes = em.createNamedQuery("AccountType.findAll", AccountType.class).getResultList();
			this.choiceAccountType.setItems(FXCollections.observableList(accounttypes));
			
			List<CountryCode> countrycodes = em.createNamedQuery("CountryCode.findAll", CountryCode.class).getResultList();
			this.choiceCountryCode.setItems(FXCollections.observableList(countrycodes));			
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
		}
	}
	
	@FXML
	private void addAgency (ActionEvent event){
		ChoiceBox catAgency = (ChoiceBox)event.getTarget();
		Agency tmp=(Agency)catAgency.getValue();
		if (tmp.getAgencyName().equals("(new agency)")){
			try{
				MainWindowController.contentPane.getChildren().setAll(loadFxml("../AddAgency/AddAgencyView.fxml"));
			}
			catch(IOException e){	
				System.out.println(e.getMessage());
			}
		}
	}
	
	@FXML
	private void addAdvisor (ActionEvent event){
		ChoiceBox catAdvisor = (ChoiceBox)event.getTarget();
		if (catAdvisor.getValue()==null){
			try{ 
				MainWindowController.contentPane.getChildren().setAll(loadFxml("../AddAdvisor/AddAdvisorView.fxml"));
			}
			catch (IOException e){
			}
		}
	}
	
	@FXML
	private void handleButtonOK (ActionEvent event){
		/*try{
			currentAccount.setAgency(choiceAgency.getValue());
		}
		catch (NullPointerException e){
			account_error.setText("Please choose an existing agency or create one");
		}
		try{
			currentAccount.setAccountNumber(this.account_number.getText());
		}
		catch (IllegalArgumentException e){
			account_error.setText(e.getMessage());
		}
		try{
			currentAccount.setCreationDate(DateUtils.LocalDateToDate(this.date_creation.getValue()));
		}
		catch (IllegalArgumentException e){
			account_error.setText(e.getMessage());
		}
		try{
			currentAccount.setFirstTotal(Double.parseDouble(this.first_total.getText()));
		}
		catch (IllegalArgumentException e){
			account_error.setText(e.getMessage());
		}
		try{
			currentAccount.setOverdraft(Integer.parseInt(this.overdraft.getText()));
		}
		catch(IllegalArgumentException e){
			account_error.setText(e.getMessage());
		}
		try{
			currentAccount.setInterestRate(Double.parseDouble(this.interest_rate.getText()));
		}
		catch(IllegalArgumentException e){
			account_error.setText(e.getMessage());
		}
		try{
			currentAccount.setCountryCode(choiceCountryCode.getValue());
		}
		catch (NullPointerException e){
			account_error.setText("Please choose an existing country code");
		}
		try{
			currentAccount.setAccountType(choiceAccountType.getValue());
		}
		catch (NullPointerException e){
			account_error.setText("Please choose an existing account type");
		}
		
		em.getTransaction().begin();
		em.persist(currentAccount);
		try{
			em.getTransaction().commit();
		}
		catch(Exception e){
			em.getTransaction().rollback();
			return;
		}*/
		
		try{ 
			MainWindowController.contentPane.getChildren().setAll(loadFxml("../compteCourant/CompteCourantList.fxml"));
		}
		catch (IOException e){
		}
		
		/*
		TODO : conserver l'info currentAccount pour charger les bonnes infos sur la page suivante  
		*/
	}
	
	@FXML
	private void handleButtonCancel (ActionEvent event){
		try{ 
			MainWindowController.contentPane.getChildren().setAll(loadFxml("../compteCourant/CompteCourantList.fxml"));
		}
		catch (IOException e){
		}
		 //attention pour une inscription il faut disable ce button
		//conserver les infos d'avant clic : quel est le currentAccount?
	}
	
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	
	
}

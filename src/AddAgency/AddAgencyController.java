package AddAgency;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import metier.Address;
import metier.Agency;
import metier.Bank;
import metier.CpCity;
import javafx.scene.control.Alert.AlertType;

public class AddAgencyController extends ControllerBase {
	
	private EntityManager em;
	private Agency currentAgency=new Agency();
	private Address currentAddress=new Address();
	private CpCity currentCpCity=new CpCity();
	private Bank newBank= new Bank();
	
	@FXML private TextField agency_name;
	@FXML private TextField counter_code;
	@FXML private TextField address_line1;
	@FXML private TextField address_line2;
	@FXML private TextField postal_code;
	@FXML private TextField new_city;
	@FXML private Label agency_error;
	@FXML private ChoiceBox <Bank> choiceBank;
	@FXML private ChoiceBox <String> choiceCity;
	@FXML private Button OK;
	@FXML private Button cancel;
	
	@Override
	public void initialize(Mediator mediator){
		try{
			em = mediator.createEntityManager();
		
			List<Bank> banks = em.createNamedQuery("Bank.findAll", Bank.class).getResultList();
			newBank.setBankName("(new bank)");
		    banks.add(newBank);
			this.choiceBank.setItems(FXCollections.observableList(banks));
			
			List<String> cities = em.createNamedQuery("cpcity.findAllcity", String.class).getResultList();
			cities.add("(new city)");
			this.choiceCity.setItems(FXCollections.observableList(cities));
			
		}
		catch(PersistenceException e){
			this.processPersistenceException(e);
		}
	}
	
	
	@FXML
	private void addBank (ActionEvent event){
		ChoiceBox catBank = (ChoiceBox)event.getTarget();
		Bank tmp=(Bank)(catBank.getValue());
		if (tmp.getBankName().equals("(new bank)")){
			try{
				MainWindowController.contentPane.getChildren().setAll(loadFxml("../AddBank/AddBankView.fxml"));
			}
			catch(IOException e){	
				System.out.println(e.getMessage());
			}
		}
	}
	
	@FXML
	private void addCity (ActionEvent event){
		ChoiceBox catCity = (ChoiceBox)event.getTarget();
		String tmp=(String)catCity.getValue();
		if (tmp.equals("(new city)")){
			//set able le texfield
		}
	}
	
	@FXML
	private void handleButtonOK (ActionEvent event){
		try{
			currentCpCity.setPostalCode(postal_code.getText());
		}
		catch(IllegalArgumentException e){
			agency_error.setText(e.getMessage());
		}
		if (choiceCity.getValue().equals("(new city)")){
			try{
				currentCpCity.setCity(new_city.getText());
			}
			catch (IllegalArgumentException e){
				agency_error.setText(e.getMessage());
			}
		}
		else {
			try{
				currentCpCity.setCity(choiceCity.getValue());
			}
			catch (IllegalArgumentException e){
				agency_error.setText("Please choose an existing city or add one");
			}
		}
		
		try{
			currentAddress.setLine1(address_line1.getText());
		}
		catch(IllegalArgumentException e){
			agency_error.setText(e.getMessage());
		}
		currentAddress.setLine2(address_line2.getText());
		try{
			currentAddress.setCpCity(currentCpCity);
		}
		catch(IllegalArgumentException e){
			agency_error.setText(e.getMessage());
		}
		
		
		try{
			currentAgency.setAgencyName(agency_name.getText());
		}
		catch (IllegalArgumentException e){
			agency_error.setText(e.getMessage());
		}
		try{
			currentAgency.setCounterCode(counter_code.getText());
		}
		catch (IllegalArgumentException e){
			agency_error.setText(e.getMessage());
		}
		try{
			currentAgency.setBank(choiceBank.getValue());
		}
		catch (NullPointerException e){
			agency_error.setText("Please choose an existing bank or create one");
		}
		try{
			currentAgency.setAddress(currentAddress);
		}
		catch (NullPointerException e){
			agency_error.setText(e.getMessage());
		}
		
		em.getTransaction().begin();
		em.persist(currentCpCity);
		em.persist(currentAddress);
		em.persist(currentAgency);
		try{
			em.getTransaction().commit();
		}
		catch(Exception e){
			em.getTransaction().rollback();
			return;
		}
		
		try{ 
			MainWindowController.contentPane.getChildren().setAll(loadFxml("../AddAccount/AddAccountView.fxml"));
		}
		catch (IOException e){
		}
	}

	
	@FXML
	private void handleButtonCancel (ActionEvent event){
		try{ 
			MainWindowController.contentPane.getChildren().setAll(loadFxml("../AddAccount/AddAccountView.fxml"));
		}
		catch (IOException e){
		}
	}
	
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
}

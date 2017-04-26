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
import javafx.scene.control.TextField;
import metier.Bank;
import javafx.scene.control.Alert.AlertType;

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

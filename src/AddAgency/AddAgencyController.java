package AddAgency;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import application.ControllerBase;
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
import metier.Advisor;
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
	List<String> cities =new ArrayList<String>();
	
	@FXML private TextField agency_name;
	@FXML private TextField counter_code;
	@FXML private TextField address_line1;
	@FXML private TextField address_line2;
	@FXML private TextField postal_code;
	@FXML private Label agency_error;
	@FXML private ChoiceBox <Bank> choiceBank;
	@FXML private ChoiceBox <String> choiceCity;
	@FXML private ChoiceBox<String> choicePostalCode;
	@FXML private Button OK;
	@FXML private Button cancel;
	
	/**
	 * Initialise les ChoiceBox bank,postalcode,city
	 */
	@Override
	public void initialize(Mediator mediator){
		agency_error.setText("");

		try{
			em = mediator.createEntityManager();
		
			List<Bank> banks = em.createNamedQuery("Bank.findAll", Bank.class).getResultList();
			newBank.setBankName("(new bank)");
		    banks.add(newBank);
			this.choiceBank.setItems(FXCollections.observableList(banks));
			
			List<String> postalcodes = em.createNamedQuery("cpcity.findAllpostalcode", String.class).getResultList();
			postalcodes.add("(new postal code)");
			this.choicePostalCode.setItems(FXCollections.observableList(postalcodes));
			
			cities.add("(new city)");
			this.choiceCity.setItems(FXCollections.observableList(cities));
		}
		catch(PersistenceException e){
			this.processPersistenceException(e);
		}
	}
	
	/**
	 * Gere une action consécutive à la sélection d'un postalcode ou de (new postal code)
	 */
	@FXML
	private void handleChoicePostalCode(ActionEvent event){
		ChoiceBox catPostalCode = (ChoiceBox)event.getTarget();
		String tmp=(String)catPostalCode.getValue();
		if (tmp.equals("(new postal code)")){
			/*
			 * Si c'est (new postal code) la sous scene correspondante (AddCpCity) est chargée
			 */
			this.loadSubScene("../AddCpCity/AddCpCityView.fxml"); 
		}
		else {
			/*
			 * Si c'est un postal code existant on restreint la liste des city dans la ChoiceBox city
			 */
			choiceCity.getItems().removeAll(cities);
			Query u = em.createQuery("SELECT c.city FROM CpCity c WHERE c.postalCode = :postalcode", String.class);
			u.setParameter("postalcode",choicePostalCode.getValue());
			cities = u.getResultList();
			cities.add("(new city)");
			this.choiceCity.setItems(FXCollections.observableList(cities));
		}
	}
	
	/*
	 * Gere une action consécutive à la sélection de (new bank)
	 */
	@FXML
	private void addBank (ActionEvent event){
		ChoiceBox catBank = (ChoiceBox)event.getTarget();
		Bank tmp=(Bank)(catBank.getValue());
		if (tmp.getBankName().equals("(new bank)")){
			/*
			 * Si c'est (new bank) la sous scene correspondante (AddBankView) est chargée
			 */
			this.loadSubScene("../AddBank/AddBankView.fxml");
		}
	}
	
	/*
	 * Gere une action consécutive à la sélection de (new city)
	 */
	@FXML
	private void addCity (ActionEvent event){
		ChoiceBox catCity = (ChoiceBox)event.getTarget();
		String tmp=(String)catCity.getValue();
		if (tmp.equals("(new city)")){
			/*
			 * Si c'est (new city) la sous scene correspondante (AddCpCity) est chargée
			 */
			this.loadSubScene("../AddCpCity/AddCpCityView.fxml");
		}
	}
	
	/*
	 * Gere une action consécutive à l'utilisation du bouton OK
	 */
	@FXML
	private void handleButtonOK (ActionEvent event){
		/*
		 * L'objet <CpCity> ("currentCpCity",créé vide) est rempli via les setters en testant chaque champ 
		 */
		try{
			currentCpCity.setPostalCode(choicePostalCode.getValue());
		}
		catch(NullPointerException e){
			agency_error.setText("Please choose an existing postal code or add one");
			return;
		}
		try{
			currentCpCity.setCity(choiceCity.getValue());
		}
		catch (IllegalArgumentException e){
			agency_error.setText("Please choose an existing city or add one");
			return;
		}
		/*
		 * L'objet <Address> ("currentAddress",créé vide) est rempli via les setters en testant chaque champ 
		 */
		try{
			currentAddress.setLine1(address_line1.getText());
		}
		catch(IllegalArgumentException e){
			agency_error.setText(e.getMessage());
			return;
		}
		currentAddress.setLine2(address_line2.getText());
		try{
			currentAddress.setCpCity(currentCpCity);
		}
		catch(IllegalArgumentException e){
			agency_error.setText(e.getMessage());
			return;
		}
		/*
		 * L'objet <Agency> ("currentAgency",créé vide) est rempli via les setters en testant chaque champ 
		 */
		try{
			currentAgency.setAgencyName(agency_name.getText());
		}
		catch (IllegalArgumentException e){
			agency_error.setText(e.getMessage());
			return;
		}
		try{
			currentAgency.setCounterCode(counter_code.getText());
		}
		catch (IllegalArgumentException e){
			agency_error.setText(e.getMessage());
			return;
		}
		try{
			currentAgency.setBank(choiceBank.getValue());
		}
		catch (NullPointerException e){
			agency_error.setText("Please choose an existing bank or create one");
			return;
		}
		try{
			currentAgency.setAddress(currentAddress);
		}
		catch (NullPointerException e){
			agency_error.setText(e.getMessage());
			return;
		}
		/*
		 * Ajout dans la base des objets currentCpCIty, currentAddress, currentAgency successivement
		 */
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
		/*
		 * La page précédente AddAccountView est chargée
		 */
		this.loadSubScene("../AddAccount/AddAccountView.fxml");
	}

	/*
	 * Gere une action consécutive à l'utilisation du bouton cancel : la page précédente AddAccountView est chargée
	 */
	@FXML
	private void handleButtonCancel (ActionEvent event){
		this.loadSubScene("../AddAccount/AddAccountView.fxml");
	}
	
	/**
	 * Affiche les erreurs relatives à la base de données (e.g : champs inexistants, incompatibles, etc...)
	 * @param e : PersistenceException
	 */
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
}

package AddUser;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import application.ControllerBase;
import application.MainWindowController;
import application.Mediator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import metier.Owner;
import metier.CpCity;
import metier.DateUtils;

public class AddUserController extends ControllerBase { 
	private EntityManager em;
	Owner owner = new Owner();
	private String newCity;

	@FXML private TextField login;
	@FXML private TextField pwd;
	@FXML private TextField confirm_pwd;
	@FXML private TextField owner_name;
	@FXML private TextField owner_firstname;
	@FXML private TextField owner_mail;
	@FXML private TextField owner_id_address1;
	@FXML private TextField owner_id_address2;
	@FXML private TextField postalcode;
	@FXML private ChoiceBox <String> city;
	@FXML private TextField owner_phonenumber;
	@FXML private DatePicker owner_birthdate;
	@FXML private Button btn_ok;
	@FXML private Button btn_cancel;
	
	@FXML private Label errlogin;
	@FXML private Label errpwd;
	@FXML private Label errconfirmpwd;
	@FXML private Label errname;
	@FXML private Label errfirstname;
	@FXML private Label errmail;
	@FXML private Label erraddress1;
	@FXML private Label erraddress2;
	@FXML private Label errpostalcode;
	@FXML private Label errcity;
	@FXML private Label errphonenumber;
	@FXML private Label errbirthdate;
	
	@Override
	public void initialize(Mediator mediator) {
		
		try {	
			em = mediator.createEntityManager();
			
			List<String> cities = em.createNamedQuery("cpcity.findAllcity", String.class).getResultList();
			cities.add("(new city)");
			this.city.setItems(FXCollections.observableList(cities));
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
		}
	}
	@FXML
	private void newCity (ActionEvent event) {
		ChoiceBox catCity  = (ChoiceBox)event.getTarget();
		if(catCity.getValue() == newCity) {
			System.out.println("new city");
			
			/* rajouter une scene ou 
			 * boite de dialogue
			 * pour ajouter une ville 
			 */
		}
	}
	
	@FXML
	private void handleButtonOk(ActionEvent Event) {
		
		try {
			owner.setLogin(login.getText());
		}
		catch  (IllegalArgumentException e) {
			errlogin.setText(" The login cannot be empty");
		}
		catch (NullPointerException e) {
			errlogin.setText(" The login cannot be null");
		}
		try {
			owner.setPwd(pwd.getText());
		}
		catch  (IllegalArgumentException e) {
			errpwd.setText(" The password cannot be empty");
		}
		catch (NullPointerException e) {
			errpwd.setText(" The password cannot be null");
		}
		try {
			if (pwd.equals(confirm_pwd)) {
			owner.setPwd(confirm_pwd.getText());	
			}	
		}
		catch  (IllegalArgumentException e) {
			errconfirmpwd.setText(" The password cannot be empty");
		}
		try {
			owner.setName(owner_name.getText());
		}
		catch  (IllegalArgumentException e) {
			errname.setText(" The owner name cannot be empty");
		}
		catch  (NullPointerException e) {
			errname.setText(" The owner name cannot be null");
		}
		try {
			owner.setFirstName(owner_firstname.getText());
		}
		catch  (IllegalArgumentException e) {
			errfirstname.setText(" The owner firstname cannot be empty");
		}
		catch  (NullPointerException e) {
			errfirstname.setText(" The owner firstname cannot be null");
		}
		try {
			owner.setEmail(owner_mail.getText());
		}
		catch  (IllegalArgumentException e) {
			errmail.setText(" The owner email cannot be empty");
		}
		try {
			owner.setPhoneNumber(owner_phonenumber.getText());
		}
		catch  (IllegalArgumentException e) {
			errphonenumber.setText(" The owner phonenumber cannot be empty");
		}
		/*
		try {
			owner.setPostalCode(postalcode.getText());
		}
		catch  (IllegalArgumentException e) {
			errpostalcode.setText(" The owner postalcode cannot be emptyand must contains 5 characters");
		}
		try {
			owner.setCity(city.getValue());
		} 
		catch  (IllegalArgumentException e) {
			errcity.setText(" The owner city cannot be empty");
		} */
		try {
			owner.setBirthdate(DateUtils.LocalDateToDate(owner_birthdate.getValue()));
		}
		catch  (IllegalArgumentException e) {
			errbirthdate.setText(" The owner birthdate cannot be empty");
		}
		catch (NullPointerException e) {
			errbirthdate.setText(" The birthdate cannot be null");
		}
		
		em.getTransaction().begin();
		em.persist(owner);
		try{
			em.getTransaction().commit();
			
		}
		catch(Exception e) {
			em.getTransaction().rollback();
			return;
		}
		
		try{ 
			MainWindowController.contentPane.getChildren().setAll(loadFxml("../authentification/AuthentificationView.fxml"));
		}
		catch (IOException e){
		}
		
	}

	@FXML
	private void handleButtonCancel(ActionEvent event) {
		Alert alert = new Alert(
				AlertType.CONFIRMATION,
				"Are you sure you want to cancel ?",
				ButtonType.YES,
				ButtonType.NO
		);
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.isPresent() && result.get() == ButtonType.YES) {
			Platform.exit();
			
		}
	}
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	 
}

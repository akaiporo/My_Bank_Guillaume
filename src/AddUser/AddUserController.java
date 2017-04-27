package AddUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import application.ControllerBase;
import application.MainWindowController;
import application.Mediator;
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
import metier.Address;
import metier.CpCity;
import metier.DateUtils;

public class AddUserController extends ControllerBase { 
	private EntityManager em;
	private Owner owner = new Owner();
	private CpCity cpcity = new CpCity();
	private Address address = new Address();
	List<String> cities = new ArrayList<String>();

	@FXML private TextField login;
	@FXML private TextField pwd;
	@FXML private TextField confirm_pwd;
	@FXML private TextField owner_name;
	@FXML private TextField owner_firstname;
	@FXML private TextField owner_mail;
	@FXML private TextField owner_id_address1;
	@FXML private TextField owner_id_address2;
	@FXML private ChoiceBox <String> postalcode;
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
		
		errlogin.setText("");
		errpwd.setText("");
		errconfirmpwd.setText("");
		errname.setText("");
		errfirstname.setText("");
		errmail.setText("");
		erraddress1.setText("");
		erraddress2.setText("");
		errpostalcode.setText("");
		errcity.setText("");
		errphonenumber.setText("");
		errbirthdate.setText("");
		
		try {	
			em = mediator.createEntityManager();
			
			cities.add("(new city)");
			this.city.setItems(FXCollections.observableList(cities));
			
			List<String> postalcodes = em.createNamedQuery("cpcity.findAllpostalcode", String.class).getResultList();
			postalcodes.add("(new postalcode)");
			this.postalcode.setItems(FXCollections.observableList(postalcodes));
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
		}
	}
	@FXML
	private void newCity (ActionEvent event) {
		ChoiceBox catCity  = (ChoiceBox)event.getTarget();
		String tmp = (String) catCity.getValue();
		if(tmp.equals("(new city)")) {
			System.out.println("new city");
			
			/* rajouter une scene ou 
			 * boite de dialogue
			 * pour ajouter une ville 
			 */
		}
	}
	@FXML 
	private void handlePostalcode (ActionEvent event) {
		
		ChoiceBox catPostalcode  = (ChoiceBox)event.getTarget();
		String tmp = (String)catPostalcode.getValue();
		if(tmp.equals("(new postalcode)")) {
			System.out.println("new postal code");
			
			// loader une page pour entrer un nouveau code postal
		}
		else {
			city.getItems().removeAll(cities);
			Query q = em.createQuery("SELECT c.city FROM CpCity c WHERE c.postalCode = :postalcode",String.class); 
			q.setParameter("postalcode", postalcode.getValue());
			cities = q.getResultList();
			cities.add("(new city)");
			this.city.setItems(FXCollections.observableList(cities));
		}
	}
	
	@FXML
	private void handleButtonOk(ActionEvent Event) {
		
		
		try{
			cpcity.setPostalCode(postalcode.getValue());
		}
		catch(NullPointerException e){
			errpostalcode.setText(e.getMessage());
			return;
		}
		try{
			cpcity.setCity(city.getValue());
		}
		catch(NullPointerException e){
			errcity.setText("Please choose an existing city or add one");
			return;
		}
		
		
		try {
			address.setLine1(owner_id_address1.getText());
		}
		catch(IllegalArgumentException e){
			erraddress1.setText(e.getMessage());
			return;
		}
		address.setLine2(owner_id_address2.getText()); // L'address2 peut être vide
		try {
			address.setCpCity(cpcity);
		}
		catch(NullPointerException e){	
			return;
		}
		
		
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
			owner.setMail(owner_mail.getText());
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
		try {
			owner.setBirthdate(DateUtils.LocalDateToDate(owner_birthdate.getValue()));
		}
		catch  (IllegalArgumentException e) {
			errbirthdate.setText(" The owner birthdate cannot be empty");
		}
		catch (NullPointerException e) {
			errbirthdate.setText(" The birthdate cannot be null");
		}
		try {
			owner.setAddress(address);
		}
		catch(NullPointerException e){	
		}
		
		
		em.getTransaction().begin();
		em.persist(owner);
		em.persist(cpcity);
		em.persist(address);
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
			
			try{ 
				MainWindowController.contentPane.getChildren().setAll(loadFxml("../compteCourant/CompteCourantList.fxml"));
			}
			catch (IOException e){
			}
		}
		
	}
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	 
}

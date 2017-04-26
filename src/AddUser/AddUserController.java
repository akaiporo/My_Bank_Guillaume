package AddUser;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import application.ControllerBase;
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
import metier.DateUtils;

public class AddUserController extends ControllerBase {
	private EntityManager em;
	Owner owner = new Owner();

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
			cities.add(null);
			this.city.setItems(FXCollections.observableList(cities));
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
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
		try {
			owner.setPwd(pwd.getText());
		}
		catch  (IllegalArgumentException e) {
			errpwd.setText(" The password cannot be empty");
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
			owner.setOwnerName(owner_name.getText());
		}
		catch  (IllegalArgumentException e) {
			errname.setText(" The owner name cannot be empty");
		}
		try {
			owner.setOwnerFirstname(owner_firstname.getText());
		}
		catch  (IllegalArgumentException e) {
			errfirstname.setText(" The owner firstname cannot be empty");
		}
		try {
			owner.setOwnerEmail(owner_mail.getText());
		}
		catch  (IllegalArgumentException e) {
			errmail.setText(" The owner email cannot be empty");
		}
		try {
			owner.setOwnerPhonenumber(owner_phonenumber.getText());
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
		
		em.getTransaction().begin();
		em.persist(owner);
		try{
			em.getTransaction().commit();
			
		}
		catch(Exception e) {
			//e.printStackTrace();
			em.getTransaction().rollback();
		}
		
	/*	
		
		CpCity cpcity = new CpCity(postalcode.getText(),this.city.getValue());
		Address address = new Address(owner_id_address1.getText(),owner_id_address2.getText(),cpcity);
		Owner owner = new Owner(owner_name.getText(),
								owner_firstname.getText(),
								owner_phonenumber.getText(),
								owner_mail.getText(),
								DateUtils.LocalDateToDate(owner_birthdate.getValue()),
								login.getText(),
								pwd.getText(),
								address
								);
		
		private boolean saveForm() {
			boolean isNew = this.cur.getId()==0;
			
			Owner owner = this.owner.getItems();
			boolean err=false;

		if(this.login.getText().isEmpty()) {
			this.errlogin.setVisible(true);
			err=true;
		}
		if(this.pwd.getText().isEmpty()) {
			this.errpwd.setVisible(true);
			err=true;
		}
		if(this.owner_name.getText().isEmpty()) {
			this.errname.setVisible(true);
			err=true;
		}
		if(this.owner_firstname.getText().isEmpty()) {
			this.errfirstname.setVisible(true);
			err=true;
		}
		if(this.owner_mail.getText().isEmpty()) {
			this.errmail.setVisible(true);
			err=true;
		}
		if(this.owner_id_address1.getText().isEmpty()) {
			this.erraddress1.setVisible(true);
			err=true;
		}
		if(this.owner_id_address2.getText()==null) {
			this.erraddress2.setVisible(true);
			err=true;
		}
		if(this.postalcode.getText().length() !=5) {
			this.errpostalcode.setVisible(true);
			err=true;
		}
		if(this.city.getValue()==null) {
			this.errcity.setVisible(true);
			err=true;
		}
		if(this.owner_phonenumber.getText().isEmpty()) {
			this.errphonenumber.setVisible(true);
			err=true;
		}
		if(this.owner_birthdate.getValue()==null) {
			this.errbirthdate.setVisible(true);
			err=true;
		}
		if(err) {
			return false;	
		
		}
		this.cur.setLogin(this.login.getText());
		this.cur.setPwd(this.pwd.getText());
		this.cur.setOwnerName(this.owner_name.getText());
		this.cur.setOwnerFirstname(this.owner_firstname.getText());
		this.cur.setOwnerEmail(this.owner_mail.getText());
		this.cur.setaddress1(this.owner_id_address1.getText());
		this.cur.setLine2(this.owner_id_address2.getText());
		this.cur.setPostalCode(this.postalcode.getText());
		this.cur.setcity(this.city.getValue());
		this.cur.setOwnerPhonenumber(this.owner_phonenumber.getText());
		this.cur.setBirthdate(DateUtils.LocalDateToDate(this.owner_birthdate.getValue()));
		
		try {
			EntityManager em = getMediator().createEntityManager();
			EntityTransaction transaction = em.getTransaction();

			try {					
				transaction.begin();
				em.persist(this.cur);
				transaction.commit();
				this.dirty = false;
				if(isNew) {
					owner.add(this.cur);
				}
				else {
					owner.set(owner.indexOf(this.cur), this.cur);
				}
			}
			catch(RollbackException e) {
				return false;
			}
			finally {
				em.close();
			}
			return true;
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
			return false;
		} 
		
		/*em.getTransaction().begin();
		em.persist(cpcity);
		em.persist(address);
		em.persist(owner);
		em.getTransaction().commit();	*/	
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
	/*private boolean updateForm(Owner newOwner) {
		this.resetErrors();
		if(this.dirty) {
			Alert alert = new Alert(AlertType.CONFIRMATION, " Save ?", ButtonType.YES, ButtonType.NO);
			
			alert.showAndWait();
			
			ButtonType result = alert.getResult();
			
			if(result == ButtonType.NO) {
				return false;
			}
		}
	this.cur = newOwner;
	this.login.setText(this.cur.getLogin());
	this.pwd.setText(this.cur.getPwd());
	this.owner_name.setText(this.cur.getName());
	this.owner_firstname.setText(this.cur.getFirstName());
	this.owner_mail.setText(this.cur.getEmail());
	this.address.setText(this.cur.getAddress());
	this.postalcode.setText(this.cur.getPostalCode());
	this.city.setValue(this.city.getCpCity());
	this.owner_phonenumber.setText(this.cur.getPhoneNumber());
	this.owner_birthdate.setValue(DateUtils.DateToLocalDate(this.cur.getBirthdate()));
	this.dirty = false;  
			
	} */
	
	/*private void resetErrors() {
	
		for(Label l : new Label[]{ errlogin, errpwd, errname, errfirstname, errmail,
				erraddress1, erraddress2, errpostalcode, errcity, errphonenumber, errbirthdate}) {
			l.setVisible(false);
		}
	}*/	
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	
	

		 
}

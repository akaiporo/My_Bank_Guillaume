package inscriptionView;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import metier.Address;
import metier.CpCity;
import metier.Owner;
import metier.DateUtils;

public class InscriptionViewController extends ControllerBase {
	
	@FXML private TextField login;
	@FXML private TextField pwd;
	@FXML private TextField confirm_pwd;
	@FXML private TextField owner_name;
	@FXML private TextField owner_firstname;
	@FXML private TextField owner_mail;
	@FXML private TextField owner_id_address1;
	@FXML private TextField owner_id_address2;
	@FXML private ChoiceBox <CpCity> postalcode;
	@FXML private ChoiceBox <CpCity> city;
	@FXML private TextField owner_phonenumber;
	@FXML private DatePicker owner_birthdate;
	@FXML private Button btn_ok;
	@FXML private Button btn_cancel;
	
		
	
	@Override
	public void initialize(Mediator mediator) {
		
		try {	
			EntityManager em = mediator.createEntityManager();
			List<CpCity> postalcodes = em.createNamedQuery("CpCity.findAllpostalcode",CpCity.class).getResultList();
			List<CpCity> cities = em.createNamedQuery("CpCity.findAllcity",CpCity.class).getResultList();
			
			this.postalcode.setItems(FXCollections.observableList(postalcodes));
			this.city.setItems(FXCollections.observableList(cities));
		}
		catch(PersistenceException e) {
		}
		
	}
	
	
	@FXML
	private void handleButtonOk(ActionEvent event) {
	CpCity cpcity = new CpCity(this.postalcode.getValue(),this.city.getValue());
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
		
	}
	@FXML
	private void handleBtnCancel(ActionEvent event) {
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
		 
}

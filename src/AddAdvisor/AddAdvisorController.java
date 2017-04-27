package AddAdvisor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import metier.Advisor;
import metier.Agency;
import metier.DateUtils;

public class AddAdvisorController extends ControllerBase {
	private EntityManager em;
	private Agency newAgency= new Agency();
	private Advisor currentAdvisor=new Advisor();
	
	@FXML private TextField advisor_name;
	@FXML private TextField advisor_firstname;
	@FXML private TextField advisor_phonenumber;
	@FXML private TextField advisor_email;
	@FXML private Button OK;
	@FXML private Button cancel;
	@FXML private ChoiceBox<Agency> choiceAgency;
	@FXML private DatePicker date_assignment;
	@FXML private Label advisor_error;
	
	@Override
	public void initialize(Mediator mediator) {
		advisor_error.setText("");
		try{
			em = mediator.createEntityManager();
		
			List<Agency> agencies = em.createNamedQuery("Agency.findAll", Agency.class).getResultList();
			newAgency.setAgencyName("(new agency)");
			agencies.add(newAgency);
			this.choiceAgency.setItems(FXCollections.observableList(agencies));
			
		}
		catch(PersistenceException e){
			this.processPersistenceException(e);
		}
	}
	
	
	@FXML
	private void addAgency (ActionEvent event){
		ChoiceBox catAgency = (ChoiceBox)event.getTarget();
		Agency tmp=(Agency)catAgency.getValue();
		if (tmp.getAgencyName().equals("(new agency)")){
			this.loadSubScene("../AddAgency/AddAgencyView.fxml");
		}
	}
	
	@FXML 
	private void handleButtonOK (ActionEvent event){
		try{
			currentAdvisor.setName(advisor_name.getText());
		}
		catch(IllegalArgumentException e){
			advisor_error.setText(e.getMessage());
		}
		try{
			currentAdvisor.setFirstName(advisor_firstname.getText());
		}
		catch(IllegalArgumentException e){
			advisor_error.setText(e.getMessage());
		}
		try{
			currentAdvisor.setPhoneNumber(advisor_phonenumber.getText());
		}
		catch(IllegalArgumentException e){
			advisor_error.setText(e.getMessage());
		}		
		try {
			currentAdvisor.setMail(advisor_email.getText());
		}
		catch  (IllegalArgumentException e) {
			advisor_error.setText(e.getMessage());
		}
		try {
			currentAdvisor.setDateAssignment(DateUtils.LocalDateToDate(date_assignment.getValue()));
		}
		catch  (IllegalArgumentException e) {
			advisor_error.setText(e.getMessage());
		}
		catch (NullPointerException e) {
			advisor_error.setText(e.getMessage());
		}
		try{
			currentAdvisor.setAgency(choiceAgency.getValue());
		}
		catch(NullPointerException e){
			advisor_error.setText("Please choose an agency or add a new one");
		}
		
		em.getTransaction().begin();
		em.persist(currentAdvisor);
		try{
			em.getTransaction().commit();
		}
		catch(Exception e){
			em.getTransaction().rollback();
			return;
		}
		
		this.loadSubScene("../AddAccount/AddAccountView.fxml");
	}
	
	@FXML 
	private void handleButtonCancel (ActionEvent event){
		this.loadSubScene("../AddAccount/AddAccountView.fxml");
	}
	
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}



}

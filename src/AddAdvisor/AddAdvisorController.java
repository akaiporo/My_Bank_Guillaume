package AddAdvisor;

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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import metier.Advisor;
import metier.Agency;

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
	
	@Override
	public void initialize(Mediator mediator) {
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
			try{
				MainWindowController.contentPane.getChildren().setAll(loadFxml("../AddAgency/AddAgencyView.fxml"));
			}
			catch(IOException e){	
				System.out.println(e.getMessage());
			}
		}
	}
	
	@FXML 
	private void handleButtonOK (ActionEvent event){
		try{
			MainWindowController.contentPane.getChildren().setAll(loadFxml("../AddAccount/AddAccountView.fxml"));
		}
		catch(IOException e){	
			System.out.println(e.getMessage());
		}
	}
	
	@FXML 
	private void handleButtonCancel (ActionEvent event){
		try{
			MainWindowController.contentPane.getChildren().setAll(loadFxml("../AddAccount/AddAccountView.fxml"));
		}
		catch(IOException e){	
			System.out.println(e.getMessage());
		}
	}
	
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}



}

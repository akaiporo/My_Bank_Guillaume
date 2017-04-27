package AddCpCity;

import javax.persistence.EntityManager;

import application.ControllerBase;
import application.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import metier.CpCity;

public class AddCpCityController extends ControllerBase {
	private EntityManager em;
	CpCity currentCpCity=new CpCity();
	
	@FXML private TextField postal_code;
	@FXML private TextField city;
	@FXML private Label cpcity_error;
	@FXML private Button OK;
	@FXML private Button cancel;
	
	@Override
	public void initialize(Mediator mediator) {
		em = mediator.createEntityManager();
		cpcity_error.setText("");
	}
	
	@FXML
	private void handleButtonOK (ActionEvent event){
		try{
			currentCpCity.setPostalCode(postal_code.getText());
		}
		catch (IllegalArgumentException e){
			cpcity_error.setText(e.getMessage());
		}
		try{
			currentCpCity.setCity(city.getText());
		}
		catch (IllegalArgumentException e){
			cpcity_error.setText(e.getMessage());
		}
		
		em.getTransaction().begin();
		em.persist(currentCpCity);
		try{
			em.getTransaction().commit();
		}
		catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		this.loadSubScene("../AddAgency/AddAgencyView.fxml");		
	}
	
	@FXML
	private void handleButtonCancel (ActionEvent event){
		this.loadSubScene("../AddAgency/AddAgencyView.fxml");
	}
}

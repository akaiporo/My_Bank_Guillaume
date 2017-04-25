package connexion;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import application.ControllerBase;
import application.Mediator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import metier.Account;

public class ConnexionController extends ControllerBase {
	private EntityManager em;
	Account account = new Account();
	
	@FXML private TextField login;
	@FXML private TextField pwd;
	@FXML private Button btn_connexion;
	@FXML private Button btn_forgottenpwd;
	@FXML private Button btn_inscription;

	@Override
	public void initialize(Mediator mediator) {
		
		try {	
			em = mediator.createEntityManager();
			
			List<String> cities = em.createNamedQuery("cpcity.findAllcity", String.class).getResultList();
			cities.add(null);
			this.city.setItems(FXCollections.observableList(cities));
		}
		catch(PersistenceException e) {
			this.btn_ok.setDisable(true);
			this.processPersistenceException(e);
		}
	}

}

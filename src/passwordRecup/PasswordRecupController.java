package passwordRecup;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import application.ControllerBase;
import application.Mediator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class PasswordRecupController extends ControllerBase {
	private EntityManager em;
	
	@FXML private TextField recup_email;
	@FXML private Button btn_ok;
	@FXML private Button btn_cancel;
	
	@Override
	public void initialize(Mediator mediator) {  
		
		try {
			em = mediator.createEntityManager();
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
		}
		
	}
	
	@FXML
	private void handleButtonOk(ActionEvent event) {
		
		/* Dans l'idéal il faudrait envoyer un lien pour la création d'un nouveau mot de passe, 
		 * donc créer une scene pour la saisie et la confirmation du nouveau mot de passe
		 */
		
		String inputmail = this.recup_email.getText();
		Query q = em.createQuery("SELECT m FROM Owner m WHERE m.mail = : inputmail "); 
		q.setParameter("mail", inputmail);
		List ml = q.getResultList();
		
		if(recup_email.equals(ml)) {
			
			Query u = em.createQuery("SELECT l FROM Owner l WHERE l.login = : owner ");
			Query e = em.createQuery("SELECT p FROM Owner p WHERE p.pwd = : owner "); 
			
			/* Il faut stocker les résultats des reqêtes 
			 * puis les  envoyer par email le login et le mot de passe 
			 * correspondant au owner dont l'adresse email a été saisie
			 */
		}
		else {
			System.out.print(" This email does not exist! ");
		}
		
		em.getTransaction().begin();
		em.persist(recup_email);
		try {
			em.getTransaction().commit();
		}
		catch(Exception e) {
			em.getTransaction().rollback();
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

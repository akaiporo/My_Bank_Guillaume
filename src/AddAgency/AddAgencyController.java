package AddAgency;

import java.util.List;

import javax.persistence.EntityManager;

import application.ControllerBase;
import application.Mediator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import metier.Agency;
import metier.Bank;

public class AddAgencyController extends ControllerBase {
	
	private EntityManager em;
	private Bank newBank= new Bank();
	
	@FXML private TextField agency_name;
	@FXML private TextField counter_code;
	@FXML private TextField address_line1;
	@FXML private TextField address_line2;
	
	<TextField id="agency_name" fx:id="agency_name" layoutX="49.0" layoutY="68.0" prefHeight="25.0" prefWidth="232.0" AnchorPane.leftAnchor="49.0" AnchorPane.topAnchor="68.0" />
    <TextField id="counter_code" fx:id="counter_code" layoutX="50.0" layoutY="132.0" prefHeight="25.0" prefWidth="60.0" AnchorPane.leftAnchor="50.0" AnchorPane.topAnchor="132.0" />
    <TextField id="address_line1" fx:id="address_line1" layoutX="95.0" layoutY="196.0" prefHeight="25.0" prefWidth="232.0" />
    <TextField id="new_city" fx:id="new_city" layoutX="88.0" layoutY="341.0" prefHeight="25.0" prefWidth="216.0" />
    <TextField id="postal_code" fx:id="postal_code" layoutX="129.0" layoutY="269.0" prefHeight="17.0" prefWidth="60.0" />
    <TextField id="address_line2" fx:id="address_line2"
	public void initialize(Mediator mediator){
		em = mediator.createEntityManager();
		
		List<Agency> agencies = em.createNamedQuery("Agency.findAll", Agency.class).getResultList();
		newBank.setBankName("(new bank)");
		agencies.add(newBank); //permettra d'ajouter une nouvelle agence
		this.choiceBank.setItems(FXCollections.observableList(agencies));
	}
	

}

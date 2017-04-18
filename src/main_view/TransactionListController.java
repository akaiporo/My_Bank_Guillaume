package main_view;

import java.util.List;


import javax.persistence.EntityManager;

import application.ControllerBase;
import application.Mediator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import metier.Category;
import metier.PeriodicTransaction;

public class TransactionListController extends ControllerBase {

	@FXML private TableView<PeriodicTransaction> listTasks;
	@FXML private CheckBox chkDone;
	@FXML private TextField txtLabel;
	@FXML private DatePicker dateCreated;
	@FXML private ChoiceBox<Category> choiceCategory;
	@FXML private Button btnApply;

	@Override
	public void initialize(Mediator mediator) {
		EntityManager em = mediator.createEntityManager();
		List<PeriodicTransaction> tasks = em.createNamedQuery("PeriodicTransaction.findAll").getResultList();
		
		// Remplissage du tableview avec tasks
		this.listTasks.setItems(FXCollections.observableList(tasks));
	}
	
	@FXML
	private void handleBtnApply(ActionEvent event) {

	}
	@FXML
	private void handleBtnNew(ActionEvent event) {

	}

}
package main_view;

import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import metier.Category;
import metier.PeriodicTransaction;
import metier.TargetTransaction;
import metier.TransactionType;

public class TransactionListController extends ControllerBase {

	@FXML private TableView<PeriodicTransaction> listTransactions;
	@FXML private CheckBox chkDone;
	@FXML private TextField txtLabel;
	@FXML private TextField txtDescription;
	@FXML private TextField txtValeur;
	@FXML private DatePicker dateCreated;
	@FXML private ChoiceBox<Category> choiceCategory;
	@FXML private ChoiceBox<TargetTransaction> choiceTarget;
	@FXML private ChoiceBox<TransactionType> choiceType;
	@FXML private Button btnApply;
		  private EntityManager em;

	@Override
	public void initialize(Mediator mediator) {
		em = mediator.createEntityManager();

		//Initialisations des listes & combox box
		@SuppressWarnings("unchecked")
		List<PeriodicTransaction> Transactions = em.createNamedQuery("PeriodicTransaction.findAll").getResultList();
		this.listTransactions.setItems(FXCollections.observableList(Transactions));
		
		@SuppressWarnings("unchecked")
		List<Category> categories = em.createNamedQuery("Category.findAllName").getResultList();
		this.choiceCategory.setItems(FXCollections.observableList(categories));
		
		@SuppressWarnings("unchecked")
		List<TargetTransaction> targets = em.createNamedQuery("TargetTransaction.findAllName").getResultList();
		this.choiceTarget.setItems(FXCollections.observableList(targets));
		
		@SuppressWarnings("unchecked")
		List<TransactionType> transactionType = em.createNamedQuery("TransactionType.findAllName").getResultList();
		this.choiceType.setItems(FXCollections.observableList(transactionType));
	}
	
	@FXML
	private void handleBtnNew(ActionEvent event) {
		Date date = Date.from(this.dateCreated.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		PeriodicTransaction transaction = new PeriodicTransaction(this.txtLabel.getText(), Double.parseDouble(this.txtValeur.getText()), 
											date,null, 0,this.txtDescription.getText(), 
											this.choiceType.getValue(), this.choiceTarget.getValue(), 
											this.choiceCategory.getValue(), null);
		
		em.getTransaction().begin();
		em.persist(transaction);
		
		try{
			em.getTransaction().commit();
		}
		catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
	}
}

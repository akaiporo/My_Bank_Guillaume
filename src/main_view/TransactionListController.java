package main_view;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;

import application.ControllerBase;
import application.Mediator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
	@FXML private Button btnDelete;
	
	//non FXML var
		  private EntityManager em;
		  private List<PeriodicTransaction> Transactions;
		  private List<Category> categories;
		  private List<TargetTransaction> targets;
		  private List<TransactionType> transactionType;
		  private boolean modified = false;
		  private PeriodicTransaction currentTransaction;
		  
	@Override
	public void initialize(Mediator mediator) {
		em = mediator.createEntityManager();
		
		//Initialisations des listes & combox box
		this.Transactions = em.createNamedQuery("PeriodicTransaction.findAll").getResultList();
		this.listTransactions.setItems(FXCollections.observableList(Transactions));
		
		this.categories = em.createNamedQuery("Category.findAll").getResultList();
		this.choiceCategory.setItems(FXCollections.observableList(categories));
		
		this.targets = em.createNamedQuery("TargetTransaction.findAll").getResultList();
		this.choiceTarget.setItems(FXCollections.observableList(targets));
		
		this.transactionType = em.createNamedQuery("TransactionType.findAll").getResultList();
		this.choiceType.setItems(FXCollections.observableList(transactionType));
		
		this.listTransactions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PeriodicTransaction>() {
			@Override 
			public void changed(ObservableValue<? extends PeriodicTransaction> arg0, PeriodicTransaction oldVal, PeriodicTransaction newVal) {
				updateForm(newVal==null ? new PeriodicTransaction() : newVal); 
				//Si newVal == null, on crée une nouvelle transaction vide,
				//Si non, on lui passe newVal
			}
		});
	}
	
	private boolean updateForm(PeriodicTransaction newTransaction) {
		this.currentTransaction = newTransaction;
		this.txtLabel.setText(this.currentTransaction.getWording());
		this.txtDescription.setText(this.currentTransaction.getDescription());
		this.txtValeur.setText(Double.toString(this.currentTransaction.getTransactionValue()));
		this.dateCreated.setValue(this.currentTransaction.getDateOperation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		this.choiceCategory.setValue(this.currentTransaction.getCategory());
		this.choiceType.setValue(this.currentTransaction.getTransactionType());
		this.choiceTarget.setValue(this.currentTransaction.getTargetTransaction());
		this.btnApply.setDisable(true);
		this.modified = false;
		return true;
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
			this.refreshTransaction(transaction);
		}
		catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}
	
	private void refreshTransaction(PeriodicTransaction transaction){
		this.Transactions.add(transaction);
		this.listTransactions.setItems(FXCollections.observableList(Transactions));
	}
}
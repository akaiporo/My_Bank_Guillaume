package main_view;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import application.ControllerBase;
import application.Mediator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	@FXML private Button btnEdit;
	@FXML private Button btnDelete;
	@FXML private Label errDate;
	@FXML private Label errLibele;
	@FXML private Label errType;
	@FXML private Label errTarget;
	@FXML private Label errCategory;
	@FXML private Label errValue;

	
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
		this.btnEdit.setDisable(false);
		
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
	public void saveForm() {
		Alert alert  = new Alert(AlertType.CONFIRMATION, "La tâche est modifiée. Enregistrer les modifications ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		
		alert.showAndWait();
		
		ButtonType result = alert.getResult();
		
		if(result == ButtonType.CANCEL) {
			return;			
		}
		else if(result == ButtonType.YES){
			boolean isNew = this.currentTransaction.getId()==0;
			System.out.print("mdfdgs fdfd gfd ff fd  s dg s  sdr");
			ObservableList<PeriodicTransaction> transactions = this.listTransactions.getItems();
			boolean err=false;
			
			if(this.dateCreated.getValue()==null) {
				this.errDate.setVisible(true);
				err=true;
			}
			if(this.txtLabel.getText().isEmpty()) {
				this.errLibele.setVisible(true);
				err=true;
			}
			if(this.txtValeur.getText().isEmpty()) {
				this.errValue.setVisible(true);
				err=true;
			}
			if(this.choiceType.getValue()==null) {
				this.errType.setVisible(true);
				err=true;
			}
			if(this.choiceTarget.getValue()==null) {
				this.errTarget.setVisible(true);
				err=true;
			}	
			if(this.choiceCategory.getValue()==null) {
				this.errCategory.setVisible(true);
				err=true;
			}	
			if(err) {
				return;
			}
			this.currentTransaction.setDateOperation(Date.from(this.dateCreated.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			Date dateOP = this.currentTransaction.getDateOperation();
			this.currentTransaction.setWording(this.txtLabel.getText());
			String wording = this.currentTransaction.getWording();
			this.currentTransaction.setCategory(this.choiceCategory.getValue());
			Category cat = this.currentTransaction.getCategory();
			this.currentTransaction.setTransactionType(this.choiceType.getValue());
			TransactionType type = this.currentTransaction.getTransactionType();
			this.currentTransaction.setTargetTransaction(this.choiceTarget.getValue());
			TargetTransaction target = this.currentTransaction.getTargetTransaction();
			this.currentTransaction.setTransactionValue(Double.parseDouble(this.txtValeur.getText()));
			double val = this.currentTransaction.getTransactionValue();
	
			try {
				EntityManager em = getMediator().createEntityManager();
				PeriodicTransaction periodicTransaction = em.find(PeriodicTransaction.class, this.currentTransaction.getId());
				try {					
					em.getTransaction().begin();
			
					periodicTransaction.setCategory(cat);
					periodicTransaction.setDateOperation(dateOP);
					periodicTransaction.setWording(wording);
					periodicTransaction.setTransactionType(type);
					periodicTransaction.setTargetTransaction(target);
					periodicTransaction.setTransactionValue(val);
				
					this.modified = false;
					em.getTransaction().commit();
					if(isNew) {
						this.refreshTransaction(this.currentTransaction);
					}
					else {
						transactions.set(transactions.indexOf(this.currentTransaction), this.currentTransaction);
					}
					
				}
				catch(RollbackException e) {
					return;
				}
				finally {
					em.close();
				}
			}
			catch(PersistenceException e) {
				this.processPersistenceException(e);
				return;
			}
		}
	}
	
	@FXML
	private void handleBtnNew(ActionEvent event) {
		PeriodicTransaction transaction = new PeriodicTransaction();
		transaction.setDescription(this.txtDescription.getText());
		transaction.setDayNumber(0);
		transaction.setEndDateTransaction(null);
		transaction.setPeriodUnit(null);
		
		try{
			double value = Double.parseDouble(this.txtValeur.getText());
			transaction.setTransactionValue(value);
			this.errValue.setVisible(false);
		}
		catch(Exception e){
			this.errValue.setVisible(true);
			return;
		}
		try{
			transaction.setWording(this.txtLabel.getText());
			this.errLibele.setVisible(false);
		}
		catch(Exception e){
			this.errLibele.setVisible(true);
			return;
		}
		try{
			Date date = Date.from(this.dateCreated.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			transaction.setDateOperation(date);
			this.errDate.setVisible(false);
		}
		catch(Exception e){
			this.errDate.setVisible(true);
			return;
		}
		try{
			transaction.setCategory(this.choiceCategory.getValue());
			this.errCategory.setVisible(false);
		}
		catch(Exception e){
			this.errCategory.setVisible(true);
			return;
		}
		try{
			transaction.setTransactionType(this.choiceType.getValue());
			this.errType.setVisible(false);
		}
		catch(Exception e){
			this.errType.setVisible(true);
			return;
		}
		try{
			transaction.setTargetTransaction(this.choiceTarget.getValue());
			this.errTarget.setVisible(false);
		}
		catch(Exception e){
			this.errTarget.setVisible(true);
			return;
		}
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
	
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	

}
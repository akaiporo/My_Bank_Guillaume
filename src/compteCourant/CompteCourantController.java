package compteCourant;

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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert.AlertType;
import metier.Account;
import metier.Category;
import metier.PeriodicTransaction;
import metier.TargetTransaction;
import metier.TransactionType;

public class CompteCourantController extends ControllerBase {

	@FXML private SplitPane splitPane;
	@FXML private TableView<PeriodicTransaction> listTransactions;
	@FXML private CheckBox chkDone;
	@FXML private TextField txtLabel;
	@FXML private TextField txtDescription;
	@FXML private TextField txtValeur;
	@FXML private DatePicker dateCreated;
	@FXML private ChoiceBox<Category> choiceCategory;
	@FXML private ChoiceBox<TargetTransaction> choiceTarget;
	@FXML private ChoiceBox<TransactionType> choiceType;
	@FXML private ChoiceBox<Account> choiceAccount;
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
		  private List<Account> accounts;
		  private PeriodicTransaction currentTransaction;
		  
	@Override
	public void initialize(Mediator mediator) {
		em = mediator.createEntityManager();
		this.accounts = em.createNamedQuery("Account.findAll").getResultList();
		this.choiceAccount.setItems(FXCollections.observableList(accounts));
		this.choiceAccount.getSelectionModel().selectFirst();
		
		Account tmp = this.choiceAccount.getSelectionModel().getSelectedItem();
		Account account = em.find(Account.class, tmp.getId());
		this.initTransactionList(account);
	
		this.categories = em.createNamedQuery("Category.findAll").getResultList();
		this.choiceCategory.setItems(FXCollections.observableList(categories));
		
		this.targets = em.createNamedQuery("TargetTransaction.findAll").getResultList();
		this.choiceTarget.setItems(FXCollections.observableList(targets));
		
		this.transactionType = em.createNamedQuery("TransactionType.findAll").getResultList();
		this.choiceType.setItems(FXCollections.observableList(transactionType));
		
		this.listTransactions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PeriodicTransaction>() {
			@Override 
			public void changed(ObservableValue<? extends PeriodicTransaction> arg0, PeriodicTransaction oldVal, PeriodicTransaction newVal) {
				updateForm(newVal); 
				//Si newVal == null, on crée une nouvelle transaction vide,
				//Si non, on lui passe newVal
			}
		});
		
	}
	
	private void initTransactionList(Account account){
		this.Transactions = account.getTransactions();
		this.listTransactions.setItems(FXCollections.observableList(Transactions));
	}
	
	private void setTransactionList(Account account){
		if(Transactions != null){
			this.listTransactions.getItems().removeAll(Transactions);
		}
		this.Transactions = account.getTransactions();
		this.listTransactions.setItems(FXCollections.observableList(Transactions));
	}
	
	@FXML
	public void handleAccount(ActionEvent event){
		em = this.getMediator().createEntityManager();
		ChoiceBox choiceAccount = (ChoiceBox)event.getTarget();
		Account account = (Account)choiceAccount.getValue();
		account = em.find(Account.class, account.getId());
		this.setTransactionList(account);
	}
	
	private boolean updateForm(PeriodicTransaction newTransaction) {
		System.out.println(newTransaction);
		this.btnEdit.setDisable(false);
		this.btnDelete.setDisable(false);
		
		this.currentTransaction = newTransaction;
		try{
			this.txtLabel.setText(this.currentTransaction.getWording());
			this.txtDescription.setText(this.currentTransaction.getDescription());
			this.txtValeur.setText(Double.toString(this.currentTransaction.getTransactionValue()));
			this.dateCreated.setValue(this.currentTransaction.getDateOperation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			this.choiceCategory.setValue(this.currentTransaction.getCategory());
			this.choiceType.setValue(this.currentTransaction.getTransactionType());
			this.choiceTarget.setValue(this.currentTransaction.getTargetTransaction());
			return true;
		}
		catch(Exception e){
			return false;
		}
		

	}
	@FXML
	public void saveForm() {
		Alert alert  = new Alert(AlertType.CONFIRMATION, "La tâche est modifiée. Enregistrer les modifications ?", ButtonType.YES, ButtonType.CANCEL);
		
		alert.showAndWait();
		
		ButtonType result = alert.getResult();
		
		if(result == ButtonType.CANCEL) {
			return;			
		}
		else if(result == ButtonType.YES){
			boolean isNew = this.currentTransaction.getId()==0;
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
				em.getTransaction().begin();
				try {					
				
					periodicTransaction.setCategory(cat);
					periodicTransaction.setDateOperation(dateOP);
					periodicTransaction.setWording(wording);
					periodicTransaction.setTransactionType(type);
					periodicTransaction.setTargetTransaction(target);
					periodicTransaction.setTransactionValue(val);
				
					
					em.getTransaction().commit();
					this.refreshTransaction();	
				}
				catch(RollbackException e) {
					em.getTransaction().rollback();
					return;
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
		em.persist(transaction);
		
		for(PeriodicTransaction pt : this.Transactions){
			if(pt.equals(transaction)){
				Alert alert  = new Alert(AlertType.CONFIRMATION, "La tâche existe déjà. Voulez-vous tout de même l'ajouter ?", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				ButtonType result = alert.getResult();
				if(result == ButtonType.NO) {
					return;			
				}
			}
		}
		try{
			em.getTransaction().begin();
			em.getTransaction().commit();
			this.refreshTransaction(transaction);
		}
		catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		this.refreshTransaction();
	}
	
	@FXML 
	public void deleteForm(){
		PeriodicTransaction periodicTransaction = new PeriodicTransaction();
		try{
			em.getTransaction().begin();
			periodicTransaction = em.find(PeriodicTransaction.class, this.currentTransaction.getId());
			em.remove(periodicTransaction);
			em.getTransaction().commit();
		}
		catch(Exception e){
			return;
		}
		
		
		this.removeTransaction(periodicTransaction);
	}
	
	private void refreshTransaction(PeriodicTransaction transaction){
		this.Transactions.add(transaction);
		this.listTransactions.setItems(FXCollections.observableList(Transactions));
	}
	//Refresh a vue, la méthode "setVisible" permettant de trigger un change event (et donc de refresh la vue)
	//A optimiser
	private void refreshTransaction(){
		this.listTransactions.getColumns().get(0).setVisible(false);
		this.listTransactions.getColumns().get(0).setVisible(true);
	}
	private void removeTransaction(PeriodicTransaction transaction){
		int index = 0;
		for(PeriodicTransaction pt : Transactions){
			if(pt.getId() == transaction.getId()){
				this.listTransactions.getItems().remove(index);
				return;
			}
			else index++;
				
		}
	}
	
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	
}

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
import application.Main;
import application.MainWindowController;
import application.Mediator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import metier.PeriodUnit;
import metier.PeriodicTransaction;
import metier.TargetTransaction;
import metier.TransactionType;

public class CompteCourantController extends ControllerBase {

	@FXML private SplitPane splitPane;
	@FXML private TableView<PeriodicTransaction> listTransactions;
	@FXML private CheckBox chkCycle;
	@FXML private TextField txtLabel;
	@FXML private TextField txtDescription;
	@FXML private TextField txtValeur;
	@FXML private TextField txtCycle;
	@FXML private DatePicker dateCreated;
	@FXML private DatePicker dateCycle;
	@FXML private ChoiceBox<Category> choiceCategory;
	@FXML private ChoiceBox<TargetTransaction> choiceTarget;
	@FXML private ChoiceBox<TransactionType> choiceType;
	@FXML private ChoiceBox<Account> choiceAccount;
	@FXML private ChoiceBox<PeriodUnit> choiceCycle;
	@FXML private Button btnApply;
	@FXML private Button btnEdit;
	@FXML private Button btnDelete;  
	@FXML private Label errDate;
	@FXML private Label errLibele;
	@FXML private Label errType;
	@FXML private Label errTarget;
	@FXML private Label errCategory;
	@FXML private Label errValue;
	@FXML private Label labelCycleValue;
	@FXML private Label labelCycleEnd;
	@FXML private Label labelCycleType;

	
	//non FXML var
		  private EntityManager em;
		  private List<PeriodicTransaction> Transactions;
		  private List<Category> categories;
		  private List<TargetTransaction> targets;
		  private List<TransactionType> transactionType;
		  private List<Account> accounts;
		  private List<PeriodUnit> periodUnits;
		  private Account currentAccount;
		  private PeriodicTransaction currentTransaction;
		  
	@Override
	/**
	 * Initialize la plupart des listes (ChoiceBoxes et TableView)
	 */
	public void initialize(Mediator mediator) {
		em = mediator.createEntityManager();
		this.accounts = em.createNamedQuery("Account.findAll").getResultList();
		this.choiceAccount.setItems(FXCollections.observableList(accounts));
		this.choiceAccount.getSelectionModel().selectFirst();
		
		Account tmp = this.choiceAccount.getSelectionModel().getSelectedItem();
		Account account = em.find(Account.class, tmp.getId());
		this.setTransactionList(account);
	
		this.categories = em.createNamedQuery("Category.findAll").getResultList();
		this.choiceCategory.setItems(FXCollections.observableList(categories));
		
		this.targets = em.createNamedQuery("TargetTransaction.findAll").getResultList();
		this.choiceTarget.setItems(FXCollections.observableList(targets));
		
		this.transactionType = em.createNamedQuery("TransactionType.findAll").getResultList();
		this.choiceType.setItems(FXCollections.observableList(transactionType));
		
		this.periodUnits = em.createNamedQuery("PeriodUnit.findAll").getResultList();
		this.choiceCycle.setItems(FXCollections.observableList(periodUnits));
		
		this.chkCycle.setSelected(false);
		this.initCycleOptionsVisibility(false);
		
		this.listTransactions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PeriodicTransaction>() {
			@Override 
			//Pour une raison inconnue, le "changed" ne se trigger pas si le Libelé et la valeur sont les mêmes 
			//(quand bien même les autres champs sont différents)
			public void changed(ObservableValue<? extends PeriodicTransaction> arg0, PeriodicTransaction oldVal, PeriodicTransaction newVal) {
				updateForm(newVal); 
			}
		});
		
	}
	

	
	/**
	 * Crée la liste des transactions lors d'un changement de compte
	 * @param account :  compte sélectionner dans la choiceBox
	 */
	private void setTransactionList(Account account){
		//Si c'est la première fois qu'on lance l'apply, la liste est vide
		//On récupère donc la liste de transaction générées avec la création du compte passer en paramètre
		if(Transactions == null){
			this.Transactions = account.getTransactions();

		}
		//Si non, on clear la liste et on la recrée avec le nouveau compte. 
		else{
			this.listTransactions.getItems().removeAll(Transactions);
			Query q =  em.createQuery("SELECT pt FROM PeriodicTransaction pt WHERE pt.account = :account");
			q.setParameter("account",account);
			this.Transactions = q.getResultList();
		}
		this.listTransactions.setItems(FXCollections.observableList(Transactions));
	}
	 
	@FXML
	/**
	 * Récupère le compte sélectionné dans la liste, puis set crée la listes de transactions
	 * @param event : event trigger par l'utilisateur
	 */
	public void handleAccount(ActionEvent event){
		em = this.getMediator().createEntityManager();
		ChoiceBox choiceAccount = (ChoiceBox)event.getTarget();
		this.currentAccount = (Account)choiceAccount.getValue();
		this.currentAccount = em.find(Account.class, currentAccount.getId());
		this.setTransactionList(currentAccount);
	}
	/**
	 * Récupère les données de la ligne sélectionnée, et remplie le formulaire avec
	 * @param newTransaction transaction actuellement sélectionnée par l'utilisateur
	 * @return true si la récupération des données s'est bien passées
	 */
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
			if(this.currentTransaction.getPeriodUnit() != null || this.currentTransaction.getDayNumber() != 0){
				this.chkCycle.setSelected(true); 
				this.initCycleOptionsVisibility(true);
				this.choiceCycle.setValue(this.currentTransaction.getPeriodUnit());
				this.txtCycle.setText(Integer.toString(this.currentTransaction.getDayNumber()));
				if(this.currentTransaction.getEndDateTransaction() != null){
					this.dateCycle.setValue(this.currentTransaction.getEndDateTransaction().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				}
			}
			else {
				this.chkCycle.setSelected(false);
				this.initCycleOptionsVisibility(false);
				this.choiceCycle.setValue(null);
				this.txtCycle.setText("0");
				this.dateCycle.setValue(null);
			}
			return true;
		}
		catch(Exception e){
			return false;
		}
		

	}
	/**
	 * Sauvegarde les données du formulaire lors d'une édition (fait suite à updateForm)
	 */
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
	/**
	 * Affiche ou non les champs du formulaire relatifs à la périodicité d'une transaction
	 * @param visibility : true ou false
	 */
	private void initCycleOptionsVisibility(boolean visibility){
		this.choiceCycle.setVisible(visibility);
		this.txtCycle.setVisible(visibility);
		this.dateCycle.setVisible(visibility);
		this.labelCycleEnd.setVisible(visibility);
		this.labelCycleValue.setVisible(visibility);
		this.labelCycleType.setVisible(visibility);
	}
	
	/**
	 * Récupère le chkBox, et initialise la visibilité en fonction
	 * @param event : event trigger par l'utilisateur
	 */
	@FXML
	private void showCycleOptions(ActionEvent event){
			if(this.chkCycle.isSelected()){
				this.initCycleOptionsVisibility(true);
			}
			else{
				this.initCycleOptionsVisibility(false);
			}
	}
	
	/**
	 * Au click sur le bouton, essaie de créer une nouvelle transaction, de l'ajouter à la liste et de le push en BDD
	 * @param event : event trigger par l'utilisateur
	 */
	@FXML
	private void handleBtnNew(ActionEvent event) {
		PeriodicTransaction transaction = new PeriodicTransaction();
		transaction.setAccount(this.currentAccount);
		transaction.setDescription(this.txtDescription.getText());
		transaction.setPeriodUnit(this.choiceCycle.getValue());
		
		/**
		 * DEBUT Tests et gestions des erreurs
		 */
		try{
			int value = Integer.parseInt(this.txtCycle.getText());
			transaction.setDayNumber(value);
			this.errValue.setVisible(false);
		}
		catch(Exception e){
			return;
		}
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
			Date date = (Date.from(this.dateCycle.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			transaction.setEndDateTransaction(date);
			this.errDate.setVisible(false);
		}
		catch(Exception e){
			this.errDate.setVisible(true);
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
		/**
		 * FIN Tests et gestions des erreurs
		 */
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
	
	/**
	 * Supprime une ligne de la base de données
	 */
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
	/**
	 * rafraichi la liste des transactions dans le cas d'un ajout de transaction
	 * @param transaction : transaction ajoutée précédemment
	 */
	private void refreshTransaction(PeriodicTransaction transaction){
		this.Transactions.add(transaction);
		this.listTransactions.setItems(FXCollections.observableList(Transactions));
	}
	/**
	 * Rafraichi la vue dans le cas d'une suppression/édition
	 */
	private void refreshTransaction(){
		this.listTransactions.getColumns().get(0).setVisible(false);
		this.listTransactions.getColumns().get(0).setVisible(true);
	}
	/**
	 * Supprime une transaction de la liste
	 * @param transaction : transaction à supprimer
	 */
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
	/**
	 * Affiche les erreurs relatives à la base de données (e.g : champs inexistants, incompatibles, etc...)
	 * @param e : PersistenceException
	 */
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	
}

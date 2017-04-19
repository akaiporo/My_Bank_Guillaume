package metier;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="periodictransaction")
@NamedQuery(name="PeriodicTransaction.findAll", query="SELECT t FROM PeriodicTransaction t")
public class PeriodicTransaction {

	/* VARIABLES */
	private int id;
	private String wording;
	private Double transaction_value;
	private Date date_operation;
	private Date end_date_transaction;
	private int day_number;
	private String description;
	private TransactionType transactionType;
	private TargetTransaction targetTransaction;
	private Category category;
	private PeriodUnit periodUnit;
	/**
	 * 
	 * @param wording			   : Libel� de a transaction
	 * @param transaction_value    : Valeur de la transaction (positive ou n�gative)
	 * @param date_operation       : Date de cr�ation de l'op�ration. Peut �tre dans le futur (planification)
	 * @param end_date_transaction : Date de fin, si c'est une p�ration cyclique dont on connait la fin
	 * @param day_number		   : �ni�me jour du cycle. D�pend de periodUnit.	
	 * @param description          : Facultatif
	 * @param transactionType      : Type de transaction (cb, ch�que, retrait...)
	 * @param category		       : Category (alimentaire, transport...)  
	 * @param periodUnit		   : Mensuel, hebdomadaire, annuel, bi-mensuel				
	 */
	public PeriodicTransaction(){
	}
	
	public PeriodicTransaction(String wording, Double transaction_value, Date date_operation, Date end_date_transaction,
							  int day_number, String description, TransactionType transactionType, TargetTransaction targetTransaction, Category category,
							  PeriodUnit periodUnit){
		if(wording.isEmpty()){
			throw new IllegalArgumentException("Le libel� ne peut �tre vide");
		}
		if(transaction_value == null){
			throw new NullPointerException("La valeur de la ligne ne peut �tre vide ou null");
		}
		if(date_operation == null){
			throw new NullPointerException("La date de d�but ne peut �tre null");
		}
		if(transactionType == null){
			throw new NullPointerException("le type de transaction ne peut �tre null");
		}
		if(category == null){
			throw new NullPointerException("le type de transaction ne peut �tre null");
		}
		if((periodUnit == null)){
			if(day_number > 0)
			throw new NullPointerException("Une period ou un jour d'�ch�ance ne peuvent �tre d�clar� l'un sans l'autre");
		}
		else if(day_number <= 0){
			if(periodUnit!= null){
				throw new NullPointerException("Une period ou un jour d'�ch�ance ne peuvent �tre d�clar� l'un sans l'autre");
			}
		}
		if(targetTransaction == null){
			throw new NullPointerException("la cible ne peut �tre null");
		}
		this.wording = wording;
		this.transaction_value = transaction_value;
		this.date_operation = date_operation;
		this.end_date_transaction = end_date_transaction;
		this.day_number = day_number;
		this.description = description;
		this.transactionType = transactionType;
		this.targetTransaction = targetTransaction;
		this.category = category;
		this.periodUnit = periodUnit;
	}
	
	/* GETTERS & SETTERS */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId(){
		return this.id;
	}
	public void setId(int val){
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
	}
	@Column(name="wording")
	public String getWording(){
		return this.wording;
	}
	public void setWording(String wording){
		this.wording = wording;
	}
	@Column(name="transaction_value")
	public double getTransactionValue(){
		return this.transaction_value;
	}
	public void setTransactionValue(double val){
		this.transaction_value = val;
	}
	@Column(name="date_operation")
	@Temporal(TemporalType.DATE)
	public Date getDateOperation(){
		return this.date_operation;
	}
	
	public void setDateOperation(Date date){
		this.date_operation = date;
	}
	@Column(name="end_date_transaction")
	@Temporal(TemporalType.DATE)
	public Date getEndDateTransaction(){
		return this.end_date_transaction;
	}
	
	public void setEndDateTransaction(Date date){
		this.end_date_transaction = date;
	}
	@Column(name="day_number")
	public int getDayNumber(){
		return this.day_number;
	}
	public void setDayNumber(int day){
		this.day_number = day;
	}
	@Column(name="description")
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String desc){
		this.description = desc;
	}
	@ManyToOne
	@JoinColumn(name="id_transactiontype")
	public TransactionType getTransactionType(){
		return this.transactionType;
	}
	public String getTransactionTypeWording(){
		return this.transactionType.getWording();
	}
	public void setTransactionType(TransactionType tt){
		this.transactionType = tt;
	}
	@ManyToOne
	@JoinColumn(name="id_targettransaction")
	public TargetTransaction getTargetTransaction(){
		return this.targetTransaction;
	}
	public String getTargetTransactionName(){
		return this.targetTransaction.getTargetName();
	}
	public void setTargetTransaction(TargetTransaction target){
		this.targetTransaction = target;
	}
	@ManyToOne
	@JoinColumn(name="id_category")
	public Category getCategory(){
		return this.category;
	}
	public String getCategoryWording(){
		return this.category.getWording();
	}
	public void setCategory(Category cat){
		this.category = cat;
	}
	@ManyToOne
	@JoinColumn(name="id_periodunit")
	public PeriodUnit getPeriodUnit(){
		return this.periodUnit;
	}
	public String getPeriodUnitName(){
		return this.periodUnit.getUnit();
	}
	public void setPeriodUnit(PeriodUnit pu){
		this.periodUnit = pu;
	}
	
}
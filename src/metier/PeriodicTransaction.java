package metier;

import java.util.Date;
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
	public PeriodicTransaction(String wording, Double transaction_value, Date date_operation, Date end_date_transaction,
							  int day_number, String description, TransactionType transactionType,  TargetTransaction targetTransaction, Category category,
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
	public int getId(){
		return this.id;
	}
	public void setId(int val){
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
	}
	public String getWording(){
		return this.wording;
	}
	public double getTransactionValue(){
		return this.transaction_value;
	}
	public Date getDateOperation(){
		return this.date_operation;
	}
	public Date getEndDateTransaction(){
		return this.end_date_transaction;
	}
	public int getDayNumber(){
		return this.day_number;
	}
	public String getDescription(){
		return this.description;
	}
	public TransactionType getTransactionType(){
		return this.transactionType;
	}
	public TargetTransaction getTargetTransaction(){
		return this.targetTransaction;
	}
	public Category getCategory(){
		return this.category;
	}
	public PeriodUnit getPeriodUnit(){
		return this.periodUnit;
	}
}

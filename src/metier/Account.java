package metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import application.Tools;

@Entity
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	/* VARIABLES */
	private int id;
	private String account_number;
	private Date creation_date;
	private double first_total;
	private int overdraft;
	private double interest_rate;
	private Agency agency;
	private CountryCode countryCode;
	private AccountType accountType;
	private ArrayList<PeriodicTransaction> transactions = new ArrayList<PeriodicTransaction>();
	private int alert_thresh;
	
	/* CONSTRUCTORS */
	
	/**
	 * 
	 * @param account_number : Numéro de compte, entre 1 et 11 characters
	 * @param date           : Date de création du compte. Inférieur ou égal à la date du jour
	 * @param first_total    : Solde de départ
	 * @param overdraft		 : Découvert. Strictement supérieur (le traitement est fait après en négatif)
	 * @param interest_rate  : Taux d'intérêt supérieur ou égal à 0;
	 * @param agency		 : Agence liée au compte;
	 * @param countryCode	 : Code pays
	 * @param accountType	 : Type de compte (Chèque, épargne, courant...)
	 * @param alert_thresh   : Suil d'alerte. Mettre "0" si pas d'alerte
	 */
	public Account(String account_number,Date date, double first_total, int overdraft,
				   double interest_rate, Agency agency, CountryCode countryCode, AccountType accountType, int alert_thresh){
		if(account_number.length() > 11){
			throw new IllegalArgumentException("Un numéro de compte ne peut être supérieur à 11");
		}
		if(account_number.length() == 0){
			throw new IllegalArgumentException("Un numéro de compte ne peut être vide");
		}
		if(date == null){
			throw new NullPointerException("La date de création ne peut être null");
		}
		if(date.getTime() > Tools.today().getTime()){
			throw new IllegalArgumentException("La date de création ne peut être supérieure à la date du jour");
		}
		if(overdraft < 0){
			throw new IllegalArgumentException("Le découvert autorisé ne peut être inférieur à 0."
					+ "Le traitement en valeur négative sera effectué plus tard");
		}
		if(interest_rate < 0){
			throw new IllegalArgumentException("Le taux d'intérêt ne peut être inférieur à 0");
		}
		if(agency == null){
			throw new NullPointerException("Un compte doit avoir une agence");
		}
		if(countryCode == null){
			throw new NullPointerException("Un compte doit avoir un code pays");
		}
		if(accountType == null){
			throw new NullPointerException("Un compte doit avoir un type de compte (épargne, chèque...");
		}
		this.account_number= account_number;
		this.creation_date = date;
		this.first_total = first_total;
		this.overdraft = overdraft;
		this.interest_rate = interest_rate;
		this.agency = agency;
		this.countryCode = countryCode;
		this.accountType = accountType;
		this.alert_thresh = alert_thresh;
	}
	/**
	 * Empty setter for the EntityManager
	 */
	public Account(){
		
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
	public String getAccountNumber(){
		return this.account_number;
	}
	@Column(name="creation_date")
	@Temporal(TemporalType.DATE)
	public Date getCreationDate(){
		return this.creation_date;
	}
	public double getFirstTotal(){
		return this.first_total;
	}
	public int getOverdraft(){
		return this.overdraft;
	}
	 
	public double getInterestRate(){
		return this.interest_rate;
	}
	public Agency getAgency(){
		return this.agency;
	}
	public CountryCode getCountryCode(){
		return this.countryCode;
	}
	public AccountType getAccountType(){
		return this.accountType;
	}
	public int getAlertThresh(){
		return this.alert_thresh;
	}
	public ArrayList<PeriodicTransaction> getTransactions() {
		return this.transactions;
	}
	
	/* METHODS */
	public void addTransactions(PeriodicTransaction transaction){
		if(transaction == null){
			throw new NullPointerException("La ligne à ajouter ne peut être vide");
		}
		else {
			this.transactions.add(transaction);
		}
	}
}























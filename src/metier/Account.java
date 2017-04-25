package metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import metier.CountryCode;
import application.Tools;

@Entity
@Table(name="account")
@NamedQueries(value = {
	@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a") }
)
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
	private List<PeriodicTransaction> transactions = new ArrayList<PeriodicTransaction>();
	private int alert_thresh;
	
	/* CONSTRUCTORS */
	/**
	 * 
	 * @param account_number : Num�ro de compte, entre 1 et 11 characters
	 * @param date           : Date de cr�ation du compte. Inf�rieur ou �gal � la date du jour
	 * @param first_total    : Solde de d�part
	 * @param overdraft		 : D�couvert. Strictement sup�rieur (le traitement est fait apr�s en n�gatif)
	 * @param interest_rate  : Taux d'int�r�t sup�rieur ou �gal � 0;
	 * @param agency		 : Agence li�e au compte;
	 * @param countryCode	 : Code pays
	 * @param accountType	 : Type de compte (Ch�que, �pargne, courant...)
	 * @param alert_thresh   : Suil d'alerte. Mettre "0" si pas d'alerte
	 */
	public Account(String account_number,Date date, double first_total, int overdraft,
				   double interest_rate, Agency agency, CountryCode countryCode, AccountType accountType, int alert_thresh){
		if(account_number.length() > 11){
			throw new IllegalArgumentException("Un num�ro de compte ne peut �tre sup�rieur � 11");
		}
		if(account_number.length() == 0){
			throw new IllegalArgumentException("Un num�ro de compte ne peut �tre vide");
		}
		if(date == null){
			throw new NullPointerException("La date de cr�ation ne peut �tre null");
		}
		if(date.getTime() > Tools.today().getTime()){
			throw new IllegalArgumentException("La date de cr�ation ne peut �tre sup�rieure � la date du jour");
		}
		if(overdraft < 0){
			throw new IllegalArgumentException("Le d�couvert autoris� ne peut �tre inf�rieur � 0."
					+ "Le traitement en valeur n�gative sera effectu� plus tard");
		}
		if(interest_rate < 0){
			throw new IllegalArgumentException("Le taux d'int�r�t ne peut �tre inf�rieur � 0");
		}
		if(agency == null){
			throw new NullPointerException("Un compte doit avoir une agence");
		}
		if(countryCode == null){
			throw new NullPointerException("Un compte doit avoir un code pays");
		}
		if(accountType == null){
			throw new NullPointerException("Un compte doit avoir un type de compte (�pargne, ch�que...");
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
	@Column(name="account_number")
	public String getAccountNumber(){
		return this.account_number;
	}
	public void setAccountNumber(String number){
		if(number.length() > 11){
			throw new IllegalArgumentException("Un num�ro de compte ne peut �tre sup�rieur � 11");
		}
		if(number.length() == 0){
			throw new IllegalArgumentException("Un num�ro de compte ne peut �tre vide");
		}
		this.account_number = number;
	}
	@Column(name="creation_date")
	@Temporal(TemporalType.DATE)
	public Date getCreationDate(){
		return this.creation_date;
	}
	public void setCreationDate(Date date){
		if(date == null){
			throw new NullPointerException("La date de cr�ation ne peut �tre null");
		}
		if(date.getTime() > Tools.today().getTime()){
			throw new IllegalArgumentException("La date de cr�ation ne peut �tre sup�rieure � la date du jour");
		}
		this.creation_date = date;
	}
	@Column(name="first_total")
	public double getFirstTotal(){
		return this.first_total;
	}
	public void setFirstTotal(double total){
		this.first_total = total;
	}
	@Column(name="overdraft")
	public int getOverdraft(){
		return this.overdraft;
	}
	public void setOverdraft(int val){
		if(val < 0){
			throw new IllegalArgumentException("Le d�couvert autoris� ne peut �tre inf�rieur � 0."
					+ "Le traitement en valeur n�gative sera effectu� plus tard");
		}
		this.overdraft = val;
	}
	@Column(name="interest_rate")
	public double getInterestRate(){
		return this.interest_rate;
	}
	public void setInterestRate(double interest){
		if(interest < 0){
			throw new IllegalArgumentException("Le taux d'int�r�t ne peut �tre inf�rieur � 0");
		}
		this.interest_rate = interest;
	}
	
	@ManyToOne
	@JoinColumn(name="id_agency")
	public Agency getAgency(){
		return this.agency;
	}
	public void setAgency(Agency agency){
		if(agency == null){
			throw new NullPointerException("Un compte doit avoir une agence");
		}
		this.agency = agency;
	}
	
	@ManyToOne
	@JoinColumn(name="id_countrycode")
	public CountryCode getCountryCode(){
		return this.countryCode;
	}
	public void setCountryCode(CountryCode country){
		this.countryCode = country;
	}
	
	@ManyToOne
	@JoinColumn(name="id_accounttype")
	public AccountType getAccountType(){
		return this.accountType;
	}
	public void setAccountType(AccountType acc){
		if(acc == null){
			throw new NullPointerException("Un compte doit avoir un type de compte (�pargne, ch�que...");
		}
		this.accountType = acc;
	}
	@Column(name="alert_thresh")
	public int getAlertThresh(){
		return this.alert_thresh;
	}
	public void setAlertThresh(int alert){
		this.alert_thresh = alert;
	}
	@OneToMany
	@JoinColumn(name="id_account")
	public List<PeriodicTransaction> getTransactions() {
		return this.transactions;
	}
	public void setTransactions(List<PeriodicTransaction> trans){
		this.transactions = trans;
	}
	
	/* METHODS */
	public void addTransactions(PeriodicTransaction transaction){
		if(transaction == null){
			throw new NullPointerException("La ligne � ajouter ne peut �tre vide");
		}
		else {
			this.transactions.add(transaction);
		}
	}
	
	private String calculcateCountryCode(String account_number){
		return "76";
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s", this.accountType.getAccountType(), this.agency.getAgencyName(), this.account_number);
	}
}























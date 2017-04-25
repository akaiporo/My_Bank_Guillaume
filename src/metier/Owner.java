package metier;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import application.Tools;

@Entity
@Table(name="Owner")
@NamedQuery(name="Owner.findAll", query = "SELECT o FROM Owner o")

public class Owner extends Person{
	
	/**
	 * Constructor
	 * @param owner_name : The owner name
	 * @param owner_firstname
	 * @param phone_number
	 * @param email
	 * @param birthdate
	 * @param login
	 * @param pwd
	 * @param address
	 */
	public Owner(String owner_name, String owner_firstname, String phone_number, String email, Date birthdate, String login, String pwd, Address address) {
		super(owner_name, owner_firstname, phone_number, email);
		if (birthdate == null){
			throw new NullPointerException ("birthdate cannot be null");
		}
		if (birthdate.getTime()>Tools.today().getTime()){
			throw new IllegalArgumentException ("birthdate in the future");
		}
		if (login.isEmpty()){
			throw new IllegalArgumentException ("login cannot be empty");
		}
		if (pwd.isEmpty()){
			throw new IllegalArgumentException ("login cannot be empty");
		}
		if (address == null){
			throw new NullPointerException ("address cannot be null");
		}
		this.birthdate=birthdate;
		this.login=login;
		this.pwd=pwd;
		this.address=address;
			
	}
	public Owner() {
		super();
	}
	@Column(name="birthdate")
	@Temporal(TemporalType.DATE)
	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date date){
		this.birthdate = date;
	}
	public void setOwnerName(String owner_name){
		this.owner_name = owner_name;
	}
	public void setOwnerFirstname(String owner_firstname){
		this.owner_firstname = owner_firstname;
	}
	public void setOwnerEmail(String owner_mail){
		this.owner_mail = owner_mail;
	}
	public void setOwnerPhonenumber(String owner_phonenumber){
		this.owner_phonenumber = owner_phonenumber;
	}
	@Column(name="login")
	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login){
		this.login = login;
	}
	@Column(name="pwd")
	public String getPwd() {
		return this.pwd;
	}
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	@ManyToOne
	@JoinColumn(name="id_address")
	public Address getAddress() {
		return this.address;
	}
	private void setAddress(Address address){
		this.address = address;
	}
	
	
	private Date birthdate;
	private String login;
	private String pwd;
	private Address address;
	private String owner_name;
	private String owner_firstname;
	private String owner_mail;
	private String owner_phonenumber;
	

}

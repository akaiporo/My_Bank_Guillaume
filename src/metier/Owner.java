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
	public Owner(String name, String firstname, String phonenumber, String email, Date birthdate, String login, String pwd, Address address) {
		super(name, firstname, phonenumber, email);
		
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
			throw new IllegalArgumentException ("password cannot be empty");
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
		if (date == null){
			throw new NullPointerException ("birthdate cannot be null");
		}
		if (date.getTime()>Tools.today().getTime()){
			throw new IllegalArgumentException ("birthdate in the future");
		}
		this.birthdate = date;
	}
	/*
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
	} */
	@Column(name="login")
	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login){
		if (login.isEmpty()){
			throw new IllegalArgumentException ("login cannot be empty");
		}
		this.login = login;
	}
	@Column(name="pwd")
	public String getPwd() {
		return this.pwd;
	}
	public void setPwd(String pwd){
		if (pwd.isEmpty()){
			throw new IllegalArgumentException ("password cannot be empty");
		}
		this.pwd = pwd;
	}
	@ManyToOne
	@JoinColumn(name="id_address")
	public Address getAddress() {
		return this.address;
	}
	private void setAddress(Address address){
		if (address == null){
			throw new NullPointerException ("address cannot be null");
		}
		this.address = address;
	}
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Owner){
			Owner tmp = (Owner)obj;
		
			if(((tmp.getName()==null || this.getName()==null) || tmp.getName().equals(this.getName()))
				&& 
				((tmp.getFirstName()==null || this.getFirstName()==null) || tmp.getFirstName().equals(this.getFirstName())) 
				&&	 
				((tmp.getPhoneNumber()==null||this.getPhoneNumber()==null) || tmp.getPhoneNumber().equals(this.getPhoneNumber()))
				&&
				((tmp.getEmail()==null || this.getEmail()==null) || tmp.getEmail().equals(this.getEmail()))
				&&
				((tmp.getBirthdate()==null||this.getBirthdate()==null) || tmp.getBirthdate().equals(this.getBirthdate()))
				&&
				((tmp.getLogin()==null||this.getLogin()==null) || tmp.getLogin().equals(this.getLogin()))
				&&
				((tmp.getPwd()==null||this.getPwd()==null) || tmp.getPwd().equals(this.getPwd()))
				&&
				((tmp.getAddress()==null||this.getAddress()==null) || tmp.getAddress().equals(this.getAddress())))
			{
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", getName(), this.getFirstName());
	}
	
	private Date birthdate;
	private String login;
	private String pwd;
	private Address address;
	/*private String owner_name;
	private String owner_firstname;
	private String owner_mail;
	private String owner_phonenumber;*/
	

}

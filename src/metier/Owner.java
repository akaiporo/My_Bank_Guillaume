package metier;

import java.util.Date;

import application.Tools;

public class Owner extends Person {

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
	
	public Date getBirthdate() {
		return this.birthdate;
	}
	public String getLogin() {
		return this.login;
	}
	public String getPwd() {
		return this.pwd;
	}
	public Address getAddress() {
		return this.address;
	}
	
	private Date birthdate;
	private String login;
	private String pwd;
	private Address address;
	

}

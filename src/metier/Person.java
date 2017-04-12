package metier;

import application.Tools;

public class Person {
	/**
	 * 
	 * @param id : identifier of the person 
	 * @param advisor_name : name of the person
	 * @param advisor_firstname : first name of the person
	 * @param phone_number : phone number of the person
	 * @param email : email of the person
	 */

	public Person(String name, String firstname, String phone_number, String email) {
		if(name.isEmpty()) {
			throw new IllegalArgumentException("The name cannot be empty");
		}
		if(firstname.isEmpty()) {
			throw new IllegalArgumentException("The firstname cannot be empty");
		}
		if(Tools.eraseChar(phone_number,"\\s").length()<4||Tools.eraseChar(phone_number,"\\s").length()>12) {
			throw new IllegalArgumentException("phone_number must contain between 4 and 12 numbers");
		}
		if(!Tools.checkMail(email)){
			throw new IllegalArgumentException("email must be of a valid format eg toto@titi.tutu");
		}
		this.name = name;
		this.firstname = firstname;
		this.phone_number = phone_number;
		this.email = email;

	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int val){
		if(val <=0){
			throw new IllegalArgumentException("Id must be strictly superior to 0");
		}
		else this.id = val;
	}
	public String getName() {
		return this.name;
	}
	
	public String getFirstName() {
		return this.firstname;
	}
	
	public String getPhoneNumber() {
		return this.phone_number;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	private int id;
	private String name;
	private String firstname;
	private String phone_number;
	private String email;
}

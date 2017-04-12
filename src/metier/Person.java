package metier;

import application.Tools;

public class Person {
	/**
	 * 
	 * @param name : name of the person
	 * @param firstname : first name of the person
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
		if(Tools.eraseChar(phone_number,"\\s").length()<4||Tools.eraseChar(phone_number,"\\s").length()>11) {
			throw new IllegalArgumentException("phone_number must contain between 4 and 11 numbers");
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
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
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
	
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Person){
			Person tmp = (Person)obj;
		
			if(tmp.getName().equals(this.getName()) && 
				tmp.getFirstName().equals(this.getFirstName()) &&	 
				tmp.getPhoneNumber().equals(this.getPhoneNumber()) &&
				tmp.getEmail().equals(this.getEmail())){
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	private int id;
	private String name;
	private String firstname;
	private String phone_number;
	private String email;
}

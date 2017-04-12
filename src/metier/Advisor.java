package metier;

import java.util.Date;

import application.Tools;

public class Advisor extends Person {
	/**
	 * @param date_assignment : Advisor assignment date
	 * @param agency : Advisor agency
	 */

	public Advisor(String advisor_name, String advisor_firstname, String phone_number, String email, Date date_assignment,Agency agency) {
		super(advisor_name, advisor_firstname, phone_number, email);
		
		if(date_assignment == null) {
			throw new NullPointerException("Date assignment cannot be null");
		}
		if(date_assignment.getTime() > Tools.today().getTime()) {  
			throw new IllegalArgumentException ("Date assigment in the future");
		}	
				
	this.date_assignment = date_assignment;
	this.agency = agency;
	
	}
	
	public Date getDateAssigment() {
		return this.date_assignment;
	}
	
	public Agency getAgency() {
		return this.agency;
	}
	
	private Date date_assignment;
	private Agency agency;
}

package metier;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import application.Tools;
@Entity
@Table(name="advisor")
@NamedQuery(name="advisor.findAll", query="SELECT ad FROM Advisor ad")
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
		if(agency == null) {
			throw new NullPointerException("Agency cannot be null");
		}
				
	this.date_assignment = date_assignment;
	this.agency = agency;
	
	}
	
	public Advisor(){
		super();
	}
	
	public Date getDateAssignment() {
		return this.date_assignment;
	}
	
	public Agency getAgency() {
		return this.agency;
	}
	
	private Date date_assignment;
	private Agency agency;

}

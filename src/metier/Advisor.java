package metier;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
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
@Table(name="advisor")
@NamedQuery(name="Advisor.findAll", query="SELECT ad FROM Advisor ad")
public class Advisor extends Person {
	/**
	 * @param date_assignment : Advisor assignment date
	 * @param agency : Advisor agency
	 */ 

	public Advisor(String name, String firstname, String phonenumber, String email, Date date_assignment,Agency agency) {
		super(name, firstname, phonenumber, email);
		
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
	@Column(name="date_assignment")
	@Temporal(TemporalType.DATE)
	public Date getDateAssignment() {
		return this.date_assignment;
	}
	
	private void setDateAssignment(Date date){
		this.date_assignment = date;
	}
	@ManyToOne
	@JoinColumn(name="id_agency")
	public Agency getAgency() {
		return this.agency;
	}
	private void setAgency(Agency agency){
		this.agency = agency;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Advisor){
			Advisor tmp = (Advisor)obj;
		
			if(((tmp.getName()==null || this.getName()==null) || tmp.getName().equals(this.getName()))
				&& 
				((tmp.getFirstName()==null || this.getFirstName()==null) || tmp.getFirstName().equals(this.getFirstName())) 
				&&	 
				((tmp.getPhoneNumber()==null||this.getPhoneNumber()==null) || tmp.getPhoneNumber().equals(this.getPhoneNumber()))
				&&
				((tmp.getEmail()==null || this.getEmail()==null) || tmp.getEmail().equals(this.getEmail()))
				&&
				((tmp.getDateAssignment()==null||this.getDateAssignment()==null) || tmp.getDateAssignment().equals(this.getDateAssignment()))
				&&
				((tmp.getAgency()==null||this.getAgency()==null) || tmp.getAgency().equals(this.getAgency())))
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
	
	private Date date_assignment;
	private Agency agency;

}

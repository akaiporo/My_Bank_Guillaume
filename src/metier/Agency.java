package metier;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="agency")
@NamedQuery(name="agency.findAll", query="SELECT ag FROM Agency ag")
public class Agency {
	/**
	 * 
	 * @param id
	 * @param agency_name
	 * @param counter_code
	 * @param address
	 * @param bank
	 */

	public Agency(String agency_name, String counter_code, Address address, Bank bank) {
		
		if(agency_name.isEmpty()) {
			throw new IllegalArgumentException("The agency name cannot be empty");
		}
		if(counter_code.length() != 5) {
			throw new IllegalArgumentException("The counter code must cointain five caracters");
		}
		if(address == null) {
			throw new NullPointerException("The agency address  cannot be null");
		}
		if(bank == null) {
			throw new NullPointerException("The bank agency cannot be null");
		}
		
	this.agency_name = agency_name;
	this.counter_code = counter_code;
	this.address = address;
	this.bank = bank;
	}
	
	public Agency(){
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}
	
	public void setId(int val){
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
	}
	
	public String getAgencyName() {
		return this.agency_name;
	}
	 
	public String getCounterCode() {
		return this.counter_code;
	}
	@ManyToOne
	@JoinColumn(name="id_address")
	public Address getAddress() {
		return this.address;
	}
	@ManyToOne
	@JoinColumn(name="id_bank")
	public Bank getBank() {
		return this.bank;
	}
	
	@Override // redéfinition de la fonction equals pour la comparaison de ts les champs quand on a  un objet
	
	public boolean equals(Object obj){
		if(obj instanceof Agency){
			Agency tmp = (Agency)obj;
		
			if(tmp.getAgencyName().equals(this.getAgencyName()) && 
					tmp.getCounterCode().equals(this.getCounterCode()) &&	 
					tmp.getAddress().equals(this.getAddress()) &&
					tmp.getBank().equals(this.getBank())
						){
					return true;
					}
			else return false;
		}
		else {
			return false;
		}
	}
	
	private int id;
	private String agency_name;
	private String counter_code;
	private Address address;
	private Bank bank;

}

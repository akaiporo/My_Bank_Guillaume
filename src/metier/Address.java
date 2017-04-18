package metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="address")
@NamedQuery(name="Address.findAll", query="SELECT add FROM AccountType add")
public class Address {
	/**
	 * 
	 * @param id
	 * @param line1
	 * @param line2
	 * @param cpCity
	 */

	public Address(String line1, String line2, CpCity cpcity) {
		if(line1.isEmpty()) {
			throw new IllegalArgumentException("The line1 cannot be empty");
		}
		if(line2 == null){
			this.line2 = "";
		}
		else this.line2 = line2;
		if(cpcity == null){
			throw new NullPointerException("PostalCode and City can't be null");
		}
		this.line1 = line1;
		this.cpCity = cpcity;

	}
	public Address(){
		
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
	public String getLine1() {
		return this.line1;
	}
	
	public String getLine2() {
		return this.line2;
	}
	
	public CpCity getCpCity() {
		return this.cpCity;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Address){
			Address tmp = (Address)obj;
		
			if(tmp.getLine1().equals(this.getLine1()) && 
				tmp.getLine2().equals(this.getLine2()) &&	 
				tmp.getCpCity().equals(this.getCpCity())){
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	private int id;
	private String line1;
	private String line2;
	private CpCity cpCity;
}
		

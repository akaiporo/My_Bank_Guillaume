package metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="cpcity")
@NamedQuery(name="cpcity.findAll", query="SELECT ad FROM CpCity ad")
public class CpCity {

	int id;
	String postalCode;
	String city;
	
	public CpCity(String postalcode, String city) {
		if(postalcode.length() != 5){
			throw new IllegalArgumentException("Poste code must must contains 5 characters");
		}
		if(city.length() == 0){
			throw new IllegalArgumentException("City can't be empty");
		}
		this.postalCode = postalcode;
		this.city = city;
	}
	
	public CpCity(){
		
	} 
	
	public void setId(int id){
		if(id <= 0){
			throw new IllegalArgumentException("Id can't be null or negativ");
		}
		this.id = id;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId(){
		return this.id;
	}
	public String getPostalCode(){
		return this.postalCode;
	}
	public String getCity(){
		return this.city;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof CpCity){
			CpCity tmp = (CpCity)obj;
	
			if(tmp.getPostalCode().equals(this.getPostalCode()) &&	 
				tmp.getCity().equals(this.getCity())){
				return true;
			}
			else return false;
		}
		else return false;
	}

}

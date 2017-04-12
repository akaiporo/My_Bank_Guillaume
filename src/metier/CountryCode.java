package metier;

public class CountryCode {

	/* VARIABLES */
	private int id;
	private String countryCode;
	
	/* CONSTRUCTORS */
	public CountryCode(String code){
		if(code.length() != 2){
			throw new IllegalArgumentException("Le code pays doit contenir exactement deux chiffres");
		}
		
		this.countryCode = code;
	}
	
	/* GETTERS & SETTERS */
	public int getId(){
		return this.id;
	}
	public void setId(int val){
		if(val <=0){
			throw new IllegalArgumentException("Id must be strictly superior to 0");
		}
		else this.id = val;
	}
	public String getCountryCode(){
		return this.countryCode;
	}
	
}

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
	public String getCountryCode(){
		return this.countryCode;
	}
	public void setId(int val){
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof CountryCode){
			CountryCode tmp = (CountryCode)obj;
		
			if(tmp.getCountryCode().equals(this.getCountryCode())){
				return true;
			}
			else return false;
		}
		else return false;	
	}
}

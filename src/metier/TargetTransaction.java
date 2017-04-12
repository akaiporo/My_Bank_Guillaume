package metier;

public class TargetTransaction {

	public TargetTransaction(String target_name,String IBAN) {
		if (target_name.isEmpty()){
			throw new IllegalArgumentException("wording cannot be empty");
		}
		if (IBAN.length() != 27){
			throw new IllegalArgumentException("IBAN is composed of 27 car in France");
		}
		if (!IBAN.toUpperCase().startsWith("FR")){
			throw new IllegalArgumentException("IBAN begins with 'FR' in France");
		}
		this.target_name=target_name;
		this.IBAN=IBAN;
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
	public String getTargetName(){
		return this.target_name;
	}
	public String getIBAN(){
		return this.IBAN;
	}
	
	
	private int id;
	private String target_name;
	private String IBAN;

}

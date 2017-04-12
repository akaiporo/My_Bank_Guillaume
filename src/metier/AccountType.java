package metier;

public class AccountType {

	/* VARIABLES */
	private int id;
	private String accountType;
	
	/* CONSTRUCTOR */
	public AccountType(String account_type){
		//Si account_type est "null", l'appelle à isEmpty() lancera automatiquement une NullPointerException
		if(account_type.isEmpty()){
			throw new IllegalArgumentException();
		}
		this.accountType = account_type;
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
	public String getAccountType(){
		return this.accountType;
	}
	
}

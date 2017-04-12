package metier;

public class AccountType {

	/* VARIABLES */
	private int id;
	private String accountType;
	
	/* CONSTRUCTOR */
	public AccountType(String account_type){
		if(account_type.isEmpty()){
			throw new IllegalArgumentException();
		}
		this.accountType = account_type;
	}
	
	/* GETTERS & SETTERS */
	public int getId(){
		return this.id;
	}
	public String getAccountType(){
		return this.accountType;
	}
	public void setId(int val){
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof AccountType){
			AccountType tmp = (AccountType)obj;
		
			if(tmp.getAccountType().equals(this.getAccountType())){
				return true;
			}
			else return false;
		}
		else return false;	
	}
}

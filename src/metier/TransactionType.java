package metier;

public class TransactionType {

	/* VARIABLES */
	private int id;
	private String wording;
	
	/* CONSTRUCTORS */
	public TransactionType(String wording){
		if(wording.isEmpty()){
			throw new IllegalArgumentException("Transaction type can't be null or empty");
		}
		this.wording = wording;
	}
	
	/*GETTERS & SETTERS*/
	public int getId(){
		return this.id;
	}
	public void setId(int val){
		if(val <=0){
			throw new IllegalArgumentException("Id must be strictly superior to 0");
		}
		else this.id = val;
	}
	public String getWording(){
		return this.wording;
	}
}

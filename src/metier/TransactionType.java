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
	public String getWording(){
		return this.wording;
	}
	public void setId(int val){
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof TransactionType){
			TransactionType tmp = (TransactionType)obj;
		
			if(tmp.getWording().equals(this.getWording())){
				return true;
			}
			else return false;
		}
		else return false;	
	}
}

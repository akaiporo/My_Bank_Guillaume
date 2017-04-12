package metier;

public class Bank {
	/**
	 * 
	 * @param id
	 * @param bank_name
	 * @param bank_code
	 */

	public Bank(String bank_name, String bank_code) {
		if(bank_name.isEmpty()) {
			throw new IllegalArgumentException("The bank name cannot be empty");
		}
		if(bank_code.isEmpty()) {
			throw new IllegalArgumentException ("The bank code canoot be empty");
		}
		this.bank_name = bank_name;
		this.bank_code = bank_code;
		
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
	public String getBankName() {
		return this.bank_name;
	}
	
	public String getBankCode() {
		return this.bank_code;
		
	}
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Bank){
			Bank tmp = (Bank)obj;
			Bank bank = new Bank(tmp.getBankName(), tmp.getBankCode());
			if(bank.getBankName() == this.getBankName() && 
			   bank.getBankCode() == this.getBankCode()){
				return true;
			}
			else return false;
		}
		else throw new IllegalArgumentException("Can't compare a bank and anon-bank object");
	}
	private int id;
	private String bank_name;
	private String bank_code;
	

}

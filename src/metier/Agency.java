package metier;

public class Agency {
	/**
	 * 
	 * @param id
	 * @param agency_name
	 * @param counter_code
	 * @param address
	 * @param bank
	 */

	public Agency(String agency_name, String counter_code, Address address, Bank bank) {
		if(agency_name.isEmpty()) {
			throw new IllegalArgumentException("The agency name cannot be empty");
		}
		if(counter_code.length() != 5) {
			throw new IllegalArgumentException("The counter code must cointain five caracters");
		}
		if(address == null) {
			throw new NullPointerException("The agency address  cannot be null");
		}
		if(bank == null) {
			throw new NullPointerException("The bank agency cannot be null");
		}
		
		this.agency_name = agency_name;
		this.counter_code = counter_code;
		this.address = address;
		this.bank = bank;
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
	public String getAgencyName() {
		return this.agency_name;
	}
	
	public String getCounterCode() {
		return this.counter_code;
	}
	
	public Address getAddress() {
		return this.address;
	}
	
	public Bank getBank() {
		return this.bank;
	}
	
	private int id;
	private String agency_name;
	private String counter_code;
	private Address address;
	private Bank bank;

}

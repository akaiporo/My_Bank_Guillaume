package metier;

public class Address {
	/**
	 * 
	 * @param id
	 * @param line1
	 * @param line2
	 * @param postalcode
	 * @param city
	 */

	public Address(String line1, String line2, String postalcode, String city) {
		if(line1.isEmpty()) {
			throw new IllegalArgumentException("The line1 cannot be empty");
		}
		if(postalcode.isEmpty()) {
			throw new IllegalArgumentException("The postal code cannot be empty");
		}
		if(city.isEmpty()) {
			throw new IllegalArgumentException("The city cannot be empty");
		}
		if(line2 == null){
			this.line2 = "";
		}
		else this.line2 = line2;
		this.line1 = line1;
		this.postalcode = postalcode;
		this.city = city;

	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int val){
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
	}
	public String getLine1() {
		return this.line1;
	}
	
	public String getLine2() {
		return this.line2;
	}
	
	public String getPostalCode() {
		return this.postalcode;
	}
	
	public String getCity() {
		return this.city;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Address){
			Address tmp = (Address)obj;
		
			if(tmp.getLine1().equals(this.getLine1()) && 
				tmp.getLine2().equals(this.getLine2()) &&	 
				tmp.getPostalCode().equals(this.getPostalCode()) &&
				tmp.getCity().equals(this.getCity())){
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	private int id;
	private String line1;
	private String line2;
	private String postalcode;
	private String city;
}
		

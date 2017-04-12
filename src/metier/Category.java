package metier;

public class Category {

	public Category(String wording,Category category) {
		if (wording.isEmpty()){
			throw new IllegalArgumentException("wording cannot be empty");
		}
		this.wording=wording;
		this.category=category; 
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
	public String getWording(){
		return this.wording;
	}
	public Category getCategory(){
		return this.category;
	}
	private int id;
	private String wording;
	private Category category;
}

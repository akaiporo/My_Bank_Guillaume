package metier;

public class Category {

	public Category(String wording,Category category) {
	
		if (wording.isEmpty()){
			throw new IllegalArgumentException("wording cannot be empty");
		}
		// category à tester pour voir si la catégorie mère existe
		this.wording=wording;
		this.category=category; 
	}
	
	public int getId() {
		return this.id;
	}
	public String getWording(){
		return this.wording;
	}
	public Category getCategory(){
		return this.category;
	}
	public void setId(int val){
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Category){
			Category tmp = (Category)obj;
			
			if(tmp.getWording().equals(this.getWording()) && 
			   tmp.getCategory().equals(this.getCategory())){
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	private int id;
	private String wording;
	private Category category;
}

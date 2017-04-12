package metier;

public class PeriodUnit {

	public PeriodUnit(String unit) {
		if (unit.isEmpty()){
			throw new IllegalArgumentException("unit cannot be empty, eg 'day' or 'month'");
		}
		this.unit=unit;
	}
	
	public int getId(){
		return this.id;
	}
	public void setId(int val){
		if(val <=0){
			throw new IllegalArgumentException("Id must be strictly superior to 0");
		}
		else this.id = val;
	}
	public String getUnit(){
		return this.unit;
	}
	
	private int id;
	private String unit;
}

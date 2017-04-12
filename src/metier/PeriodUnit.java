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
		if(val <= 0){
			throw new IllegalArgumentException();
		}
		this.id = val;
	}
	public String getUnit(){
		return this.unit;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof PeriodUnit){
			PeriodUnit tmp = (PeriodUnit)obj;
		
			if(tmp.getUnit().equals(this.getUnit())){
				return true;
			}
			else return false;
		}
		else return false;	
	}
	
	private int id;
	private String unit;
}

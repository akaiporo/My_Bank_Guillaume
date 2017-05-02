package ribcalculation;

import application.ControllerBase;
import application.Mediator;

public class RIBCalculationController extends ControllerBase {

	@Override
	public void initialize(Mediator mediator) {
		// TODO Auto-generated method stub
		
	}
	
	private int tradRIBkey(String brut){
		String traduit="";
		String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		for (int j=0 ; j<brut.length() ; j++){
			boolean bool=true;
			for (int i=0 ; i<alphabet.length() ; i++){
				if (brut.charAt(j)==alphabet.charAt(i)){
					if (i>17){
						traduit=traduit+(i%9+2);
					}
					else{traduit=traduit+(i%9+1);}
					bool=false;
				}
			}
			if (bool){
				traduit=traduit+brut.charAt(j);
			}
		}
		return Integer.parseInt(traduit);
	}
	
	private int calculationRIBkey(String bank_code,String counter_code,String account_number){
		return 97-(89*tradRIBkey(bank_code)+15*tradRIBkey(counter_code)+3*tradRIBkey(account_number))%97;
	}

}

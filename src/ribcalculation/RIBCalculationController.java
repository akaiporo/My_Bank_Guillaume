package ribcalculation;

import java.math.BigInteger;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import application.ControllerBase;
import application.MainWindowController;
import application.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import metier.Account;
import metier.Address;
import metier.Agency;
import metier.Bank;
import metier.CpCity;
import metier.Owner;
import javafx.scene.control.Alert.AlertType;

public class RIBCalculationController extends ControllerBase {
	private EntityManager em;
	private Owner owner = MainWindowController.currentOwner;
	private Agency agency = new Agency();
	private Account account = new Account();
	private Bank bank = new Bank();
	private CpCity cpcity = new CpCity();
	private Address address = new Address();
	private Long RIBkey;
	private String IBANkey;

	
	@ FXML private Label titulaire;
	@ FXML private Label domiciliation;
	@ FXML private Label bank_code;
	@ FXML private Label counter_code;
	@ FXML private Label account_number;
	@ FXML private Label rib_key;
	@ FXML private Label iban;
	@ FXML private Label bic;
	@ FXML private Button btn_print;
	@ FXML private Button btn_cancel;
	
	private String bankcode;
	private String countercode;
	private String accountnumber;
	private String countrycode;
	
	@Override
	public void initialize(Mediator mediator) {
		
		try {
			em = mediator.createEntityManager();
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
		}
		titulaire.setText(String.format("%s %s",owner.getFirstName(), owner.getName()));
					
	}
	/**
	 * Dans une premier temps, la fonction tradRIBkey va permettre de convertir en chiffres toutes les lettres se trouvant dans les données bancaires;
	 *Le résultat est ensuite stocké dans "traduit" sous forme de "Long".
	 */
	
	private Long tradRIBkey(String brut){
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
		return Long.parseLong(traduit);
		
		
	}
	/**
	 *  La clé RIB est ensuite calculée, en prenant soin de traduire en chiffres toutes les données bancaires pouvant contenir des lettres.
	 */
	private Long calculationRIBkey(String bank_code,String counter_code,String account_number){
		 return 97-(89*tradRIBkey(bank_code)+15*tradRIBkey(counter_code)+3*tradRIBkey(account_number))%97;
	}
	/**
	 * Dans un premeier temps on va traduire les données bancaires pouvant contenir des lettres en chiffres; qu'on va stocker dans "traduit".
	 * On va ensuite calculer le modulo 97 de traduit ==> "résultat";
	 * Si le résultat est composé de 1 chiffre, on affiche la clé IBAN en rajoutant "0" devant le "résultat".
	 * Sinon, la clé IBAN est égale au "résultat"
	 */
	private String calculationIBANkey(String bank_code,String counter_code,String account_number,String country_code){
	String brut = String.format("%s%s%s%s%s%s%s", bank_code,counter_code,account_number,Long.toString(calculationRIBkey(bank_code,counter_code,account_number)),country_code,0,0);
	
	String traduit="";
	String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	BigInteger resultat;
	
	for (int j=0 ; j<brut.length() ; j++){
		boolean bool=true;
		for (int i=0 ; i<alphabet.length() ; i++){
			if (brut.charAt(j)==alphabet.charAt(i)){
				traduit=traduit+(i+10);
				bool=false;
			}
		}
		if (bool){
			traduit=traduit+brut.charAt(j);
		}
	}
	resultat = new BigInteger(traduit).mod(BigInteger.valueOf(97));
	resultat= BigInteger.valueOf(98).subtract(resultat);
		if(resultat.toString().length()!=1) {
			return resultat.toString();
		}
		else {
			return "0"+resultat.toString();
		}
	}
	
	/**
	 * @param event : L'événement du bouton cancel va permettre  de revenir dans la fenêtre des comptes 
	 * si jamais on ne veut plus générer un RIB, tout en demandant une confirmation
	 */
	@FXML
	private void handleButtonCancel(ActionEvent event) {
		Alert alert = new Alert(
				AlertType.CONFIRMATION,
				"Are you sure you want to cancel ?",
				ButtonType.YES,
				ButtonType.NO
		);
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.isPresent() && result.get() == ButtonType.YES) {
				
			this.loadSubScene("../compteCourant/CompteCourantList.fxml");
			
		}
		
	}
	private void handleButtonPrint(ActionEvent event) {
		
		// A compléter ultérieurement
	
	}	
	
	/**
	 * Affiche les erreurs relatives à la base de données (e.g : champs inexistants, incompatibles, etc...)
	 * @param e : PersistenceException
	 */
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	
	public void setAccount(Account a){
		this.account = a;
		this.agency = account.getAgency();
		this.accountnumber=account.getAccountNumber();
		this.bankcode=account.getAgency().getBank().getBankCode();
		this.countercode=account.getAgency().getCounterCode();
		this.countrycode=account.getCountryCode().getCountryCode();
		
		account_number.setText(this.accountnumber);
		bank_code.setText(this.bankcode);
		counter_code.setText(this.countercode);
		domiciliation.setText(String.format("%s %s %s %s", agency.getAddress().getLine1(), agency.getAddress().getLine2(), agency.getAddress().getCpCity().getPostalCode(), agency.getAddress().getCpCity().getCity()));
		RIBkey=calculationRIBkey(bankcode, countercode,accountnumber);
		rib_key.setText(Long.toString(RIBkey));
		IBANkey=calculationIBANkey(bankcode, countercode,accountnumber,countrycode);
		iban.setText(String.format("%s%s%s%s%s%s",countrycode,IBANkey.toString(), bankcode, countercode, accountnumber, Long.toString(RIBkey))) ;
	}
}

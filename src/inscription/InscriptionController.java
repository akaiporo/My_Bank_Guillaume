package inscription;

import application.Tools;

public class InscriptionController {

	private String firstname;
	private String lastname;
	private String password;
	private String confirm_password;
	private String email;
	
	public InscriptionController(String fn, String ln, String pwd, String confpwd, String mail) {
		this.firstname = fn;
		this.lastname = ln;
		this.password = pwd;
		this.confirm_password = confpwd;
		this.email = mail;
		
		if(!checkPassword()){
			System.out.print("Les mots de passe de correspondent pas.");
		}
		else if(!Tools.checkMail(this.email)){
			System.out.print("L'adresse email n'est pas valide. Elle doit être du format xxx@yyy.zz");
		}
		
		else System.out.print(String.format("Bienvenue à vous, %s %s", this.firstname, this.lastname));
	}
	
	/**
	 * Test if the passwords are matching and if they're not composes of multiple white spaces
	 * @return true if they're matching the conditions
	 */
	private boolean checkPassword(){
		if(Tools.eraseChar(password, "\\s").length() != 0){
			return password.equals(confirm_password);
		}
		else return false;	
	}
}

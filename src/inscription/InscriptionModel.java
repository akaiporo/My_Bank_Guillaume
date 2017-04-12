package inscription;

/**
 * G�re les acc�s aux donn�es relatives � l'inscription : 
 * -> Push des donn�es dans la base et les tables relatives
 * -> Check des donn�es existantes pour �viter les doublons
 * @author Guillaume
 *
 */
public class InscriptionModel {

	public InscriptionModel() {
		
	}
	
	/**
	 * Push the data to the database
	 */
	public void PushInscriptionData(){
		
	}
	/**
	 * Check if a owner with the same username or email already exists
	 * @return true if so. Returning true avoid the inscription to be finalized
	 */
	public boolean checkExistingOwners(){
		return false;
	}
	
	

}

package inscription;

/**
 * Gère les accès aux données relatives à l'inscription : 
 * -> Push des données dans la base et les tables relatives
 * -> Check des données existantes pour éviter les doublons
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

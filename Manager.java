
/**
 * This class creates a Manager object that is a Singletone class. 
 * @author Raz Ben-Aderet and Maor Getter
 */
public class Manager 
{
	
	private static Manager Manager_instance=null;
	private static NightClubMgmtApp app;
	
	/**
	 * Manager constructor.
	 */
	private Manager() // Using a 'SingleTone' Class
	{
		app=new NightClubMgmtApp();
	}
	
	/**
	 * A method for the creation of only one instance of the class.
	 * @return the reference of the created Manager-Object.
	 */
	public static Manager Manager() 
    { 
        // To ensure only one instance is created 
        if (Manager_instance == null) 
        { 
        	Manager_instance = new Manager();
        }        
        return Manager_instance; 
    } 
	
	/**
	 * Checks whether a given ID already exists in the DB.
	 * This method is static and can be accessed from other classes that wants to ensure a Unique-ID.
	 * @param ID - the ID to check.
	 * @return boolean answer - false if the ID already exists, true otherwise.
	 */
	public static boolean Check_ID(String ID)
	{
		if(app.Check_Unique_ID(ID)==false)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Checks whether a Unique-ID-Check is needed or not.
	 * @return the check type - true if a Unique-ID-Check is not needed, false otherwise.
	 */
	public static boolean What_CHeck_To_Use()
	{
		return app.What_Check_Type();
	}
	
	/**
	* Main method that creates and runs the program 
	* @param args main arguments
	*/
	public static void main(String[] args)
	{
		Manager manager =  Manager.Manager();
	}
}

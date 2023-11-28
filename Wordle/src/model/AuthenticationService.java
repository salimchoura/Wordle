package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import model.interfaces.Loggable;

/**
 * This class represents a uniform authentication service 
 * for user authentication and persistance.
 * @author UBIS
 *
 */
public class AuthenticationService implements Loggable
{
	private HashMap<String, UserAccount> users;
	private UserAccount currentUser;
	private String DatabaseString="usersDB";
	
	/**
	 * This is a constructor.
	 */
	public AuthenticationService()
	{
		//do this to avoid serialization problems.
				getUsers();
//		users=new HashMap<String, UserAccount>();
	}
	
	/**
	 * This methods gets users from the DB.
	 * @return map of users.
	 */
	private synchronized HashMap<String, UserAccount>  getUsers() 
	{
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("usersDB"));
			try 
			{
				users = (HashMap<String, UserAccount>) input.readObject();
				return users;
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private synchronized void writeAccount(UserAccount newAccount) 
	{
		users.put(newAccount.getUserName(), newAccount);

		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("usersDB"));
			output.writeObject(users);
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private synchronized void saveData() 
    {

		users.put(currentUser.getUserName(), currentUser);

		try 
		{
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("usersDB"));
			output.writeObject(users);
		}

		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * This method logs in the user.
	 * @param newUsername the name of the user.
	 * @param newPassword the password of the user.
	 * @return the code that indicates the status of the log in.
	 */
	public int logIn(String newUsername, String newPassword)
	{	
		if (newUsername == "" || newPassword == "") {
			return 4;
		}
		
		if (currentUser!=null)
		{
			//someone is logged in.
			return 0;
		}
		
		if (!users.containsKey(newUsername))
		{
			//user name does not exist;
			return 1;
		}
		
		UserAccount key=users.get(newUsername);
		
		if(!key.getPassword().equals(newPassword))
		{
			//password does not match
			return 2;
		}
		
		currentUser=users.get(newUsername);
		return 3;
	}
	
	/**
	 * This methods logs out the user.
	 * @return the code that indicates the status of the log in.
	 */
	public int logOut()
	{
		if (currentUser ==null)
		{
			//no one to log out
			return 0;
		}
		
		saveData();
		currentUser=null;
		return 1;
	}
	
	/**
	 * This method create a new account.
	 * @param newUsername the name of the user.
	 * @param newPassword the password of the user.
	 * @return the code that indicates the status of the log in.
	 */
	public int createAccount(String newUsername, String newPassword)
	{
		
		if (newUsername == "" || newPassword == "") {
			return 2;
		}
		
		if (users.containsKey(newUsername))
		{
			//the account already exists
			return 0;
		}
		
		UserAccount newUser=new UserAccount(newUsername, newPassword);
		writeAccount(newUser);
		return 1;
	}
	
	/**
	 * This method returns the current user.
	 * @return current user.
	 */
	public UserAccount getCurrentUser()
	{
		return currentUser;
	}
}

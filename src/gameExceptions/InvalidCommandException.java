package gameExceptions;

/**Class: Adventure 
 * @author Ryan Gambrell
 * @version 2.0 
 * Course: ITEC 3860 Spring 2021
 * Written: March 21, 2021
 * 
 * This class is the main class. It contains the game loop
 * responsible for obtaining user input and passing it to the game controller 
 * for processing. It also manages the map's current room and it is responsible for all 
 * exception handling. 
*/
public class InvalidCommandException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCommandException()
	{
	}
	
	public InvalidCommandException(String msg)
	{
		super(msg); 
	}
}

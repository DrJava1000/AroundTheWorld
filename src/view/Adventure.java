package view;

import java.util.Scanner;

import controller.GameController;
import controller.Room;
import gameExceptions.InvalidCommandException;
import gameExceptions.InvalidExitException;
import gameExceptions.InvalidFileException;
import gameExceptions.InvalidItemException;
import gameExceptions.InvalidRoomException;

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
public class Adventure 
{
	private Scanner input;
	private static Room currentRoom; 
	private GameController gc; 
	static boolean displayStatus; 
	
	/** Constructor: Adventure
	  * 
	  * The Adventure constructor initializes the scanner for user input
	  * and it creates the game controller. 
	  */
	public Adventure()
	{
		input = new Scanner(System.in); 
		try
		{
			gc = new GameController(); 
		}catch(InvalidFileException ex)
		{
			System.out.println(ex.getMessage()); 
			System.exit(0);
		}
	}
	
	/** Method: playGame
	  * 
	  * The playGame method contains the game loop and is responsible for
	  * accepting user commands and passing them to the game controller. 
	  * It is also responsible for handling many of the error messages that come with
	  * commands. 
	  */
	private void playGame()
	{
		System.out.println(gc.introduceGame()); 
		currentRoom = GameController.getMap().getFirstRoom(); 
		
		String command = ""; 
		displayStatus = true; 
		
		while(true)
		{	
			if(displayStatus)
				System.out.println(currentRoom.display()); 
			
			displayStatus = false; 
			
			if(!currentRoom.isVisited())
				currentRoom.setVisited(true);
			
			System.out.print("\nWhat do you want to do?: "); 
			command = input.nextLine(); 
			
			String response;
			
			try
			{
				response = gc.executeCommand(command, currentRoom);
			}catch(InvalidExitException|InvalidCommandException|InvalidItemException|InvalidRoomException ex)
			{
				response = ex.getMessage(); 
			}
			
			if(response.equalsIgnoreCase("EXIT_GAME"))
			{
				System.out.println("\nHope you've had fun."); 
				break; 
			}else
				System.out.println(response); 	
		}
	}
	
	/** Method: setRoom
	  * 
	  * This method is used to change the player's current room. 
	  * @param Room the new Room to set as the current
	  */
	public static void setRoom(Room room)
	{
		currentRoom = room; 
		displayStatus = true; 
	}
	
	/** Method: Main
	  * 
	  * This is Java's main method and it only serves to start loading
	  * the game. 
	  * @param args Java's default command-line arguments
	  */
	public static void main(String[] args) 
	{
		new Adventure().playGame(); 
	}

}

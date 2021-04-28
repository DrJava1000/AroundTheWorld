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
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 * 
 * This class is the main class. It contains the game loop
 * responsible for obtaining user input and passing it to the game controller 
 * for processing. 
 */
public class Adventure 
{
	private Scanner commandParser;
	private static Room currentRoom; 
	private GameController gc; 
	static boolean displayRoom;
	static boolean displayMenu; 

	/** Constructor: Adventure
	 * 
	 * The Adventure constructor initializes the scanner for command input
	 * and it creates the game controller. 
	 */
	public Adventure()
	{
		commandParser = new Scanner(System.in); 
		
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
		displayRoom = false; 
		displayMenu = true; 

		String command; 
		currentRoom = new Room(); 
		
		while(true)
		{	
			if(displayRoom) // display room
				System.out.println(currentRoom.display()); 

			if(displayMenu) // display game menu
			{
				System.out.println("Around The World \n");
				System.out.println("New Game (new [PlayerName]), Load (load [PlayerName]) , or Exit (exit)"); 
			}else
			{
				if(!currentRoom.isVisited())
					currentRoom.setVisited(true);

				// display monster if one available
				if(currentRoom.getMonster()!=null) 
				{
					if(!currentRoom.getMonster().isDefeated()) 
					{
						System.out.println("\nMONSTER_INCOMING: " + currentRoom.getMonster().getMonsterDescription() + "\n");
					}
				}

				// display puzzle if one available
				if(currentRoom.getPuzzle()!=null)
				{
					if(!currentRoom.getPuzzle().isSolved())
					{
						System.out.println("\nPUZZLE_INCOMING: " + currentRoom.getPuzzle().getProblem() + "\n");
					}
				}
		    }

			System.out.print("\nEnter Here: "); 
			command = commandParser.nextLine(); 

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

	public static void setRoom(Room room)
	{
		currentRoom = room; 
		displayRoom = true; 
		displayMenu = false; 
	}
	
	public static Room getRoom()
	{
		return currentRoom; 
	}
	
	public static boolean getDisplayMenuStatus()
	{
		return displayMenu; 
	}

	/** Method: Main
	 * 
	 * This is Java's main method and it only serves to start loading
	 * the game. 
	 * @param args Java's default command-line arguments
	 * @throws InvalidFileException 
	 */
	public static void main(String[] args) throws InvalidFileException 
	{
		new Adventure().playGame();
	}

}

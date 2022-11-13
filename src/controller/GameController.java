package controller;

import gameExceptions.InvalidCommandException;
import gameExceptions.InvalidExitException;
import gameExceptions.InvalidFileException;
import gameExceptions.InvalidItemException;
import gameExceptions.InvalidRoomException;
import model.MapModel;
import view.Adventure;

/**Class: GameController 
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 * This class represents the game controller. It is responsible for 
 * initiating the loading of the game map and it serves to pass user 
 * commands to the commands manager for analysis. 
*/
public class GameController 
{
	private static MapModel map; 
	private Commands commands; 
	private static Player currentPlayer; 
	
	/** Constructor: GameController
	  * 
	  * The GameController constructor initializes the commands manager, the map, 
	  * and it initiates the loading of the map from text files. 
	  */
	public GameController() throws InvalidFileException
	{
		commands = new Commands(); 
		map = new MapModel();
	}
	
	/** Method: getMap
	  *
	  * This method gets the game's map. 
	  * 
	  * @return a reference to the game's map
	  */
	public static MapModel getMap()
	{
		return map;
	}
	
	/** Method: executeCommand
	  * 
	  * This method passes user commands to the commands manager. 
	  * 
	  * @param cmd the command the user entered
	  * @param room the player's current room
	  * @return the response that the command elicits 
	  */
	public String executeCommand(String cmd, Room room) throws InvalidCommandException, InvalidExitException, InvalidItemException, InvalidRoomException
	{
		return commands.executeCommand(cmd, room); 
	}

	public static Player getCurrentPlayer() 
	{
		return currentPlayer;
	}

	public static void setCurrentPlayer(Player player) 
	{
		currentPlayer = player;
	}
	
	public static void setCurrentRoom(Room room)
	{
		Adventure.getRoom().storeVisited(currentPlayer);
		Adventure.setRoom(room);
		map.storePlayerCurrentRoom(currentPlayer, room);
	}
}

package controller;

import gameExceptions.InvalidCommandException;
import gameExceptions.InvalidExitException;
import gameExceptions.InvalidFileException;
import gameExceptions.InvalidItemException;
import gameExceptions.InvalidRoomException;
import model.MapModel;

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
	
	/** Method: retrieveByID
	  * 
	  * This method acts as a wrapper for the method getRoom() in the MapModel class. 
	  * It gets the Room associated with an ID. If not possible, it creates a new
	  * Room with that id and passes it. 
	  * @param curRoom the ID for the room that is to be retrieved
	  * @return a Room containing that id
	  */
	public Room retrieveByID(int curRoom)
	{
		return map.getRoom(curRoom); 
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
}

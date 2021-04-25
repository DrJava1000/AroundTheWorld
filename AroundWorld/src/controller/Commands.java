package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gameExceptions.InvalidCommandException;
import gameExceptions.InvalidExitException;
import gameExceptions.InvalidItemException;
import gameExceptions.InvalidRoomException;
import view.Adventure;

/**Class: Commands 
 * @author Ryan Gambrell
 * @version 2.0 
 * Course: ITEC 3860 Spring 2021
 * Written: March 21, 2021
 * 
 * This class is the commands class. It is responsible for validating user commands
 * and for executing those commands. 
*/
public class Commands 
{
	public static List<String> VALID_DIRECTIONS = Arrays.asList("west", "north", "south", "east", "up", "down"); 
	public static List<String> VALID_COMMANDS = Arrays.asList("move", "look", "inspect", "get", "remove", "backpack", "exit"); 
	public static List<String> MONSTER_COMMANDS = Arrays.asList("fight", "run");
	public static List<String> GAME_COMMANDS = Arrays.asList("save", "load", "quit");
	private Player player; 
	private Monster monsters;
	private Puzzle puzzles;
	
	/** Constructor: Commands
	  * 
	  * Initialize the player (and their inventory). 
	  */
	public Commands()
	{
		player = new Player(); 
	}
	
	/**Method: calcScore
	 * 
	 * This method calculates the score depending on the users
	 * choice when dealing with a puzzle or monster
	 * 
	 * count n# of monster completed and return score
	 *@return the final score of user
	 **/
	public int calcScore()
	{
		return player.getNumOfDefeats() * player.healthPoints();
	}
	
	/** Method: validateCommand
	  * 
	  * This methods takes a user command and checks to see if that
	  * command is allowed by the game. 
	  * @param userCmd the user-entered command without additional parameters
	  * @return whether the command is allowed or not
	  */
	private boolean validateCommand(String userCmd)
	{
		for(String validCom : VALID_COMMANDS)
		{
			if(userCmd.equalsIgnoreCase(validCom))
				return true; 
		}
		
		return false; 
	}

	/** Method: executeCommand
	  * 
	  * Return a response based on what command is entered. 
	  * @param userCmd the full (with parameters) user-entered command 
	  * @param currentRoom the player's current room
	  * @return the response to an executed command
	  */
	public String executeCommand(String userCmd, Room currentRoom) throws InvalidCommandException, InvalidExitException, InvalidItemException, InvalidRoomException
	{
		String pureCommand = userCmd.split(" ")[0]; 
		
		if(!validateCommand(pureCommand))
			throw new InvalidCommandException("\nThis is not a valid command. Try Again.");
		if(pureCommand.equalsIgnoreCase("look"))
			return look(currentRoom); 
		if(pureCommand.equalsIgnoreCase("backpack"))
			return player.printInventory(); 
		if(pureCommand.equalsIgnoreCase("exit"))
			return "EXIT_GAME";
		else 
		{
			String parameter; 
			
			try
			{
				parameter = userCmd.split(" ")[1];
			}catch(ArrayIndexOutOfBoundsException ex)
			{
				throw new InvalidCommandException("\nYou have not entered an acceptable parameter for the chosen command."); 
			}
			
			if(pureCommand.equalsIgnoreCase("move"))
				return move(parameter, currentRoom); 
			if(pureCommand.equalsIgnoreCase("inspect"))
				return inspect(parameter, currentRoom); 
			if(pureCommand.equalsIgnoreCase("get"))
				return pickUp(parameter, currentRoom); 
			if(pureCommand.equalsIgnoreCase("remove"))
				return drop(parameter, currentRoom);
//			if(pureCommand.equalsIgnoreCase("fight"))
//				return fight(parameter, currentRoom);
//			if(pureCommand.equalsIgnoreCase("run"))
//				return run(parameter, currentRoom);
		}
		
		return ""; 
	}
	
	/** Method: validateDirection
	  * 
	  * This method determines whether a user-entered direction is allowed. 
	  * @param direction user-entered direction
	  * @param currentRoom the player's current room
	  * @return whether the user-entered direction is allowed by the move command
	  */
	private boolean validateDirection(String direction, Room currentRoom)
	{
		for(String validDir : VALID_DIRECTIONS)
		{
			if(direction.equalsIgnoreCase(validDir))
			{
				return true; 
			}	
		}
		
		return false; 
	}
	
	/** Method: move
	  * 
	  * This method moves a player to a different location and updates the player's 
	  * current room.
	  * @param direction user-entered direction
	  * @param currentRoom the player's current room
	  * @return a response indicating where a player is going or a response telling
	  * them that a direction or exit isn't possible
	  */
	private String move(String direction, Room currentRoom) throws InvalidExitException, InvalidRoomException
	{
		if(!validateDirection(direction, currentRoom))
			throw new InvalidExitException("\nThis is not a valid direction for the move command.");  
		
		// up and down are alia for north and south 
		if(direction.equalsIgnoreCase("up"))
			direction = "north"; 
		if(direction.equalsIgnoreCase("down"))
			direction = "south"; 
		
		if(!currentRoom.validDirection(direction))
			throw new InvalidRoomException("\nThis is not a valid exit for this room.");  
		
		ArrayList<Exit> exits = currentRoom.getExits(); 
		
		for(Exit exit : exits)
		{
			if(exit.getDirection().equalsIgnoreCase(direction))
			{
				Adventure.setRoom(GameController.getMap().getRoom(exit.getRoomNum())); 
				return "\nGoing " + exit.getDirection() + "\n"; 
			}
		}
		
		return ""; 
	}
	
	/** Method: look
	  * 
	  * This method is a wrapper for Room's displayFullRoom().
	  * It is meant to get everything involved with a room. This includes
	  * name, exits, description, etc. 
	  * @param currentRoom the player's current room
	  * @return all the properties of a room in a single formatted string
	  */
	private String look(Room currentRoom)
	{	
		return currentRoom.displayFullRoom(); 
	}
	
	/** Method: inspect
	  *
	  * This method is used to get the description of an item
	  * found within a specific room. 
	  * @param item a user-entered item
	  * @param room the player's current room
	  * @return an item's description
	  */
	private String inspect(String item, Room room) throws InvalidItemException
	{
		for(Item roomItem : room.getItems())
		{
			if(roomItem.getItemName().equalsIgnoreCase(item))
			{
				return roomItem.getItemDesc(); 
			}
		}
		
		throw new InvalidItemException("\nThis item isn't in the room and cannot be inspected."); 
	} 
	
	/** Method: pickUp
	  * 
	  * Add an item to a user's inventory. 
	  * @param item a user-entered item
	  * @param room the player's current room
	  * @return a clarification that the item has been 
	  * added to a player's inventory
	  */
	private String pickUp(String item, Room room) throws InvalidItemException 
	{
		for(Item roomItem : room.getItems())
		{
			if(roomItem.getItemName().equalsIgnoreCase(item))
			{
				room.removeItem(roomItem);
				player.addItem(roomItem); 
				return "\nYou have added " + roomItem.getItemName() + " to your inventory.\n"; 
			}
		}
		
		throw new InvalidItemException("\nThe requested item doesn't exist in this room and cannot be picked up."); 
	}
	
	/** Method: drop
	  * Convert calendar date into Julian day.
	  * Note: This algorithm is from Press et al., Numerical Recipes
	  * in C, 2nd ed., Cambridge University Press, 1992
	  * @param item a user-entered item
	  * @param room the player's current room
	  * @return a clarification that the item has been removed from a 
	  * player's inventory
	  */
	private String drop(String item, Room room) throws InvalidItemException
	{
		player.getInventory(); 
		
		for(Item playerItem : player.getInventory())
		{
			if(item.equalsIgnoreCase(playerItem.getItemName()))
			{
				room.addItem(playerItem);
				player.removeItem(playerItem); 
				return "\nYou have dropped " + playerItem.getItemName() + " in " + room.getName() + ".\n"; 
			}
		}
		
		throw new InvalidItemException("\nThe requested item doesn't exist in your inventory."); 
	}
}

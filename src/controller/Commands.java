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
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 * 
 * This class is the commands class. It is responsible for validating user commands
 * and for executing those commands. 
 */
public class Commands 
{
	public static List<String> VALID_DIRECTIONS = Arrays.asList("west", "north", "south", "east", "up", "down"); 
	public static List<String> VALID_COMMANDS = Arrays.asList("move", "look", "inspect", "get", "remove", "backpack", "exit", "fight", "run"); 
	private Player player; 
	private InventoryOperations inventory; 

	/** Constructor: Commands
	 * 
	 * Initialize the player, healthPoints, (and their inventory). 
	 */
	public Commands()
	{
		player = new Player(); 
		player.setHP(100);
		inventory = new InventoryOperations(); 
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
		boolean monster = currentRoom.getMonster()!=null;
		boolean puzzle = currentRoom.getPuzzle()!=null;

		if(monster) 
    {
			if(!currentRoom.getMonster().isDefeated()) 
      {
				return monster(currentRoom, userCmd);
			}
		}

		if(puzzle) 
    {
			if(!currentRoom.getPuzzle().isSolved())
			{
				return puzzle(currentRoom, userCmd);
			}

		}

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
				inventory.addToInventory(player, room, roomItem);
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
				inventory.removeFromInventory(player, room, playerItem);
				return "\nYou have dropped " + playerItem.getItemName() + " in " + room.getName() + ".\n"; 
			}
		}

		throw new InvalidItemException("\nThe requested item doesn't exist in your inventory."); 
	}

	private String monster(Room room, String cmd)
	{
		if(cmd.equals("fight")){
			return fight(room);
		}
		if(cmd.equals("run")) {
			int run = room.getRoomID()-1;
			Adventure.setRoom(GameController.getMap().getRoom(run)); 
			return "You ran away.";
		}
		else return checkItem(cmd, room);
	}

	private String fight(Room room) 
	{

		return "What item will you use?";
	}

	private String checkItem(String item, Room room)
	{
		ArrayList<Item> items = player.getInventory();
		int itemID = 0;
		for(Item i : items) {
			if(item.equals(i.getItemName()))
			{
				itemID = i.getItemID();
			}
			else return "You do not have that item.";
		}
		if(itemID==room.getMonster().getRightItemChoice()) {
			room.getMonster().setDefeated(true);;
			return room.getMonster().getRightChoice();
		}
		else
		{
			player.setHP(player.getHP()-10);
			return room.getMonster().getWrongChoice();
		}
	}

	private int run(Room room)
	{
		int previousRoom = room.getRoomID()-1;
		return previousRoom;
	}

	private String puzzle(Room room, String cmd) {
		if(cmd.toLowerCase().equals(room.getPuzzle().getAnswer().toLowerCase()))
		{
			room.getPuzzle().setSolved(true);
			return "Yay you got it!";
		}
		else 
		{
			player.setHealthPoints(player.getHealthPoints()-5);
			return room.getPuzzle().getWRONG_ANSWER();
		}
	}
}

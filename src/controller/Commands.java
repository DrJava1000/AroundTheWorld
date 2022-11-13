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
	private static List<String> VALID_DIRECTIONS = Arrays.asList("west", "north", "south", "east", "up", "down"); 
	private static List<String> VALID_ROOM_COMMANDS = Arrays.asList("move", "look", "inspect", 
			"get", "remove", "backpack", 
			"exit", "score", "health");
	private static List<String> VALID_MENU_COMMANDS = Arrays.asList("new", "load", "exit"); 
	private static List<String> VALID_MONSTER_COMMANDS = Arrays.asList("use", "run", "tip", "exit", 
			"backpack", "health", "score");
 	private InventoryOperations inventoryOps;
	
	/** Constructor: Commands
	 * 
	 * Initialize the player, healthPoints, (and their inventory). 
	 */
	public Commands()
	{
		Player newPlayer = new Player(); 
		newPlayer.setHP(100);
		GameController.setCurrentPlayer(newPlayer); 
		inventoryOps = new InventoryOperations(); 
	}

	/** Method: validateRoomCommand
	 * 
	 * This methods takes a user room command and checks to see if that
	 * command is allowed by the game. 
	 * @param userCmd the user-entered command without additional parameters
	 * @return whether the command is allowed or not
	 */
	private boolean validateRoomCommand(String userCmd)
	{
		for(String validCom : VALID_ROOM_COMMANDS)
		{
			if(userCmd.equalsIgnoreCase(validCom))
				return true; 
		}
		return false; 
	}
	
	/** Method: validateMenuCommand
	 * 
	 * This methods takes a user menu command and checks to see if that
	 * command is allowed by the game. 
	 * @param userCmd the user-entered command without additional parameters
	 * @return whether the command is allowed or not
	 */
	private boolean validateMenuCommand(String userCmd)
	{
		for(String validCom : VALID_MENU_COMMANDS)
		{
			if(userCmd.equalsIgnoreCase(validCom))
				return true; 
		}

		return false; 
	}
	
	private boolean validateMonsterCommand(String userCmd)
	{
		for(String validCom : VALID_MONSTER_COMMANDS)
		{
			if(userCmd.equalsIgnoreCase(validCom))
				return true; 
		}

		return false; 
	}
	
	/**Method: executeCommand
	 * 
	 * Return a response based on what command is entered. 
	 * @param userCmd the full (with parameters) user-entered command 
	 * @param currentRoom the player's current room
	 * @return the response to an executed command
	 */
	public String executeCommand(String userCmd, Room currentRoom) throws InvalidCommandException, InvalidExitException, InvalidItemException, InvalidRoomException
	{
		String pureCommand = userCmd.split(" ")[0]; 
		
		// enter menu sequence 
		if(Adventure.getDisplayMenuStatus())
		{
			return executeMenuCommands(userCmd); 
		}

		// enter into monster sequence if not
		// already completed 
		if(currentRoom.getMonster()!=null) 
        {
			if(!currentRoom.getMonster().isDefeated()) 
            {
				return executeMonsterCommands(currentRoom, userCmd);
			}
		}

		// enter into puzzle sequence
		// if not already completed 
		if(currentRoom.getPuzzle()!=null) 
        {
			if(!currentRoom.getPuzzle().isSolved())
			{
				return executePuzzleCommands(currentRoom, userCmd);
			}
		}

		// commands without parameters
		if(!validateRoomCommand(pureCommand))
			throw new InvalidCommandException("\nThis is not a valid room command. Try Again.\n");
		if(pureCommand.equalsIgnoreCase("look"))
			return look(currentRoom); 
		if(pureCommand.equalsIgnoreCase("backpack"))
			return GameController.getCurrentPlayer().printInventory(); 
		if(pureCommand.equalsIgnoreCase("exit"))
			return "EXIT_GAME";
		if(pureCommand.equalsIgnoreCase("health"))
			return "\nPlayer's Health: " + GameController.getCurrentPlayer().getHP() + "\n"; 
		if(pureCommand.equalsIgnoreCase("score"))
			return "\nPlayer's Score: " + GameController.getCurrentPlayer().getScore() + "\n"; 
		else 
		{
			String parameter; 

			try
			{
				parameter = userCmd.split(" ")[1];
			}catch(ArrayIndexOutOfBoundsException ex)
			{
				throw new InvalidCommandException("\nYou have not entered an acceptable parameter "
						+ "for the chosen command.\n"); 
			}

			// commands with parameters
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
			throw new InvalidExitException("\nThis is not a valid direction for the move command.\n");  

		// up and down are alias for north and south 
		if(direction.equalsIgnoreCase("up"))
			direction = "north"; 
		if(direction.equalsIgnoreCase("down"))
			direction = "south"; 

		if(!currentRoom.validDirection(direction))
			throw new InvalidRoomException("\nThis is not a valid exit for this room.\n");  

		ArrayList<Exit> exits = currentRoom.getExits(); 

		for(Exit exit : exits)
		{
			if(exit.getDirection().equalsIgnoreCase(direction))
			{
				GameController.setCurrentRoom(GameController.getMap().getRoom(GameController.getCurrentPlayer(), exit.getRoomNum())); 
				return "\nGoing " + exit.getDirection() + "...\n"; 
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

		throw new InvalidItemException("\nThis item isn't in the room and cannot be inspected.\n"); 
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
				inventoryOps.addToInventory(GameController.getCurrentPlayer(), room, roomItem);
				return "\nYou have added " + roomItem.getItemName() + " to your inventory.\n"; 
			}
		}

		throw new InvalidItemException("\nThe requested item doesn't exist in this room and cannot be picked up.\n"); 
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
		Item [] inventory = GameController.getCurrentPlayer().getInventory(); 
		
		for(Item playerItem : inventory)
		{
			if(playerItem == null)
				throw new InvalidItemException("\nYou don't have that item in your inventory.\n"); 
			
			if(item.equalsIgnoreCase(playerItem.getItemName()))
			{
				inventoryOps.removeFromInventory(GameController.getCurrentPlayer(), room, playerItem);
				return "\nYou have dropped " + playerItem.getItemName() + " in " + room.getName() + ".\n"; 
			}
		}

		throw new InvalidItemException("\nThe requested item doesn't exist in your inventory.\n"); 
	}

	/** Method: executeMenuCommands
	 * 
	 * Return a response based on what command is entered at the menu. 
	 * @param userCmd the full (with parameters) user-entered command 
	 * @return the response to an executed command
	 */
	private String executeMenuCommands(String userCmd) throws InvalidCommandException
	{
		String pureCommand = userCmd.split(" ")[0]; 
		String parameter; 
		
		if(!validateMenuCommand(pureCommand))
			throw new InvalidCommandException("\nThis is not a valid menu command. Try Again.\n");
		
		if(pureCommand.equalsIgnoreCase("exit"))
			return "EXIT_GAME"; 
		
		try
		{
			parameter = userCmd.split(" ")[1];
		}catch(ArrayIndexOutOfBoundsException ex)
		{
			throw new InvalidCommandException("\nYou have a missing parameter for the chosen menu command.\n"); 
		}
		
		if(pureCommand.equalsIgnoreCase("new"))
			return newGame(parameter); 
		if(pureCommand.equalsIgnoreCase("load"))
			return loadGame(parameter); 
		return ""; 
	}
	
	// wrapper for call to GameController's new game method
	private String newGame(String playerName)
	{
		return GameController.getMap().newPlayerProfile(playerName); 
	}
	
	// wrapper for call to GameController's load game method
	private String loadGame(String playerName)
	{
		return GameController.getMap().loadPlayerProfile(playerName); 
	}
	
	/** Method: executeMonsterCommands
	 * 
	 * Return a response based on what command is entered during a monster a sequence. 
	 * @param userCmd the full (with parameters) user-entered command 
	 * @param room the player's current room
	 * @return the response to an executed command
	 */
	private String executeMonsterCommands(Room room, String cmd) throws InvalidCommandException
	{
		String pureCommand = cmd.split(" ")[0]; 
		
		if(!validateMonsterCommand(pureCommand))
			throw new InvalidCommandException("\nThis is not a valid command at the time. Try Again.\n");
		
		if(pureCommand.equalsIgnoreCase("backpack"))
			return GameController.getCurrentPlayer().printInventory(); 
		if(pureCommand.equalsIgnoreCase("exit"))
			return "EXIT_GAME";
		if(pureCommand.equalsIgnoreCase("health"))
			return "\nPlayer's Health: " + GameController.getCurrentPlayer().getHP() + "\n"; 
		if(pureCommand.equalsIgnoreCase("score"))
			return "\nPlayer's Score: " + GameController.getCurrentPlayer().getScore() + "\n"; 
		
		if(pureCommand.equalsIgnoreCase("tip")) 
		{
			return "\n" + room.getMonster().getTip() + "\n";
		}
		
		if(pureCommand.equalsIgnoreCase("run")) 
		{
			int run = room.getRoomID()-1;
			GameController.setCurrentRoom(GameController.getMap().getRoom(GameController.getCurrentPlayer(), run)); 
			return "\nYou ran back to the previous room.\n";
		}
		
		if(pureCommand.equalsIgnoreCase("use"))
		{
			String parameter; 
			try
			{
				parameter = cmd.split(" ")[1];
			}catch(ArrayIndexOutOfBoundsException ex)
			{
				throw new InvalidCommandException("\nYou have not entered a parameter "
						+ "for the chosen monster command.\n"); 
			}
			
			return checkItem(parameter, room);
		}
		
		return ""; 
	}

	/** Method: checkItem
	 * 
	 * Returns a string denoting whether an item is allowed 
	 * during a monster sequence. 
	 * @param item user-entered item
	 * @param room the player's current room
	 * @return the response to an executed command
	 */
	private String checkItem(String itemName, Room room)
	{
		// search player's current items 
		Item[] items = GameController.getCurrentPlayer().getInventory();
		
		Item correctItem = null; 
		int itemID = -100; 
		
		// search player's inventory for 
		// selected item first 
		for(Item item : items) 
		{
			if(item != null && item.getItemName().equalsIgnoreCase(itemName))
			{
				correctItem = item; 
				itemID = item.getItemID();
				break; 
			}else
				return "\nYou do not have that item.\n";
		}
		
		// if players enter correct item
		if(itemID==room.getMonster().getRightItemChoice()) 
		{
			room.getMonster().saveDefeated(GameController.getCurrentPlayer());
			GameController.getCurrentPlayer().updateScore(10);
			return "\n" + room.getMonster().getRightChoice() + "\n";
		}
		else // if players don't enter correct item
		{
			GameController.getCurrentPlayer().updateHP(-10);
			return "\n" + room.getMonster().getWrongChoice() + "\n";
		}
	}
	
	/** Method: executePuzzleCommands
	 * 
	 * Return a response based on what command is entered during a puzzle sequence. 
	 * @param userCmd the full (with parameters) user-entered command 
	 * @param room the player's current room
	 * @return the response to an executed command
	 * @throws InvalidCommandException 
	 */
    private String executePuzzleCommands(Room room, String cmd) throws InvalidCommandException 
    {	
    	if(cmd.equalsIgnoreCase("tip")) 
		{
			return room.getPuzzle().getTip();
		}
		
		if(cmd.equalsIgnoreCase(room.getPuzzle().getCorrectAnswer()))
		{
			room.getPuzzle().saveSolved(GameController.getCurrentPlayer());
			GameController.getCurrentPlayer().updateScore(10); 
			return "\nYay you got it! Adding +10 to Score...\n";
		}
		else 
		{
			GameController.getCurrentPlayer().updateHP(-10);
			return "\n" + Puzzle.WRONG_ANSWER + "\n";
		}
	}
}

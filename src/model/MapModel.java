package model;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import controller.Exit;
import controller.Item;
import controller.Monster;
import controller.Puzzle;
import controller.Room;
import gameExceptions.InvalidFileException;

/**Class: MapModel 
 * @author Ryan Gambrell
 * @version 2.0 
 * Course: ITEC 3860 Spring 2021
 * Written: March 21, 2021
 * 
 * This class is responsible for the map. Specifically, it is responsible for loading
 * the introduction, the rooms, and items from individual text files while also
 * maintaining a collection of all rooms and items. 
*/
public class MapModel 
{
	private ArrayList<Room> rooms; 
	private ArrayList<Item> items; 
	private String gameIntroduction; 
	private File introFile; 
	private File roomsFile; 
	private File itemsFile; 
	
	/** Constructor: MapModel
	  * 
	  * The MapModel constructor is used to select the text files
	  * that the game should use and it is responsible for initializing 
	  * the collections use to store items and rooms. 
	  */
	public MapModel()
	{
		introFile = new File("Introduction.txt"); 
		roomsFile = new File("Rooms.txt");
		itemsFile = new File("Items.txt"); 
		
		gameIntroduction = ""; 
		rooms = new ArrayList<Room>(); 
		items = new ArrayList<Item>(); 
	}
	
	/** Method: loadGame
	  * 
	  * This method is the all-encompassing method used to initiate the
	  * loading of the map's rooms, the game's items, and the game's introduction 
	  * from text files. 
	  */
	public void loadGame() throws InvalidFileException
	{
		readRooms(); 
		readItems(); 
		readGameIntroduction(); 
	}
	
	/** Method: readGameIntroduction
	  * 
	  * This method reads the introduction file and stores it as a
	  * single formatted string representative of the user's original
	  * introduction message. 
	  */
	private void readGameIntroduction() throws InvalidFileException
	{
		Scanner introParser = null; 
		
		try
		{
			introParser = new Scanner(introFile); 
		}catch(FileNotFoundException fileErr)
		{
			throw new InvalidFileException("The introduction file \"" + introFile.getAbsolutePath() + "\" couldn't be found.");
		}
		
		while(introParser.hasNext())
		{
			gameIntroduction += introParser.nextLine() + "\n";
		}
		
		introParser.close(); 
	}
	
	/** Method: Method Name
	  * 
	  * This method reads the rooms file and it generates
	  * and updates each room with a user-specified id, name, description, 
	  * and a list of exits. 
	  */
	private void readRooms() throws InvalidFileException
	{
		Scanner roomsParser = null; 
		
		try
		{
			roomsParser = new Scanner(roomsFile); 
		}catch(FileNotFoundException fileErr)
		{
			throw new InvalidFileException("The rooms file \"" + roomsFile.getAbsolutePath() + "\" couldn't be found."); 
		}
		
		int roomID = 0; 
		String roomName = ""; 
		String description; 
		ArrayList<Exit> exits; 
		ArrayList<Item> items;
		Monster monster;
		Puzzle puzzle;
		
		while(roomsParser.hasNext())
		{
			exits = new ArrayList<Exit>(); 
			items = new ArrayList<>();
			monster = new Monster();
			puzzle = new Puzzle();
			
			roomID = Integer.parseInt(roomsParser.nextLine()); 
			roomName = roomsParser.nextLine(); 
			
			// build description and store in temp list
			description = roomsParser.nextLine(); 
			
			// build individual exits and store in temp list 
			Exit currentExit;
			String currentLine;
			
			roomsParser.nextLine();
			currentLine = roomsParser.nextLine();
			while(!currentLine.equals("----"))
			{
				String [] exitDefinition = currentLine.split(" ");
				currentExit = new Exit(); 
				currentExit.buildExit(exitDefinition[0], Integer.parseInt(exitDefinition[1]));
				exits.add(currentExit);
				currentLine = roomsParser.nextLine(); 
			}
			
				
			// update already existing room or create new room 
			
			Room currentRoom = getRoom(roomID); 
			currentRoom.setName(roomName);
			currentRoom.setDescription(description);
			currentRoom.setExits(exits); 
			currentRoom.setItems(items);
			rooms.add(currentRoom);
		}
					
		roomsParser.close(); 
	}
	
	/** Method: readItems
	  * 
	  * This method reads the items file and it generates and updates
	  * a list of items with user-defined id s, names, and descriptions. It
	  * also uses that file to place those items into corresponding rooms. 
	  */
	private void readItems() throws InvalidFileException
	{
		Scanner itemsParser = null; 
		
		try
		{
			itemsParser = new Scanner(itemsFile); 
		}catch(FileNotFoundException fileErr)
		{
			throw new InvalidFileException("The items file \"" + itemsFile.getAbsolutePath() + "\" couldn't be found.");
		}
		
		int itemID = 0; 
		String itemName = ""; 
		String description = ""; 
		int initialRoom = 0; 
		
		while(itemsParser.hasNext())
		{
			// fetch item id on first line
			itemID = itemsParser.nextInt(); 
			itemsParser.nextLine(); 
			
			// fetch item name on second line
			itemName = itemsParser.nextLine(); 
			
			// fetch description only on third line
			description = itemsParser.nextLine(); 
			
			// fetch the item's location (via room ID) on the fourth line 
			initialRoom = itemsParser.nextInt(); 
			itemsParser.nextLine();
			
			// update current item or create new one
			Item currentItem = getItem(itemID); 
			currentItem.setItemName(itemName);
			currentItem.setItemDesc(description);
			items.add(currentItem); 
				
			// add each item to the room whose id is listed last 
			getRoom(initialRoom).addItem(currentItem);
		}
		
		itemsParser.close(); 
	}	
	
	
	/** Method: getGameIntroduction
	  * 
	  * This method gets the game's introduction. 
	  * @return a formatted String containing the game's introduction 
	  */
	public String getGameIntroduction()
	{
		return gameIntroduction; 
	}
	
	/** Method: getRoom
	  * 
	  * This method gets the room associated with a room ID or
	  * it returns a new room (with that ID) if it cannot be found. 
	  * @param roomID the ID used to identify a specific room 
	  * @return Room a reference to a Room with that ID
	  */
	public Room getRoom(int roomID)
	{
		for(Room currentRoom : rooms)
		{
			if(currentRoom.getRoomID() == roomID)
				return currentRoom; 
		}
		
		return new Room(roomID); 
	}
	
	/** Method: getAllRooms() 
	  * 
	  * This method gets the map's collection of rooms. 
	  * 
	  * @return a reference to the MapModel's collection of rooms 
	  */
	public ArrayList<Room> getAllRooms()
	{
		return rooms; 
	}
	
	/** Method: getFirstRoom
	  * 
	  * This method gets the first room of the map. This room is
	  * the one that is listed first in the rooms file. 
	  * @return Room the first (or starting) room in the game
	  */
	public Room getFirstRoom()
	{
		return rooms.get(0);
	}
	
	/** Method: getItem
	  * 
	  * This method gets the item that is associated with a specific ID. 
	  * @param itemID the ID used to identify a specific item
	  * @return Item the item associated with a specific ID
	  */
	public Item getItem(int itemID)
	{
		for(Item currentItem : items)
		{
			if(currentItem.getItemID() == itemID)
				return currentItem; 
		}
		
		return new Item(); 
	}
	
	/** Method: getItems
	  * 
	  * This method gets a collection of all the items in a specific room. 
	  * @param roomID the ID used to identify a specific room
	  * @return a reference to the collection of items associated with a room
	  */
	public ArrayList<Item> getItems(int roomID)
	{
		return getRoom(roomID).getItems(); 
	}
}

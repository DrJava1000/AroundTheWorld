package model;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import controller.Exit;
import controller.Item;
import controller.Monster;
import controller.Puzzle;
import controller.Room;
import gameExceptions.InvalidFileException;

/**Class: MapModel 
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 * 
 * This class is responsible for the map. Specifically, it is responsible for loading
 * the introduction, the rooms, and items from individual text files while also
 * maintaining a collection of all rooms and items. 
*/
public class MapModel 
{
	private ATWorldDB gameDB; 
	private File dbFile; 
	private File roomsFile; 
	private File itemsFile; 
	private File monstersFile;
	private File puzzlesFile;
	
	/** Constructor: MapModel
	  * 
	  * The MapModel constructor is used to select the text files
	  * that the game should use and it is responsible for initializing 
	  * the collections use to store items and rooms. 
	 * @throws InvalidFileException 
	  */
	public MapModel() throws InvalidFileException
	{
		dbFile = new File("aroundTheWorld.db");
		gameDB = new ATWorldDB(dbFile.getName()); 
		
		// check to see if database already exists 
		// and create it in working directory 
		// if it doesn't exist 
        if(dbFile.exists() == false)
        {
        	roomsFile = new File("Rooms.txt");
        	itemsFile = new File("Items.txt"); 
        	monstersFile = new File("Monsters.txt");
        	puzzlesFile = new File("Puzzles.txt");
        	
        	gameDB.connectGameDB();
        	gameDB.buildTables();
        	
        	loadGame();  
        }else
        	gameDB.connectGameDB(); 
	}
	
	/** Method: loadGame
	  * 
	  * This method is the all-encompassing method used to initiate the
	  * loading of the map's rooms, the game's items, and the game's introduction 
	  * from text files. 
	  */
	private void loadGame() throws InvalidFileException
	{
		readRooms(); 
		readItems(); 
		readMonsters();
		readPuzzles();
	}
	
	/** Method: readRooms()
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

		while(roomsParser.hasNext())
		{
			exits = new ArrayList<Exit>(); 		
			roomID = Integer.parseInt(roomsParser.nextLine()); 
			roomName = roomsParser.nextLine(); 
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
			
			// add new room 
			gameDB.addRoom(roomID, roomName, description, exits); 
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
			
			// add new item 
			gameDB.addItem(itemID, itemName, description, initialRoom);
		}
		
		itemsParser.close(); 
	}	
	
	private void readMonsters() throws InvalidFileException 
	{
		Scanner monsterParser = null;
		try
		{
			monsterParser = new Scanner(monstersFile); 
		}catch(FileNotFoundException fileErr)
		{
			throw new InvalidFileException("The monsters file \"" + monstersFile.getAbsolutePath() + 
					"\" couldn't be found.");
		}
		
		int monsterID = 0;
		String monsterName = "";
		String description = "";
		String tip = "";
		int rightItemChoice; 
		String rightChoice = "";
		String wrongChoice = "";
		int initialRoom = 0;
		
		while(monsterParser.hasNext())
		{
			// fetch monsterID on first line
			monsterID = monsterParser.nextInt();
			monsterParser.nextLine();
			
			//fetch monster name on second line
			monsterName = monsterParser.nextLine();
			
			//fetch monster description on third line
			description = monsterParser.nextLine();
			
			//fetch monster tip
			tip = monsterParser.nextLine();
			
			//fetch correct item to use 
			rightItemChoice = monsterParser.nextInt(); 
			monsterParser.nextLine(); 
			
			//fetch monster wrong choice
			wrongChoice = monsterParser.nextLine();
			
			//fetch right choice
			rightChoice = monsterParser.nextLine();
			
			//fetch initial room
			initialRoom = monsterParser.nextInt();
			
			gameDB.addMonster(monsterID, monsterName, description, rightItemChoice, 
					rightChoice, wrongChoice, tip, initialRoom);
		}
		monsterParser.close();
	}
	
	private void readPuzzles() throws InvalidFileException
	{
		Scanner puzzleParser = null;
		
		try
		{
			puzzleParser = new Scanner(puzzlesFile); 
		}catch(FileNotFoundException fileErr)
		{
			throw new InvalidFileException("The puzzles file \"" + puzzlesFile.getAbsolutePath() + 
					"\" couldn't be found.");
		}
		
		int puzzleID = 0;
		int roomID;
		String problem;
		String answer;
		String tip;
		
		while(puzzleParser.hasNext())
		{
			puzzleID = puzzleParser.nextInt();
			puzzleParser.nextLine();
			
			roomID = puzzleParser.nextInt();
			puzzleParser.nextLine();
			
			problem = puzzleParser.nextLine();
			answer = puzzleParser.nextLine();
			tip = puzzleParser.nextLine();
			
			gameDB.addPuzzle(puzzleID, problem, answer, tip, roomID);	
		}
		
		puzzleParser.close();
	}
	
	/** Method: getFirstRoom
	  * 
	  * This method gets the first room of the map. This room is
	  * the one that is listed first in the rooms file. 
	  * @return Room the first (or starting) room in the game
	  */
	public Room getFirstRoom()
	{
		return getRoom(1);
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
		ResultSet roomResult = gameDB.getRoom(roomID); 
		
		Room newRoom = null; 
		ArrayList<Exit> exits = new ArrayList<Exit>(); 
		
		try 
		{
			newRoom = new Room(roomResult.getInt("RoomID")); 
			newRoom.setName(roomResult.getString("Name"));
			newRoom.setDescription(roomResult.getString("Description"));
	    
			String [] sqlTableExits = {"NorthExit", "SouthExit", "EastExit", "WestExit"}; 
			String [] cardinalDirections = {"North", "South", "East", "West"};  
	    
			for(int i = 0; i < 4; i++)
			{
				Exit exit = new Exit(); 
				
				// only build exits where not null
				if(roomResult.getInt(sqlTableExits[i]) == 0)
					continue; 
				
				exit.buildExit(cardinalDirections[i], roomResult.getInt(sqlTableExits[i]));
				exits.add(exit); 
			}
		}catch(SQLException sqlError)
		{
			// throw new Exception()
		}
	    
	    newRoom.setExits(exits);
	    newRoom.setItems(getItems(newRoom.getRoomID()));
	    newRoom.setMonster(getMonster(newRoom.getRoomID())); 
	    newRoom.setPuzzle(getPuzzle(newRoom.getRoomID())); 
	    
		return newRoom; 
	}
	
	private ArrayList<Item> getItems(int roomID)
	{
		ResultSet itemsResult = gameDB.getItems(roomID); 
		
		ArrayList<Item> items = new ArrayList<Item>(); 
		
		try 
		{
			while(itemsResult.next())
			{
				Item currentItem = new Item(); 
				currentItem.setItemID(itemsResult.getInt("ItemID"));
				currentItem.setItemName(itemsResult.getString("Name"));
				currentItem.setItemDesc(itemsResult.getString("Description"));
				currentItem.setRoomID(roomID);
				items.add(currentItem); 
			}
		} catch (SQLException e) 
		{
			// throw new Exception()
		}
		
		return items; 
	}
	
	private Monster getMonster(int roomID)
	{
        ResultSet monsterResult = gameDB.getMonster(roomID); 
		
        Monster newMonster = null; 
        
		try 
		{
			if(monsterResult.next())
			{
				newMonster = new Monster(); 
				newMonster.setMonsterID(monsterResult.getInt("MonsterID"));
				newMonster.setMonsterName(monsterResult.getString("Name"));
				newMonster.setMonsterDescription(monsterResult.getString("Description"));
				newMonster.setRightItemChoice(monsterResult.getInt("CorrectDefenseItem"));
				newMonster.setRightChoice(monsterResult.getString("CorrectDefenseItemResponse"));
				newMonster.setWrongChoice(monsterResult.getString("IncorrectDefenseItemResponse"));
				newMonster.setTip(monsterResult.getString("Tip"));
				newMonster.setDefeated(monsterResult.getBoolean("isDefeated"));
			}
		} catch (SQLException e) 
		{
			// throw new Exception()
		}
		
		return newMonster; 
	}
	
	private Puzzle getPuzzle(int roomID)
	{
        ResultSet puzzleResult = gameDB.getPuzzle(roomID); 
		
        Puzzle newPuzzle = null;
        
		try 
		{
			if(puzzleResult.next())
			{
				newPuzzle = new Puzzle(); 
				newPuzzle.setPuzzleID(puzzleResult.getInt("PuzzleID"));
				newPuzzle.setProblem(puzzleResult.getString("Prompt"));
				newPuzzle.setAnswer(puzzleResult.getString("Answer"));
				newPuzzle.setTip(puzzleResult.getString("Tip"));
				newPuzzle.setSolved(puzzleResult.getBoolean("isCompleted"));
			}
		} catch (SQLException e) 
		{
			// throw new Exception()
		}
		
		return newPuzzle; 
		
	}
}

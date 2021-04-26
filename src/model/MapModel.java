package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException; 

import controller.Exit;
import controller.Item;
import controller.Monster;
import controller.Player;
import controller.Puzzle;
import controller.Room;
import gameExceptions.InvalidFileException;

/**Class: MapModel 
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 * 
 * This class facilitates the initialization of the game's database from text files and it acts
 * as an intermediary component for the database. Database operations are passed along through here
 * and associated methods are responsible for converting the results of database statements to game-defined
 * entities. 
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
	  * The MapModel constructor is responsible for facilitating 
	  * the loading of a current database or the creation and 
	  * formatting of a new one. 
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
	  * loading of the map's rooms, items, monsters, and puzzles from text files
	  * into the backend database. 
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
	  * This method reads the Rooms.txt file and collects
	  * necessary room attributes to send off to the 
	  * database for insertion. 
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
	
	/** Method: readItems()
	  * 
	  * This method reads the Items.txt file and collects
	  * necessary item attributes to send off to the 
	  * database for insertion. 
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
	
	/** Method: readMonsters()
	  * 
	  * This method reads the Monsters.txt file and collects
	  * necessary Monster attributes to send off to the 
	  * database for insertion. 
	  */
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
	
	/** Method: readPuzzles()
	  * 
	  * This method reads the Puzzles.txt file and collects
	  * necessary Puzzles attributes to send off to the 
	  * database for insertion. 
	  */
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
	
	/** Method: getRoom
	  * 
	  * This method gets the room associated with a room ID
	  * 
	  * @param roomID the id associated with a desired room 
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
			
			// While null in database upon creation, the isVisited status will match
			// Unvisited as the getBoolean method returns false for null 
			newRoom.setVisited(roomResult.getBoolean("isVisited"));  
	    
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
	
	/** Method: getItems
	  * 
	  * This method gets the items within a room. 
	  * 
	  * @param roomID the id associated with a desired room 
	  * @return ArrayList<Item> a list containing items within the room 
	  */
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
	
	/** Method: getMonster
	  * 
	  * This method gets the Monster within a Room if it exists. 
	  * 
	  * @param roomID the id associated with a desired room 
	  * @return Monster the monster within the room or null if one isn't found 
	  */
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
	
	/** Method: getPuzzle
	  * 
	  * This method gets the Puzzle within a room if it exists. 
	  * 
	  * @param roomID the id associated with a desired room 
	  * @return Puzzle the puzzle within a room or null if one doesn't exist
	  */
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
	
	public void storeVisited(Player player, Room room)
	{
		
	}
	
	public void storeSolved(Player player, Puzzle puzzle)
	{
		
	}
	
	public void storeDefeated(Player player, Monster monster)
	{
		
	}
	
	public void storeRoomItem(Player player, Room room, Item item)
	{
		
	}
	
	public void storeInventoryItem(Player player, Room room, Item item)
	{
		
	}
}

package model;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Exit;
//import gameExceptions.InvalidFileException;

import java.util.ArrayList;

/**Class: ATWorldDB 
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 * 
 * This class is responsible for interacting with AroundTheWorld's database. Specifically, it is
 * responsible for connecting to the database, for building the database's tables, and for updating
 * and pulling from the database where necessary. 
*/
public class ATWorldDB 
{
	private Connection dbConn; 
	private Statement sqlStmt; 
	private String dbConnURL; 
	
	/** Constructor: ATWorldDB(dbPath)
	  * 
	  * The ATWorldDB constructor is used to register the sqlite database 
	  * driver and to build the connection URL used for accessing both
	  * current and new databases. 
	  */
	public ATWorldDB(String dbPath)
	{
        String dbDriver = "org.sqlite.JDBC";
        
        // register the JDBC driver 
        try 
        {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) 
        {
		    // throw new Exception();
		}
 
        // create the URL needed to make a database connection 
        String databaseType = "jdbc:sqlite";
        dbConnURL = databaseType + ":" + dbPath;
	}
	
	/** Method: connectGameDB
	  * 
	  * This method is used to fetch a database connection and to register a handle for
	  * executing sql queries to that database. If a database doesn't exist, it is created here.  
	  */
	public void connectGameDB()
	{
		// establish a connection 
        try 
        {
			dbConn = DriverManager.getConnection(dbConnURL);
		} catch (SQLException e) 
        {
			// throw new Exception(); 
		} 
        
        try 
        {
			sqlStmt = dbConn.createStatement();
		} catch (SQLException e) 
        {
			// throw new Exception();
		} 
	}
	
	/** Method: buildTables
	  * 
	  * This method is used to both construct the database table creation statements and 
	  * to construct that database using said statements. 
	  */
	public void buildTables()
	{
		String createPlayersSQL = "CREATE TABLE Players(PlayerID int PRIMARY KEY, Name varchar(255),"
				+ " Score int, Location int,"
				+ " Health int, ItemSlot int, ItemSlot2 int, ItemSlot3 int"
				+ ");"; 
		String createMonstersSQL = "CREATE TABLE Monsters(MonsterID int PRIMARY KEY, Name varchar(255),"
				+ " Description varchar(255), CorrectDefenseItem int,"
				+ " CorrectDefenseItemResponse varchar(255), IncorrectDefenseItemResponse varchar(255),"
				+ " Tip varchar(255), Location int, isDefeated bit, Player int);"; 
		String createItemsSQL = "CREATE TABLE Items(ItemID int PRIMARY KEY, Name varchar(255),"
				+ " Description varchar(255), Location int, Player int);"; 
		String createRoomsSQL = "CREATE TABLE Rooms(RoomID int PRIMARY KEY, Name varchar(255),"
				+ " Description varchar(255), NorthExit int,"
				+ " SouthExit int, EastExit int, WestExit int, isVisited bit, Player int);"; 
		String createPuzzlesSQL = "CREATE TABLE Puzzles(PuzzleID int PRIMARY KEY, Prompt varchar(255),"
				+ " Answer varchar(255), Tip varchar(255),"
				+ " Location int, isCompleted bit, Player int);"; 
		
		try
		{
			sqlStmt.executeUpdate(createPlayersSQL); 
			sqlStmt.executeUpdate(createMonstersSQL); 
			sqlStmt.executeUpdate(createItemsSQL); 
			sqlStmt.executeUpdate(createRoomsSQL); 
			sqlStmt.executeUpdate(createPuzzlesSQL); 
		}catch(SQLException e)
		{
			// throw new Exception();
		}
	}
	
	/** Method: getRoom
	  * 
	  * This method is used to execute the SQL statement required for 
	  * finding a specific room entry. 
	  * @parameter roomID the room id used to search for the associated room entry
	  * @return ResultSet containing 1 room entry representing a Room with the specified room id 
	  */
	public ResultSet getRoom(int roomID)
	{
		ResultSet result; 
		try 
		{
			result = sqlStmt.executeQuery("SELECT * FROM Rooms"
					+ " WHERE RoomID=" + roomID + ";");
		} catch (SQLException e) 
		{
			return null; 
			// throw new Exception(); 
		} 
		
		return result; 
	}
	
	/** Method: getItems
	  * 
	  * This method is used to execute the SQL statement required for 
	  * finding all the items within a specific room. 
	  * @parameter roomID the room id used to search for the associated item entries 
	  * @return ResultSet containing item entries that represent items within the specified room 
	  */
	public ResultSet getItems(int roomID)
	{
		ResultSet result; 
		try 
		{
			result = sqlStmt.executeQuery("SELECT * FROM Items"
					+ " WHERE Location=" + roomID + ";");
		} catch (SQLException e) 
		{
			return null; 
			// throw new Exception(); 
		} 
		
		return result; 
	}
	
	/** Method: getMonster
	  * 
	  * This method is used to execute the SQL statement required for 
	  * finding a monster within a specific room.  
	  * @parameter roomID the room id used to search for the associated monster entry
	  * @return ResultSet containing 1 monster entry representing a Monster that exists in that room 
	  */
	public ResultSet getMonster(int roomID)
	{
		ResultSet result; 
		try 
		{
			result = sqlStmt.executeQuery("SELECT * FROM Monsters"
					+ " WHERE Location=" + roomID + ";");
		} catch (SQLException e) 
		{
			return null; 
			// throw new Exception(); 
		} 
		
		return result; 
	}
	
	/** Method: getPuzzle
	  * 
	  * This method is used to execute the SQL statement required for 
	  * finding a puzzle within a specific room. 
	  * @parameter roomID the room id used to search for the associated puzzle entry
	  * @return ResultSet containing 1 Puzzle entry representing a Puzzle that exists in that room 
	  */
	public ResultSet getPuzzle(int roomID)
	{
		ResultSet result; 
		try 
		{
			result = sqlStmt.executeQuery("SELECT * FROM Puzzles"
					+ " WHERE Location=" + roomID + ";");
		} catch (SQLException e) 
		{
			return null; 
			// throw new Exception(); 
		} 
		
		return result; 
	}
	
	/** Method: addRoom
	  * 
	  * This method is used to execute the SQL statement required for 
	  * inserting a room into the database. 
	  * @parameter id the room id
	  * @parameter name the room's name
	  * @parameter description the room's description 
	  * @parameter exits a list of the room's exits and where they lead to
	  */
	public void addRoom(int id, String name, String description, ArrayList<Exit> exits)
	{
		try 
		{
			sqlStmt.executeUpdate("INSERT INTO Rooms (RoomID, Name, Description)"
					+ " VALUES (" + "\'" + id + "\'" +  ", " + "\'" + name + "\'" 
					+ ", " + "\'" + description + "\'" + ");");
		} catch (SQLException e) 
		{
			// throw new Exception()
		} 
		
		for(Exit exit : exits)
		{
			// specified direction is modified here so that it is in the format of 
			// first level uppercase (e.g North)
			// this was added so that the variable could be seamlessly 
			// slipped in the query below 
			String direction = exit.getDirection().toLowerCase();
			direction = direction.substring(0, 1).toUpperCase() + direction.substring(1); 
		
			try 
			{
				sqlStmt.executeUpdate("UPDATE Rooms SET " + "" + direction + 
				"Exit= " + "\'" + exit.getRoomNum() + "\'" + 
			    "WHERE RoomID=" + id + ";");
			} catch (SQLException e) 
			{
				// throw new Exception() 
			} 
		}
	}
	
	/** Method: addItem
	  * 
	  * This method is used to execute the SQL statement required for 
	  * inserting an item into the database. 
	  * @parameter id the item id
	  * @parameter name the item's name
	  * @parameter description the item's description 
	  * @parameter location the room id corresponding to the room 
	  * that the item is placed in initially 
	  */
	public void addItem(int id, String name, String description, int location)
	{
		try 
		{
			sqlStmt.executeUpdate("INSERT INTO Items (ItemID, Name, Description, Location)"
					+ " VALUES (" + "\'" + id + "\'" + ", " + "\'" + name + "\'" + 
					", " + "\'" + description + "\'" + ", " + "\'" + location + "\'" + ");");
		} catch (SQLException e) 
		{
			// throw new Exception(); 
		} 
	}
	
	/** Method: addMonster
	  * 
	  * This method is used to execute the SQL statement required for 
	  * inserting a monster into the database. 
	  * @parameter id the monster id
	  * @parameter name the monster's name
	  * @parameter description the monster's description 
	  * @parameter correctItem the item used that corresponds to the item
	  * that can be used to defeat the monster 
	  * @parameter rightChoice the string to be displayed when the correct item is 
	  * selected
	  * @parameter wrongChoice the string to be displayed when the incorrect item 
	  * is selected 
	  * @parameter tip the string to be displayed when the player requests help 
	  * @parameter location the room id corresponding to the room 
	  * that the Monster is placed in
	  */
	public void addMonster(int id, String name, String description, int correctItem, 
			String rightChoice, String wrongChoice, String tip, int location)
	{
		try 
		{
			sqlStmt.executeUpdate("INSERT INTO Monsters (MonsterID, Name, Description, CorrectDefenseItem,"
					+ " CorrectDefenseItemResponse, IncorrectDefenseItemResponse, Tip, Location)"
					+ " VALUES (" + "\'" + id + "\'" + ", " + "\'" + name + "\'" + ", " + 
					"\'" + description + "\'" + ", " + "\'" + correctItem + "\'" + ", " + "\'" + rightChoice
					+ "\'" + ", " + "\'" + wrongChoice + "\'" + ", " + "\'" + tip + "\'" 
					+ ", " + "\'" + location + "\'" + ");");
		} catch (SQLException e) 
		{
			// throw new Exception(); 
		} 
	}
	
	/** Method: addPuzzle
	  * 
	  * This method is used to execute the SQL statement required for 
	  * inserting a puzzle into the database. 
	  * @parameter id the puzzle id
	  * @parameter prompt the puzzle's prompt (or question)
	  * @parameter answer the puzzle's answer 
	  * @parameter tip the string to be displayed when the player requests help
	  * @parameter location the room id corresponding to the room 
	  * that the puzzle is placed in
	  */
	public void addPuzzle(int id, String prompt, String answer, String tip, int location)
	{
		try 
		{
			sqlStmt.executeUpdate("INSERT INTO Puzzles (PuzzleID, Prompt, Answer, Tip, Location)"
					+ " VALUES (" + "\'" + id + "\'" + ", " + "\'" + prompt + "\'" + ", " + "\'" 
					+ answer + "\'" + ", " + "\'" + tip + "\'" + ", " + "\'" + location + "\'" + ");");
		} catch (SQLException e) 
		{
			// throw new Exception(); 
		} 
	}
}

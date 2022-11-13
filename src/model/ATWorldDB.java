package model;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Exit;
import controller.GameController;
import controller.Item;
import controller.Monster;
//import gameExceptions.InvalidFileException;
import controller.Player;
import controller.Puzzle;
import controller.Room;

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
		String createMonstersSQL = "CREATE TABLE Monsters(MonsterID int, Name varchar(255),"
				+ " Description varchar(255), CorrectDefenseItem int,"
				+ " CorrectDefenseItemResponse varchar(255), IncorrectDefenseItemResponse varchar(255),"
				+ " Tip varchar(255), Location int, isDefeated bit, Player int);"; 
		String createItemsSQL = "CREATE TABLE Items(ItemID int, Name varchar(255),"
				+ " Description varchar(255), Location int, Player int);"; 
		String createRoomsSQL = "CREATE TABLE Rooms(RoomID int, Name varchar(255),"
				+ " Description varchar(255), NorthExit int,"
				+ " SouthExit int, EastExit int, WestExit int, isVisited bit, Player int);"; 
		String createPuzzlesSQL = "CREATE TABLE Puzzles(PuzzleID int, Prompt varchar(255),"
				+ " Answer varchar(255), Tip varchar(255),"
				+ " Location int, isCompleted bit, Player int);"; 
		String defaultPlayerSQL = "INSERT INTO Players (PlayerID, Name, Score, Location, Health)"
		+ " VALUES (" + "\'" + "0" + "\'" + ", " +"\'" + "default" + "\'" + ", " + "\'" + "0" + "\'" + ", " + "\'" 
		+ "1" + "\'" + ", " + "\'" + "100" + "\'" + ");"; 
		try
		{
			sqlStmt.executeUpdate(createPlayersSQL); 
			sqlStmt.executeUpdate(createMonstersSQL); 
			sqlStmt.executeUpdate(createItemsSQL); 
			sqlStmt.executeUpdate(createRoomsSQL); 
			sqlStmt.executeUpdate(createPuzzlesSQL); 
			sqlStmt.executeUpdate(defaultPlayerSQL); // default player used for template 
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
	public ResultSet getRoom(Player player, int roomID)
	{
		ResultSet result; 
		try 
		{
			result = sqlStmt.executeQuery("SELECT * FROM Rooms"
					+ " WHERE RoomID=" + roomID + " AND " + "Player=" + "\'" + player.getPlayerID() + "\'" + ";");
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
	public ResultSet getItems(Player player, int roomID)
	{
		ResultSet result; 
		try 
		{
			result = sqlStmt.executeQuery("SELECT * FROM Items"
					+ " WHERE Location=" + roomID + " AND " + "Player=" + "\'" + player.getPlayerID() + "\'" + ";");
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
	public ResultSet getMonster(Player player, int roomID)
	{
		ResultSet result; 
		try 
		{
			result = sqlStmt.executeQuery("SELECT * FROM Monsters"
					+ " WHERE Location=" + roomID + " AND " + "Player=" + "\'" + player.getPlayerID() + "\'" + ";");
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
	public ResultSet getPuzzle(Player player, int roomID)
	{
		ResultSet result; 
		try 
		{
			result = sqlStmt.executeQuery("SELECT * FROM Puzzles"
					+ " WHERE Location=" + roomID + " AND " + "Player=" + "\'" + player.getPlayerID() + "\'" + ";");
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
			sqlStmt.executeUpdate("INSERT INTO Rooms (RoomID, Name, Description, Player)"
					+ " VALUES (" + "\'" + id + "\'" +  ", " + "\'" + name + "\'" 
					+ ", " + "\'" + description + "\'" + ", " + "\'" + "0" + "\'" + ");");
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
			sqlStmt.executeUpdate("INSERT INTO Items (ItemID, Name, Description, Location, Player)"
					+ " VALUES (" + "\'" + id + "\'" + ", " + "\'" + name + "\'" + 
					", " + "\'" + description + "\'" + ", " + "\'" + location + "\'" + ", " + "\'"
					+ "0" + "\'" + ");");
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
					+ " CorrectDefenseItemResponse, IncorrectDefenseItemResponse, Tip, Location, Player)"
					+ " VALUES (" + "\'" + id + "\'" + ", " + "\'" + name + "\'" + ", " + 
					"\'" + description + "\'" + ", " + "\'" + correctItem + "\'" + ", " + "\'" + rightChoice
					+ "\'" + ", " + "\'" + wrongChoice + "\'" + ", " + "\'" + tip + "\'" 
					+ ", " + "\'" + location + "\'" + ", " + "\'" + "0" + "\'" + ");");
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
			sqlStmt.executeUpdate("INSERT INTO Puzzles (PuzzleID, Prompt, Answer, Tip, Location, Player)"
					+ " VALUES (" + "\'" + id + "\'" + ", " + "\'" + prompt + "\'" + ", " + "\'" 
					+ answer + "\'" + ", " + "\'" + tip + "\'" + ", " + "\'" + location + "\'" + ", "
					+ "\'" + "0" + "\'" + ");");
		} catch (SQLException e) 
		{
			// throw new Exception(); 
		} 
	}
	
	public boolean checkProfileExistence(String playerName)
	{
        ResultSet result = null; 
		
		try 
		{
			result = sqlStmt.executeQuery("SELECT * FROM Players WHERE Name=" + "\'" + playerName + "\'" + ";");
		} catch (SQLException e) 
		{
			System.out.println(e.getMessage()); 
		}
		
		try 
		{
			return result.next(); 
		} catch (SQLException e) 
		{
			// throw new Exception()
		}
		
		return false; 
	}
	
	public ResultSet loadProfile(String playerName)
	{
		ResultSet result = null; 
		
		if(checkProfileExistence(playerName))
		{
			try 
			{
				result = sqlStmt.executeQuery("SELECT * FROM Players WHERE Name=" + "\'" + playerName + "\'" + ";");
			} catch (SQLException e) 
			{
				// throw new Exception()
			}
		}
		
		return result; 
	}
	
	public boolean newProfile(String playerName)
	{
		ResultSet lastEntry; 
		if(!checkProfileExistence(playerName))
		{	
			ResultSet resultSet = null;  
			int playerID; 
			try 
			{
				resultSet = sqlStmt.executeQuery("SELECT * FROM Players ORDER BY PlayerID DESC LIMIT 1"); 
				playerID = resultSet.getInt("PlayerID") + 1; 
				Player newPlayer = new Player(); 
				newPlayer.setPlayerID(playerID);
				GameController.setCurrentPlayer(newPlayer);
				sqlStmt.executeUpdate("INSERT INTO Players (PlayerID, Name, Score, Location, Health)"
						+ " VALUES (" + "\'" + playerID + "\'" + ", " + "\'" + playerName + 
						"\'" + ", " + "\'" + "0" + "\'" + ", " + "\'" + "1" + "\'" + ", " + "\'" + "100" + "\'" + ");");
				generateFromDefaultProfile(playerID); 
			} catch (SQLException e) 
			{
				System.out.println(e.getMessage()); 
				// throw new Exception()
			}
			
			return false;
		}
			
		return true; 
	}
	
	private void generateFromDefaultProfile(int playerID)
	{
		try
		{
			sqlStmt.executeUpdate("INSERT INTO Rooms(RoomID, Name, Description, "
					+ "NorthExit, SouthExit, EastExit, WestExit, Player) "
					+ "SELECT RoomID, Name, Description, NorthExit, SouthExit, EastExit, WestExit, "
					+ "\"" + playerID + "\" "
					+ "FROM Rooms WHERE Player=\"0\";"); // for rooms 
			sqlStmt.executeUpdate("INSERT INTO Items (ItemID, Name, Description, Location, Player) "
					+ "SELECT ItemID, Name, Description, Location, \"" + playerID + "\" "
					+ "FROM Items WHERE Player=\"0\";"); // for items
			sqlStmt.executeUpdate("INSERT INTO Monsters (MonsterID, Name, Description, CorrectDefenseItem, "
					+ "CorrectDefenseItemResponse, IncorrectDefenseItemResponse, Tip, Location, Player) "
					+ "SELECT MonsterID, Name, Description, CorrectDefenseItem, CorrectDefenseItemResponse,"
					+ "IncorrectDefenseItemResponse, Tip, Location, \"" + playerID + "\" "
					+ "FROM Monsters WHERE Player=\"0\";"); // for monsters
			sqlStmt.executeUpdate("INSERT INTO Puzzles (PuzzleID, Prompt, Answer, Tip, Location, Player) "
					+ "SELECT PuzzleID, Prompt, Answer, Tip, Location, \"" + playerID + "\" "
					+ "FROM Puzzles WHERE Player=\"0\";"); // for puzzles
		}catch(SQLException e)
		{
			System.out.println(e.getMessage()); 
			// throw new Exception(); 
		}
	}

	public void removePlayer(String playerName) 
	{
		try
		{
			sqlStmt.executeUpdate("DELETE FROM Rooms WHERE Player=" + "\'" + playerName + "\';"); // for rooms 
			sqlStmt.executeUpdate("DELETE FROM Items WHERE Player=" + "\'" + playerName + "\';"); // for items
			sqlStmt.executeUpdate("DELETE FROM Monsters WHERE Player=" + "\'" + playerName + "\';"); // for monsters
			sqlStmt.executeUpdate("DELETE FROM Puzzles WHERE Player=" + "\'" + playerName + "\';"); // for puzzles
			sqlStmt.executeUpdate("DELETE FROM Players WHERE Name=" + "\'" + playerName + "\';"); // for players 
		}catch(SQLException e)
		{
			// throw new Exception(); 
		}
	}
	
	public void saveHealth(Player player, int health)
	{
		try
		{
			sqlStmt.executeUpdate("UPDATE Players SET Health=" + "\'" + health + "\'" + 
				    " WHERE Player=" + "\'" + player.getName() + "\'" + ";"); 
		}catch(SQLException e)
		{
			// throw new Exception(); 
		}
	}
	
	public void saveScore(Player player, int score)
	{
		try
		{
			sqlStmt.executeUpdate("UPDATE Players SET Score=" + "\'" + score + "\'" + 
				    " WHERE Player=" + "\'" + player.getName() + "\'" + ";"); 
		}catch(SQLException e)
		{
			// throw new Exception(); 
		}
	}
	
	public void saveCurrentRoom(Player player, Room room)
	{
		try
		{
			sqlStmt.executeUpdate("UPDATE Players SET Location=" + "\'" + room.getRoomID() + "\'" +
				    " WHERE Name=" + "\'" + player.getName() + "\'" + ";"); 
		}catch(SQLException e)
		{
			// throw new Exception(); 
		}
	}
	
	public void saveVisited(Player player, Room room)
	{
		try
		{
			sqlStmt.executeUpdate("UPDATE Rooms SET isVisited=1" + 
				    " WHERE RoomID=" + "\'" + room.getRoomID() + "\'" + " AND Player=" + 
					"\'" + player.getPlayerID() + "\'" + ";"); 
		}catch(SQLException e)
		{
			// throw new Exception(); 
		}
	}
	
	public void saveDefeat(Player player, Monster monster)
	{
		try
		{
			sqlStmt.executeUpdate("UPDATE Monsters SET isDefeated=1" + 
				    " WHERE MonsterID=" + "\'" + monster.getMonsterID() + "\'" + " AND Player=" + 
					"\'" + player.getPlayerID() + "\'" + ";"); 
		}catch(SQLException e)
		{
			// throw new Exception(); 
		}
	}
	
	public void saveSolved(Player player, Puzzle puzzle)
	{
		try
		{
			sqlStmt.executeUpdate("UPDATE Puzzles SET isCompleted=1" + 
				    " WHERE PuzzleID=" + "\'" + puzzle.getPuzzleID() + "\'" + " AND Player="
					+ "\'" + player.getPlayerID() + "\'" + ";");
		}catch(SQLException e)
		{
			// throw new Exception(); 
		}
	}
	
	public void saveRoomItem(Player player, Room room, Item item)
	{
		try
		{
			sqlStmt.executeUpdate("UPDATE Items SET Location=" + "\'" + room.getRoomID() + "\'" + 
				    " WHERE Player=" + "\'" + player.getPlayerID() + "\'" + " AND ItemID=" + "\'" + item.getItemID() + "\'" + ";");
		}catch(SQLException e)
		{
			System.out.println(e.getMessage()); 
			// throw new Exception(); 
		}
	}
	
	public void saveInventoryItem(Player player, Room room, Item item)
	{
		try
		{
			// location 1000 is player's inventory
			sqlStmt.executeUpdate("UPDATE Items SET Location=" + "\'1000\'" + 
					 " WHERE Player=" + "\'" + player.getPlayerID() + "\'" + " AND ItemID=" + "\'" + item.getItemID() + "\'" + ";");
		}catch(SQLException e)
		{
			// throw new Exception(); 
		}
	}
}

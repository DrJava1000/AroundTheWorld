package model;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import controller.Exit;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATWorldDB 
{
	private Connection dbConn; 
	private Statement sqlStmt; 
	private String dbConnURL; 
	
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
        
        buildTables(); 
	}
	
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
				+ " SouthExit int, EastExit int, WestExit int);"; 
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

package controller;

/**	Class: Monster
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 *
 * This class encapsulates the functionality of a Monster in 
 * Around the World. 
 */
public class Monster 
{
	private int monsterID;
	private String monsterName;
	private String monsterDescription;
	private int rightItemChoice; // item id of right item to use 
	private String tip;
	private boolean defeated;
	private String wrongChoice; // text when wrong item is used 
	private String rightChoice; // text when right item is used 
	private int roomID; 
	
	public int getMonsterID() 
	{
		return monsterID;
	}

	public void setMonsterID(int id) 
	{
		monsterID = id;
	}
	
	public String getMonsterName() 
	{
		return monsterName;
	}

	public void setMonsterName(String name) 
	{
		monsterName = name;
	}

	public String getMonsterDescription() 
	{
		return monsterDescription;
	}

	public void setMonsterDescription(String description) 
	{
		monsterDescription = description;
	}

	public String getTip()
	{
		return tip;
	}

	public void setTip(String t) 
	{
		tip = t;
	}

	/** Method: storeDefeated
	  * 
	  * This method stores a monster's defeat in the database for a specific player. 
	  * @parameter player the current player to link the Monster's defeat too
	  */
	public void saveDefeated(Player player)
	{
		setDefeated(true); 
		GameController.getMap().storeDefeated(player, this);
	}

	public boolean isDefeated() 
	{
		return defeated;
	}

	public void setDefeated(boolean defeat) 
	{
		defeated = defeat;
	}

	public String getWrongChoice() 
	{
		return wrongChoice;
	}

	public void setWrongChoice(String wChoice) 
	{
		wrongChoice = wChoice;
	}
	
	public void setRightChoice(String rChoice) 
	{
		rightChoice = rChoice;
	}

	public String getRightChoice() 
	{
		return rightChoice;
	}

	public int getRightItemChoice() 
	{
		return rightItemChoice;
	}

	public void setRightItemChoice(int itemChoice) 
	{
		rightItemChoice = itemChoice;
	}

	public int getRoomID() 
	{
		return roomID;
	}

	public void setRoomID(int id) 
	{
		roomID = id;
	}
}

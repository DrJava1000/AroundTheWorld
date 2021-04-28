package controller;

import java.util.ArrayList;

/**Class: Player 
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 * 
 * This class is the player class. It is responsible for managing the player's inventory. 
*/
public class Player 
{ 
	private int playerID; 
	private int hp;
	private String name; 
	private int playerScore; 
	private Item[] inventory; // contains a list of roomIDs 
	private int roomID; 
	
	/** Constructor: Player
	  * 
	  * This constructor initializes the player's inventory.
	  */
	public Player()
	{
		inventory = new Item[3]; 
	}
	
	/** Method: addItem
	  * 
	  * This method adds an item to the player's inventory. 
	  * @param item the item to add
	  */
	public void addItem(Item item)
	{
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] == null)
			{
				inventory[i] = item; 
				break; 
			}
		}
	}

	/** Method: removeItem
	  * 
	  * This method removes an item from the player's inventory.
	  * @param item the item to remove
	  */
	public void removeItem(Item item)
	{
		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i].getItemName().equals(item.getItemName()))
			{
				inventory[i] = null;
				break; 
			}
		}
	}
	
	/** Method: printInventory
	  * 
	  * Get the player's inventory as a string. 
	  * @return a string containing a list of everything in the player's inventory.
	  */
	public String printInventory()
	{
		// check for empty inventory 
		if(inventory[0] == null)
		{
			return "\nYou don't have anything in your inventory.\n"; 
		}
		
		String inventoryStr = "\nIn your inventory, you have ";
		for(int i = 0; i < inventory.length; i++)
		{
			String response; 
			if(inventory[i] == null)
				response = "Empty Slot"; 
			else 
				response = inventory[i].getItemName(); 
			
			if(i == (inventory.length - 1))
				inventoryStr += response + ".\n"; 
			else
				inventoryStr += response + ", "; 
		}
		
		return inventoryStr; 
	}
	
	public void setInventory(ArrayList<Item> items)
	{
		int index = 0; 
		for(Item currentItem : items)
		{
			inventory[index] = currentItem; 
			index++; 
		}
	}
	
	/** Method: getInventory
	  * 
	  * Gte the player's inventory as a collection. 
	  * @return a reference to the player's inventory
	  */
	public Item[] getInventory()
	{
		return inventory; 
	}

	/**
	 * @return the healthPoints
	 */
	public int getHP() 
	{
		return hp;
	}
	
	/**
	 * @param healthPoints the healthPoints to set
	 */
	public void setHP(int healthPoints) 
	{
		hp = healthPoints;
	}
	
	public void updateHP(int healthPoints)
	{
		hp = hp + healthPoints; 
		GameController.getMap().storePlayerHealth(this, healthPoints);
	}

	public int getScore() 
	{
		return playerScore;
	}
	
	public void updateScore(int score) 
	{
		playerScore = playerScore + score; 
		GameController.getMap().storePlayerScore(this, score);
	}

	public void setScore(int score) 
	{
		playerScore = score;
	}

	public int getRoomID() 
	{
		return roomID;
	}

	public void setRoomID(int roomID) 
	{
		this.roomID = roomID;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public int getPlayerID() 
	{
		return playerID;
	}

	public void setPlayerID(int playerID) 
	{
		this.playerID = playerID;
	}
}

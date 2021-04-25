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
	private ArrayList<Item> inventory; 
	
	/** Constructor: Player
	  * 
	  * This constructor initializes the player's inventory.
	  */
	public Player()
	{
		inventory = new ArrayList<Item>(); 
	}
	
	/** Method: addItem
	  * 
	  * This method adds an item to the player's inventory. 
	  * @param item the item to add
	  */
	public void addItem(Item item)
	{
		inventory.add(item); 
	}
	
	/** Method: removeItem
	  * 
	  * This method removes an item from the player's inventory.
	  * @param item the item to remove
	  */
	public void removeItem(Item item)
	{
		for(int i = 0; i < inventory.size(); i++)
		{
			if(inventory.get(i).getItemName().equals(item.getItemName()))
			{
				inventory.remove(i); 
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
		if(inventory.size() == 0)
		{
			return "\nYou don't have anything in your inventory.\n"; 
		}
		
		String inventoryStr = "\nIn your inventory, you have ";
		for(int i = 0; i < inventory.size(); i++)
		{
			if(i == (inventory.size() - 1))
				inventoryStr += inventory.get(i).getItemName() + ".\n"; 
			else
				inventoryStr += inventory.get(i).getItemName() + ", "; 
		}
		
		return inventoryStr; 
	}
	
	/** Method: getInventory
	  * 
	  * Gte the player's inventory as a collection. 
	  * @return a reference to the player's inventory
	  */
	public ArrayList<Item> getInventory()
	{
		return inventory; 
	}
}

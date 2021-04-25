package controller;

import java.util.ArrayList;

/**Class: Player 
 * @author Ryan Gambrell
 * @version 2.0 
 * Course: ITEC 3860 Spring 2021
 * Written: March 21, 2021
 * 
 * This class is the player class. It is responsible for managing the player's inventory. 
*/
public class Player 
{
	private ArrayList<Item> inventory; 
	private ArrayList<Monster> monsters;
	private ArrayList<Puzzle> puzzles;
	
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
	
	public int getNumOfDefeats()
	{
		int countMonster = 0;
		int countPuzzle = 0;
		
		for(Monster monster: monsters)
		{
			if(monster.isDefeated() == true)
			{
				countMonster++;
			}
		}
		
		for(Puzzle puzzle: puzzles)
		{
			if(puzzle.isSolved() == true)
			{
				countPuzzle++;
			}
		}
		
		return countMonster + countPuzzle;
	}
	
	/**updates the players health whenever is passes a puzzle or monster correctly
	 * 
	 * */
	public int healthPoints()
	{
		int countHealth = 100;
		for(Monster monster: monsters)
		{
			if(monster.isDefeated() == true)
			{
				countHealth += 10;
			}
			System.out.println("Congratulations you defeated " + monster.getMonsterName() + "! You are able to move to the next room." );
			return countHealth;
		}
		
		for(Puzzle puzzle: puzzles)
		{
			if(puzzle.isSolved() == true)
			{
				countHealth++;
			}
			System.out.println("Congratulations, you solved the puzzle, you may go to the next room.");
			return countHealth;
		}
		
		return countHealth;
	}
}
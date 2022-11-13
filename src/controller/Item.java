package controller;

/**Class: Item
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 * This class is the Item class. It is responsible for representing an item and its
 * attributes in Around The World. 
*/
public class Item 
{
	private int itemID;
	private String itemName; 
	private String itemDesc; 
	private int roomID; 
	
	public Item()
	{
		setItemName("Unamed Room"); 
		setItemDesc("No Description"); 
	}
	
	public Item(int id, String name, String description)
	{
		setItemID(id); 
		setItemName(name);
		setItemDesc(description); 
	}
	
	public int getItemID() 
	{
		return itemID;
	}

	public void setItemID(int itemID) 
	{
		this.itemID = itemID;
	}

	public String getItemName() 
	{
		return itemName;
	}

	public void setItemName(String itemName) 
	{
		this.itemName = itemName;
	}

	public String getItemDesc() 
	{
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) 
	{
		this.itemDesc = itemDesc;
	}

	public String display()
	{
		return "Item ID: " + itemID + ", Name: " + itemName + "\nDescription: " + itemDesc; 
	}
	
	public int getRoomID() 
	{
		return roomID;
	}

	public void setRoomID(int roomID) 
	{
		this.roomID = roomID;
	} 
	
	public String toString()
	{
		return display();
	}

}

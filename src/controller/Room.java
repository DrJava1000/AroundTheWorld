package controller;

import java.util.ArrayList;

/**Class: Room
 * 
 * Course: ITEC 3860 Spring 2021
 * 
 * This class encapsulates all the properties and functionality
 * of any given room in the game's map.  
*/
public class Room 
{
	private int roomID; 
	private String roomName; 
	private String description;
	private ArrayList<Exit> roomExits = new ArrayList<Exit>(); 
	private ArrayList<Item> roomItems = new ArrayList<Item>(); 
	private Puzzle puzzle = new Puzzle();
	private Monster monster = new Monster();
	private boolean visitedStatus; 
	
	/** Constructor: Room
	  * 
	  * This is the default constructor for Room. It is unused but
	  * kept to ensure that each Room can use the secondary constructor. 
	  */
	public Room()
	{	
	}
	
	/** Constructor: Room(id)
	  * 
	  * This constructor creates a room with a specific id. 
	  * @param id the id to give to this room
	  */
	public Room(int id)
	{
		roomID = id; 
	}
	
	/** Method: display
	  * 
	  * This method gets much of the information associated with a 
	  * specific room. It gets the room's name, visited status, its
	  * description, and a list of the room's exits. 
	  * @return a single formatted String containing all the room's
	  * information 
	  */
	public String display()
	{
		String visitStatus = ""; 
	
		if(visitedStatus)
			visitStatus = "Yes"; 
		else
			visitStatus = "No"; 
		
		return "Room: " + roomName + ", Visited Before?: " + visitStatus + "\n\n" + description + "\n" + displayExits(); 
	}
	
	/** Method: displayExits
	  * 
	  * This method gets a list of all of a room's exits in a clean and organized format.
	  * This method is used to tell the user what exits are allowed in any given room. 
	  * @return a single formatted string with all room exits
	  */
	public String displayExits()
	{
		String availableExits = "You can go "; 
		
		for(int i = 0; i < roomExits.size(); i++)
		{
			if(i == (roomExits.size() - 1))
				availableExits += roomExits.get(i) + "."; 
			else
				availableExits += roomExits.get(i) + ", "; 
		}
		
		return availableExits; 
	}
	
	/** Method: displayItems
	  * 
	  * This method gets a list of all of a room's properties in a clean and organized format.
	  * 
	  * @return the Julian day number that begins at noon of the
	  * given calendar date.
	  */
	public String displayItems()
	{
		String availableItems = "\n\nThe following items are in this room: "; 
		
		if(roomItems.size() == 0)
			return display() + "\n\nThere are no items in this room."; 
		
		for(int i = 0; i < roomItems.size(); i++)
		{
			if(i == (roomItems.size() - 1))
				availableItems += roomItems.get(i).getItemName() + "."; 
			else
				availableItems += roomItems.get(i).getItemName() + ", "; 
		}
		
		return display() + availableItems; 
	}
	
	/** Method: displayFullRoom
	  * 
	  * This method is a wrapper for displayFullRoom().
	  * @return a single formatted String containing all of a room's properties 
	  */
	public String displayFullRoom()
	{
		return displayItems(); 
	}
	
	/** Method: addName
	  * 
	  * This method adds an item to a room. 
	  * @param item a reference to the item to be added to a room
	  */
	public void addItem(Item item)
	{
		roomItems.add(item); 
	}
	
	/** Method: removeItem
	  * 
	  * This method removes an item from a room.
	  * @param item a reference to the item to be removed from a room
	  */
	public void removeItem(Item item)
	{
		for(int i = 0; i < roomItems.size(); i++)
		{
			if(roomItems.get(i).getItemName().equals(item.getItemName()))
			{
				roomItems.remove(i); 
			}
		} 
	}
	
	/** Method: validDirection
	  * 
	  * This method gets whether a specific direction is allowed for 
	  * a room. Meaning, that direction actually leads to another room. 
	  * @param direction a cardinal direction entered by a user
	  * @return whether a user direction is valid or not
	  */
	public boolean validDirection(String direction)
	{
		for(Exit validExit : roomExits)
		{
			if(validExit.getDirection().equalsIgnoreCase(direction))
				return true; 
		}
		
		return false; 
	}
	
	/** Method: setRoomID
	  * 
	  * Assign an id to the room. 
	  * @param id the id to be assigned to a room
	  */
	public void setRoomID(int id)
	{
		roomID = id; 
	}
	
	/** Method: getRoomID
	  * 
	  * Get the id associated with a room. 
	  * @return the room id
	  */
	public int getRoomID()
	{
		return roomID; 
	}
	
	/** Method: setName
	  * 
	  * Assign a name to a room. 
	  * @param name the name given to a room
	  */
	public void setName(String name)
	{
		roomName = name; 
	}
	
	/** Method: getName
	  * 
	  * Get the name associated with a room. 
	  * @return a room's name 
	  */
	public String getName()
	{
		return roomName; 
	}
	
	/** Method: setDescription
	  * 
	  * This method gives a specific description
	  * to a room. 
	  * @param desc the description to give to a room
	  */
	public void setDescription(String desc)
	{
		description = desc; 
	}
	
	/** Method: buildDescription
	  * 
	  * This method takes a list of description lines and aggregates them
	  * into a single description for the room. 
	  * @param descArr a collection of Strings (or lines) that make up a description
	  */
	public void buildDescription(ArrayList<String> descArr)
	{
		description = ""; 
		
		for(String line : descArr)
		{
			description += line + "\n";  
		}
	}
	
	/** Method: getDescription
	  * 
	  * Get a room's description. 
	  * @return a formatted String containing the description
	  */
	public String getDescription()
	{
		return description; 
	}
	
	/** Method: setExits
	  * 
	  * Assign a set of exits to a room. 
	  * @param a collection of exits to associated with a room 
	  */
	public void setExits(ArrayList<Exit> exits)
	{
		roomExits = exits; 
	}
	
	/** Method: getExits
	  * 
	  * Get a set of exits out of a room. 
	  * @return a collection of Exits associated with a room
	  */
	public ArrayList<Exit> getExits()
	{
		return roomExits;
	}
	
	/** Method: setVisited
	  * 
	  * Set the room's previously visited status. 
	  * @param visited the room's previously-visited status
	  */
	public void setVisited(boolean visited)
	{
		visitedStatus = visited; 
	}
	
	/** Method: isVisited
	  * 
	  * This method gets a boolean value that indicates whether
	  * a room has been visited or not. 
	  * @return whether the room has been previously visited
	  */
	public boolean isVisited()
	{
		return visitedStatus; 
	}
	
	/** Method: setExits
	  * 
	  * Associate a collection of items with a specific room. 
	  * @param items a collection of items
	  */
	public void setItems(ArrayList<Item> items)
	{
		roomItems = items; 
	}
	
	/** Method: getItems
	  * 
	  * Get a collection of items within a specific room. 
	  * @return a collection of items
	  */
	public ArrayList<Item> getItems()
	{
		return roomItems; 
	}
	
	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	/** Method: toString
	  * 
	  * This method is a wrapper for displayFullRoom(). 
	  * @return a single formatted string containing every room's properties
	  */
	public String toString()
	{
		return displayFullRoom(); 
	} 
}

package controller;

import java.util.Arrays;
import java.util.List;

/**Class: Exit
 * @author Ryan Gambrell
 * @version 2.0 
 * Course: ITEC 3860 Spring 2021
 * Written: March 21, 2021
 * 
 * This class is the Exit class. It is responsible for representing an exit
 * with a cardinal direction and the room it leads to.  
*/
public class Exit 
{
	private String cardinalDirection; 
	private int roomNum; 
	private static List<String> VALID_DIRECTIONS = Arrays.asList("WEST", "EAST", "NORTH", "SOUTH", "UP", "DOWN");
	
	/** Method: setDirection
	  * 
	  * Set the direction associated with an exit. 
	  * @param direction the direction to give an exit
	  */
	public void setDirection(String direction) 
	{
		cardinalDirection = direction; 
	}
	
	/** Method: getDirection
	  * 
	  * Get the direction associated with an exit. 
	  * @return the direction an exit has. 
	  */
	public String getDirection() 
	{
		return cardinalDirection; 
	}
	
	/** Method: setRoomNum
	  * 
	  * Set an exit's room id so that an exit may be
	  * linked to another room via its room id. 
	  * @return the room id associated with an exit
	  */
	public void setRoomNum(int num) 
	{
		roomNum = num;
	} 
	
	/** Method: getRoomNum
	  * 
	  * Get the room id representative of a room 
	  * that an exit leads to. 
	  * @return the room id associated with an exit
	  */
	public int getRoomNum() 
	{
		return roomNum; 
	}
	
	/** Method: buildExit
	  * 
	  * This method is used to associate a cardinal direction
	  * and room id with an exit.
	  * @param direction a cardinal direction
	  * @param num a room id
	  */
	public void buildExit(String direction, int num)
	{
		if(evaluateDirection(direction))
			cardinalDirection = direction; 
		roomNum = num; 
	}
	
	/** Method: evaluateDirection
	  * 
	  * This method evaluates a user-entered cardinal direction
	  * and ensures that the specified direction is allowed for an exit.
	  * @param direction a cardinal direction
	  * @return whether the direction is allowed to be used for an exit
	  */
	private boolean evaluateDirection(String direction)
	{
		for (String availableDirection : VALID_DIRECTIONS)
		{
			if(availableDirection.equalsIgnoreCase(direction))
			{
				return true; 
			}
		}
		
		return false; 
	}
	
	/** Method: toString()
	  * 
	  * This method is a wrapper for getDirection(). 
	  * 
	  * @return a string containing the exit's direction in all uppercases 
	  */
	public String toString()
	{
		return getDirection().toUpperCase(); 
	}
}

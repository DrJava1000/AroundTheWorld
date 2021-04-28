package controller;

public class InventoryOperations 
{
	public String addToInventory(Player player, Room room, Item item)
	{
		return switchItemToPlayer(player, room, item); 
	}
	
	public String removeFromInventory(Player player, Room room, Item item)
	{
		return switchItemToRoom(player, room, item); 
	}
	
	private String switchItemToPlayer(Player player, Room room, Item item)
	{
		return GameController.getMap().storeInventoryItem(player, room, item);
	}
	
	private String switchItemToRoom(Player player, Room room, Item item)
	{
		return GameController.getMap().storeRoomItem(player, room, item);
	}
}

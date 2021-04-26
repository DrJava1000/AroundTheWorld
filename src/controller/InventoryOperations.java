package controller;

public class InventoryOperations 
{
	public void addToInventory(Player player, Room room, Item item)
	{
		room.removeItem(item);
		player.addItem(item);
	}
	
	public void removeFromInventory(Player player, Room room, Item item)
	{
		room.addItem(item);
		player.removeItem(item);
	}
	
	public void switchItemToPlayer(Player player, Room room, Item item)
	{
		GameController.getMap().storeRoomItem(player, room, item);
	}
	
	public void switchItemToRoom(Player player, Room room, Item item)
	{
		GameController.getMap().storeInventoryItem(player, room, item);
	}
}

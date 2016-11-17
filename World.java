package peli;

public class World {
	private Room currentRoom;
	private Room exitRoom;
	
	public Room currentRoom(){
		return this.currentRoom;
	}
	
	public boolean Next(String move){
		try {
			currentRoom = currentRoom.getNextRoom(move);
			return true;
		} catch (InvalidCommandException e) {
			return false;
		}
		
	}
	
	public boolean End(){
		return currentRoom == exitRoom;
	}
	
	public World() {
		Room start = new Room("You wake up in a forest. You have no idea how you got here.");
		Room caveEnterance = new Room("You see a cave in front of you.");
		Room exit = new Room("You went in to the cave. You win!");
		
		start.addAllowedMove("north", caveEnterance);
		start.addAllowedMove("up", caveEnterance);
		
		caveEnterance.addAllowedMove("north", exit);
		caveEnterance.addAllowedMove("up", exit);
		caveEnterance.addAllowedMove("south", start);
		
		currentRoom = start;
		exitRoom = exit;
	}
}

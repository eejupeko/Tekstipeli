package peli;

import java.util.ArrayList;
import java.util.List;

public class World {
	private Room currentRoom;
	private Room exitRoom;
	private List<String> inv = new ArrayList<String>();
	
	public Room currentRoom(){
		return this.currentRoom;
	}
	
	private boolean Next(String move){
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
		String[] frwd = new String[]{"forward","forwards", "up","north"};
		String[] back = new String[]{"back","backwards","down","south"};
				
		Room start = new Room("You wake up in a forest. You have no idea how you got here.");
		Room caveEnterance = new Room("You see a cave in front of you.");
		Room exit = new Room("You went in to the cave. You win!");
		
		start.addAllowedMove(frwd, caveEnterance);
		
		caveEnterance.addAllowedMove(frwd, exit);
		caveEnterance.addAllowedMove(back, start);
		
		currentRoom = start;
		exitRoom = exit;
	}
	
	private void addItem(String item){
		inv.add(item);
	}
	
	private void removeItem(String item){
		for (String s : inv){
			if (item.equals(s)) inv.remove(s);
		}
	}
	
	public String doStuff(String[] data){
		switch(data[0]){
			case "go":
				Next(data[1]);
				return "";
			case "take":
				if (currentRoom.getItem(data[1])) {
					addItem(data[1]);
					return "You took the " + data[1];
				}
				break;
			case "attack":
				break;
			case "use":
				break;
			default:
				break;
		}
		return "Can't do that \r\n";
	}
}

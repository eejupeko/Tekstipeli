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
	
	private boolean next(String move){
		try {
			currentRoom = currentRoom.getNextRoom(move);
			return true;
		} catch (InvalidCommandException e) {
			return false;
		}
		
	}
	
	public boolean end(){
		return currentRoom.ending;
	}
	
	public boolean win(){
		return currentRoom == exitRoom;
	}
	
	public World() {}
	
	public String start(){
		String[] frw = new String[]{"forward","forwards", "up","north"};
		String[] back = new String[]{"back","backwards","down","south"};
		String[] right = new String[]{"right", "east"};
		String[] left = new String[]{"left","west"};
		
		
		Room start = new Room("You are in a forest. You have an idea how you got here. There's a path leading right and forwards.");
		Room caveEnterance = new Room("You see a key on the ground and a path forwards leading to a cave.");
		Room cave = new Room("You walk in to the cave and a bear attacks you. You died");
		
		start.addAllowedMove(frw, caveEnterance);
		
		caveEnterance.addAllowedMove(frw, cave);
		caveEnterance.addAllowedMove("cave", cave);
		caveEnterance.addAllowedMove(back, start);
		caveEnterance.addItemToRoom("key", "You see a path forwards leading to a cave.");
		
		currentRoom = start;
		cave.ending = true;
		
		return "You wake up in aforest. You have no idea how you got here. There's a path leading right and forwards.";
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
				if (next(data[1])) return "";
				break;
			case "take":
				if (currentRoom.getItem(data[1])) {
					addItem(data[1]);
					return "You took the " + data[1] + "\r\n";
				}
				break;
			case "attack":
				break;
			case "use":
				if (currentRoom.useItem(data[1])){
					removeItem(data[1]);
					return "You used the " + data[1] + "\r\n";
				}
				break;
			case "quit":
				Room exit = new Room("");
				exit.ending = true;
				currentRoom = exit;
			default:
				break;
		}
		return "Can't do that (" + data[0] + ", " + data[1] +")\r\n";
	}
}

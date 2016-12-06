package peli;

import java.util.ArrayList;
import java.util.List;

public class World {
	private Room currentRoom;
	private Room exitRoom;
	private Room prevRoom;
	private List<String> inv = new ArrayList<String>();
	private String weapon = "NONE";
	private Room winRoom;
	
	public Room currentRoom(){
		return currentRoom;
	}
	
	private boolean next(String move){
		Room temp = null;
		try {
			temp = prevRoom;
			prevRoom = currentRoom;
			currentRoom = currentRoom.getNextRoom(move);
			return true;
		} catch (InvalidCommandException e) {
			if (temp != null) prevRoom = temp;
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
		String[] back = new String[]{"backwards","backward","down","south"};
		String[] right = new String[]{"right", "east"};
		String[] left = new String[]{"left","west"};
		
		
		Room start = new Room("You are in a forest. You have an idea how you got here. There's a path leading right and forwards.");
		Room caveEnterance = new Room("You see a sword on the ground and a path forwards leading to a cave and back where you came from.");
		Room cave = new Room("You walk in to the cave and a bear attacks you. You died");
		Room pathRight = new Room("You see a troll in front of you");
		Room trollDoor = new Room("You see a button in front of you");
		
		start.addAllowedMove(frw, caveEnterance);
		start.addAllowedMove(right, pathRight);
		
		caveEnterance.addAllowedMove(frw, cave);
		caveEnterance.addAllowedMove("cave", cave);
		caveEnterance.addAllowedMove(back, start);
		caveEnterance.addItemToRoom("sword", "You see a path forwards leading to a cave and back where you came from.");
		
		pathRight.addAllowedMove(left, start);
		
		
		
		pathRight.isAttackable("You killed the troll using your sword \r\n", "The troll killed you \r\n", 
								"There's a door in front of you that the troll was guarding and a path to the left where you came from.", 
								"sword", new String[]{"door"}, trollDoor);
		
		currentRoom = start;
		cave.ending = true;
		winRoom = trollDoor;
		
		return "You wake up in a forest. You have no idea how you got here. There's a path leading right and forwards.";
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
				if (data[1].equals("back")) {currentRoom = prevRoom; return ""; }
				if (next(data[1])) return "";
				break;
			case "take":
				if (currentRoom.getItem(data[1])) {
					addItem(data[1]);
					if (data[1].equals("sword")) this.weapon = "sword";
					return "You took the " + data[1] + "\r\n";
				}
				break;
			case "attack":
				if (currentRoom.getAttackable()){
					return currentRoom.attack(weapon);
				}
				break;
			case "use":
				if (currentRoom.useItem(data[1])){
					removeItem(data[1]);
					return "You used the " + data[1] + "\r\n";
				}
				break;
			case "open":
				if (next(data[1])) return "You opened the door and went in \r\n";
				break;
			case "quit":
				Room r = new Room("");
				r.ending = true;
				currentRoom = r;
				return "Goodbye.\r\n";
			case "help":
				return getHelp();
			case "press":
				if (data[1].equals("button") && currentRoom == winRoom){
					Room rm = new Room("");
					rm.ending = true;
					currentRoom = rm;	
					return "As you push the button you realise it was your purpose all along to push that button. \r\n"
							+ "Conglaturation !!!";
				}
			default:
				break;
		}
		return "Can't do that (" + data[0] + ", " + data[1] +")\r\n";
	}
	
	public String getHelp(){
		return "Some example phrases: walk forwads,  attack the ogre, use the key, get the big red ball ... \r\n";
	}
}

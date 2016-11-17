package peli;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private String text;
	private List<roomDirection> allowedMoves = new ArrayList<roomDirection>();
	
	public Room(String text){
		this.text = text;
	}
	
	public void addAllowedMove(String[] s, Room h) {
		allowedMoves.add(new roomDirection(s,h));
	}
	
	public void addAllowedMove(String s, Room h) {
		String[] i = new String[] {s};
		allowedMoves.add(new roomDirection(i,h));
	}
	
	public String[] getAllowedMoves(Room h) {
		List<String> list = new ArrayList<String>();
		for(roomDirection rd : allowedMoves){
			for(String move : rd.s){
				list.add(move);
			}
		}
		return list.toArray(new String[0]);
	}
	
	public String getText() { return this.text; }
	
	public Room getNextRoom(String s) throws InvalidCommandException{
		for(roomDirection rd : allowedMoves){
			for(String move : rd.s){
				if (move.equals(s)) return rd.r;
			}
		}
		throw new InvalidCommandException("error");
	}
}


class roomDirection{
	public String[] s;
	public Room r;
	public roomDirection(String[] s, Room r){
		this.s = s;
		this.r = r;
	}
}
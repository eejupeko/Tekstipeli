package peli;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private String text;
	private String altText;
	private String defeat;
	private String winBattle;
	private List<roomDirection> allowedMoves = new ArrayList<roomDirection>();
	private List<String> items = new ArrayList<String>();
	private String usableItem;
	public boolean ending = false;
	private boolean attackable = false;
	private String neededItem = "NONE";
	private String[] winBattleMoves = null;
	private Room winBattleRoom = null;
	
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
	
	public void addItemToRoom(String item, String str){
		this.items.add(item);
		this.altText = str;
	}
	
	public void addUsableItem(String item, String str){
		this.usableItem = item;
		this.altText = str;
	}
	
	
	public void isAttackable(String win, String lose, String after, String item){
		isAttackable(win, lose, after, item, null, null);
	}
	
	public void isAttackable(String win, String lose, String after, String item, String[] newMoves, Room newRoom){
		this.attackable = true;
		this.neededItem = item;
		this.winBattle = win;
		this.altText = after;
		this.defeat = lose;
		this.winBattleMoves = newMoves;
		this.winBattleRoom = newRoom;
	}
	
	public boolean getAttackable() { return this.attackable; }
	
	public String attack(String item){
		if (this.neededItem.equals(item) || this.neededItem.equals("NONE") ){
			this.text = this.altText;
			this.attackable = false;
			if (this.winBattleMoves != null && this.winBattleRoom != null)  addAllowedMove(winBattleMoves, winBattleRoom);
			return this.winBattle;
		}
		this.text = this.defeat;
		this.ending = true;
		return "";
	}
	
	public boolean getItem(String item){
		for (String s : this.items){
			if (item.equals(s)) {items.remove(s); this.text = this.altText; return true;}
		}
		return false;
	}
	
	public boolean useItem(String item){
		if (this.usableItem == item){
			this.usableItem = null;
			this.text = this.altText;
			return true;
		}
		return false;
	}
	
	
	public boolean end(){
		return this.ending;
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
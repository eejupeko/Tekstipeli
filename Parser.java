package peli;

/*
 *  L -> Vo P O
 *  L -> V Od
 *  O -> A Ad O
 *  Od -> T Oe
 *  
 *  Vo -> take | get | ...
 *  P -> this | his | that | ε | ...
 *  A -> a | an | the | ε
 *  Ad -> red | big | ...
 *  O -> key | card | letter | ...
 *  V -> go | walk | run ...
 *  T -> to | to the
 *  Od -> left | right | forward | ...
 *  Oe -> left | right | forward | ...
 */

public class Parser {
	private String[] words;
	private String[] data = new String[2];
	
	public Parser(){;}
	
	public String[] parse(String s){
		this.words = s.toLowerCase().split("\\s+");
		if (words.length == 1 && (words[0].equals("quit") || words[0].equals("exit") )) { data[0] = "quit"; return data;}
		if (words.length == 1 && words[0].equals("help")) { data[0] = "help"; return data;};
		for(int i=-1; i < this.words.length; i++){
			if (L(i)) return data;
		}
		return null;
	}
	
	private boolean L(int i){
		if(i+1 == words.length) return false;
		switch(words[i+1]){
			case "take":
			case "get":
				data[0] = "take";
				return Vo(i+1);
			case "pick":
				data[0] = "take";
				if (words[i+2].equals("up")) return Vo(i+2);
				return Vo(i+1);
			case "attack":
			case "fight":
			case "punch":
			case "kick":
			case "hit":
				data[0] = "attack";
				return Vo(i+1);
			case "use":
				data[0] = "use";
				return Vo(i+1);
			case "go":
			case "walk":
			case "run":
				data[0] = "go";
				return V(i+1);
			case "open":
				data[0] = "open";
				return Vo(i+1);
			case "press":
			case "push":
				data[0] = "press";
				return Vo(i+1);
			default:
				break;
		}
		return false;
	}
	
	private boolean Vo(int i){
		if(i+1 == words.length) return false;
		switch(words[i+1]){
			case "this":
			case "that":
			case "his":
				return P(i+1);
			default:
				if (P(i)) return true;
				break;
		}
		return false;
	}
	
	private boolean P(int i){
		if(i+1 == words.length) return false;
		switch(words[i+1]){
			case "key":
			case "card":
			case "troll":
			case "sword":
			case "door":
			case "button":
				data[1] = words[i+1];
				return true;
			case "a":
			case "an":
			case "the":
				return A(i+1);
			default:
				if(A(i)) return true;
				break;
		}
		return false;
	}
	
	private boolean A(int i){
		if(i+1 == words.length) return false;
		switch(words[i+1]){
			case "big":
			case "red":
				return Ad(i+1);
			default:
				if(Ad(i)) return true;
				break;
		}
		return false;
	}
		
	private boolean Ad(int i){
		if(i+1 == words.length) return false;
		switch(words[i+1]){
			case "key":
			case "card":
			case "troll":
			case "sword":
			case "door":
			case "button":
				data[1] = words[i+1];
				return true;
			default:
				return false;
		}
	}
	
	private boolean V(int i){
		if(i+1 == words.length) return false;
		switch(words[i+1]){
			case "forward":
			case "forwards":
			case "back":
			case "backwards":
			case "backward":
			
			case "up":
			case "down":
			case "left":
			case "right":
				
			case "north":
			case "south":
			case "east":
			case "west":
				
			case "cave":
				data[1] = words[i+1];
				return true;
			case "to":
				if (words[i+2].equals("the")) return T(i+2);
				return T(i+1);
			default:
				return false;
		}
	}
	
	private boolean T(int i){
		if(i+1 == words.length) return false;
		switch(words[i+1]){
			case "forward":
			case "forwards":
			case "back":
			case "backwards":
			case "backward":
			
			case "up":
			case "down":
			case "left":
			case "right":
				
			case "north":
			case "south":
			case "east":
			case "west":
				
			case "cave":
				data[1] = words[i+1];
				return true;
			default:
				return false;
		}
	}
}

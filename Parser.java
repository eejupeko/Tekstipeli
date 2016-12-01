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
		this.words = s.split("\\s+");
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
			case "attack":
			case "fight":
			case "punch":
			case "kick":
				data[0] = "attack";
				return Vo(i+1);
			case "use":
				data[0] = "use";
				return Vo(i+1);
			case "go":
			case "walk":
				data[0] = "go";
				return V(i+1);
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
			
			case "up":
			case "down":
			case "left":
			case "right":
				
			case "north":
			case "south":
			case "east":
			case "west":
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
			
			case "up":
			case "down":
			case "left":
			case "right":
				
			case "north":
			case "south":
			case "east":
			case "west":
				data[1] = words[i+1];
				return true;
			default:
				return false;
		}
	}
}

package peli;

import java.util.Scanner;

public class Peli {

	public static void main(String[] args) {
		World w = new World();
		Scanner keyboard = new Scanner(System.in);
		String command;
		while(true){
			System.out.println(w.currentRoom().getText());
			if (w.End()) break;
			System.out.print('>');
			command = keyboard.nextLine();
			if (!w.Next(command)) System.out.println("I didn't understand.");;
			System.out.println();			
		}
		keyboard.close();
	}

}

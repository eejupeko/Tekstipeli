package peli;

import java.util.Scanner;

public class Peli {

	public static void main(String[] args) {
		World world = new World();
		Scanner keyboard = new Scanner(System.in);
		String command;
		Parser p = new Parser();
		System.out.println(world.start());
		while(true){
			System.out.print('>');
			
			command = keyboard.nextLine();
			String[] data = p.parse(command);
			
			if (data == null) System.out.println("I didn't understand");
			else System.out.print(world.doStuff(data));

			System.out.println();		
			System.out.println(world.currentRoom().getText());
			if (world.end()) break;
		}
		keyboard.close();
	}
}

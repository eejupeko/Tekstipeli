package peli;

public class InvalidCommandException extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidCommandException(String msg){
		super(msg);
	}
}

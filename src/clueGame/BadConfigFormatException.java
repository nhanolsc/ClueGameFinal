package clueGame;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {}
	
	public BadConfigFormatException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return "Error: Check Configuration Files";
	}
	
	
}

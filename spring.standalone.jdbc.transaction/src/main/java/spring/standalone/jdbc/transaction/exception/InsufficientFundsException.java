package spring.standalone.jdbc.transaction.exception;

public class InsufficientFundsException extends Exception {
	private static final long serialVersionUID = 1L;

	String message;
	
	public InsufficientFundsException(String message) {
		super(message);
	}
}

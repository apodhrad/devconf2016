package cz.devconf.workshop.friendbook;

public class FriendBookException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FriendBookException(String message) {
		super(message);
	}

	public FriendBookException(String message, Throwable cause) {
		super(message, cause);
	}

}

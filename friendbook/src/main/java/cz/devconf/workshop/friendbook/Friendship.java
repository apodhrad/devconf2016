package cz.devconf.workshop.friendbook;

public class Friendship {

	private String userId1;
	private String userId2;
	private boolean isConfirmed;

	public Friendship(String userId1, String userId2) {
		this.userId1 = userId1;
		this.userId2 = userId2;
		this.isConfirmed = false;
	}

	public String getUserId1() {
		return userId1;
	}

	public String getUserId2() {
		return userId2;
	}

	public void confirm() {
		this.isConfirmed = true;
	}

	public boolean isConfirmed() {
		return this.isConfirmed;
	}

}

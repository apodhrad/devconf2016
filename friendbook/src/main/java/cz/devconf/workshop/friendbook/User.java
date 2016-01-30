package cz.devconf.workshop.friendbook;

import java.util.HashMap;
import java.util.Map;

public class User {

	private String id;
	private String name;
	private String surname;
	private String email;

	private Map<User, FriendshipState> friends;
	
	public User(String name, String surname) {
		this.name = name;
		this.surname = surname;
		friends = new HashMap<User, FriendshipState>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void askForFriendship(User user) {
		friends.put(user, FriendshipState.PROPOSED);
		user.askForFriendship(this);
	}

	public void confirmFriendship(User user) {
		friends.put(user, FriendshipState.CONFIRMED);
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", email=" + email + "]";
	}

	private enum FriendshipState {

		PROPOSED, CONFIRMED;

	}
}

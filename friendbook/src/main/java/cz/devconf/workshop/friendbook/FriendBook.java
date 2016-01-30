package cz.devconf.workshop.friendbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FriendBook {

	private Collection<User> users;

	public FriendBook() {
		users = new ArrayList<User>();
	}

	public void registerUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}

	public Collection<User> getUsers() {
		return Collections.unmodifiableCollection(users);
	}

	public User findUser(String name) {
		return null;
	}

	public void loadUsers() {

	}

	public void saveUsers() {

	}
}

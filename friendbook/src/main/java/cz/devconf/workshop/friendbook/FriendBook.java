package cz.devconf.workshop.friendbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FriendBook {

	public static final FriendBook INSTANCE = new FriendBook();

	private Collection<User> users;

	private FriendBook() {
		this.users = new ArrayList<User>();
	}

	public void registerUser(User user) {
		this.users.add(user);
	}

	public void removeUser(User user) {
		for (User u : this.users) {
			u.rejectFriend(user);
		}
		this.users.remove(user);
	}

	public Collection<User> getUsers() {
		return Collections.unmodifiableCollection(this.users);
	}

	public User findUser(String id) {
		for (User user : this.users) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}

	public void clean() {
		this.users.clear();
	}

	public void loadUsers() {

	}

	public void saveUsers() {

	}
}

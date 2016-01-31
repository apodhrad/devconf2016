package cz.devconf.workshop.friendbook;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class User {

	private String id;
	private String name;
	private String surname;
	// Map<UserId, FriendshipState>
	private Map<String, FriendshipState> friends;

	public User(String id) {
		this(id, null, null);
	}

	public User(String id, String name, String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.friends = new HashMap<String, FriendshipState>();
	}

	public String getId() {
		return id;
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

	public void addFriend(User user) {
		this.friends.put(user.getId(), FriendshipState.PROPOSED);
		user.friends.put(this.getId(), FriendshipState.REQUESTED);
	}

	public void confirmFriend(User user) {
		if (FriendshipState.REQUESTED.equals(this.friends.get(user.getId()))) {
			this.friends.put(user.getId(), FriendshipState.CONFIRMED);
			user.friends.put(this.getId(), FriendshipState.CONFIRMED);
		} else {
			throw new FriendBookException("Cannot confirm a friendship with a user who didn't request the friendship");
		}
	}

	public void rejectFriend(User user) {
		this.friends.remove(user.getId());
		user.friends.remove(this.getId());
	}

	public Collection<User> getFriends() {
		return getFriends(FriendshipState.CONFIRMED);
	}

	public Collection<User> getProposedFriends() {
		return getFriends(FriendshipState.PROPOSED);
	}

	public Collection<User> getRequestedFriends() {
		return getFriends(FriendshipState.REQUESTED);
	}

	public Collection<User> getFriends(FriendshipState state) {
		Collection<User> friends = new HashSet<User>();
		for (String userId : this.friends.keySet()) {
			if (this.friends.get(userId) == state) {
				friends.add(FriendBook.INSTANCE.findUser(userId));
			}
		}
		return Collections.unmodifiableCollection(friends);
	}

	public boolean hasFriend(User user) {
		return getFriends().contains(user);
	}

	@Override
	public String toString() {
		return "User [id = " + id + ", name=" + name + ", surname=" + surname + "]";
	}

	public enum FriendshipState {

		PROPOSED, REQUESTED, CONFIRMED

	}
}

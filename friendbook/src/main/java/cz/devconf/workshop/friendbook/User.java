package cz.devconf.workshop.friendbook;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class User {

	private String nickname;
	private String name;
	private String surname;
	// Map<nickname, FriendshipState>
	private Map<String, FriendshipState> friends;

	public User(String nickname) {
		this(nickname, null, null);
	}

	public User(String nickname, String name, String surname) {
		if (nickname == null) {
			throw new IllegalArgumentException("User must have a nickname");
		}
		this.nickname = nickname;
		this.name = name;
		this.surname = surname;
		this.friends = new HashMap<String, FriendshipState>();
	}

	public String getNickname() {
		return this.nickname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void addFriend(User user) {
		this.friends.put(user.getNickname(), FriendshipState.PROPOSED);
		user.friends.put(this.getNickname(), FriendshipState.REQUESTED);
	}

	public void confirmFriend(User user) {
		if (FriendshipState.REQUESTED.equals(this.friends.get(user.getNickname()))) {
			this.friends.put(user.getNickname(), FriendshipState.CONFIRMED);
			user.friends.put(this.getNickname(), FriendshipState.CONFIRMED);
		} else {
			throw new IllegalStateException(
					"Cannot confirm a friendship with a user who didn't request the friendship");
		}
	}

	public void rejectFriend(User user) {
		this.friends.remove(user.getNickname());
		user.friends.remove(this.getNickname());
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
		return "User [nickname = " + nickname + ", name = " + name + ", surname = " + surname + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return ((User) obj).getNickname().equals(getNickname());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getNickname().hashCode();
	}

	public enum FriendshipState {

		PROPOSED, REQUESTED, CONFIRMED

	}
}

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
		if (FriendBook.INSTANCE.findUser(user.getNickname()) == null) {
			throw new FriendBookException(user + " is not registered");
		}
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
		return "User [nickname = " + nickname + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	public enum FriendshipState {

		PROPOSED, REQUESTED, CONFIRMED

	}
}

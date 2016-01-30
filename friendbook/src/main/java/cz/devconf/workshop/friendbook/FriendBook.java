package cz.devconf.workshop.friendbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class FriendBook {

	private Collection<User> users;
	private Collection<Friendship> friendships;

	public FriendBook() {
		this.users = new ArrayList<User>();
		this.friendships = new HashSet<Friendship>();
	}

	public void registerUser(User user) {
		this.users.add(user);
	}

	public void removeUser(User user) {
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

	public void addFriend(User user1, User user2) {
		this.friendships.add(new Friendship(user1.getId(), user2.getId()));
	}

	public Collection<User> getFriends(User user) {
		Collection<User> friends = new HashSet<User>();
		for (Friendship friendship : this.friendships) {
			if (friendship.getUserId1().equals(user.getId())) {
				friends.add(findUser(friendship.getUserId2()));
			}
			if (friendship.getUserId2().equals(user.getId())) {
				friends.add(findUser(friendship.getUserId1()));
			}
		}
		return Collections.unmodifiableCollection(friends);
	}
	
	public Collection<Friendship> getFriendships() {
		return Collections.unmodifiableCollection(this.friendships);
	}

	public void loadUsers() {

	}

	public void saveUsers() {

	}
}

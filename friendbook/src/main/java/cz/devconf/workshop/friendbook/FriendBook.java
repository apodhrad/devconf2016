package cz.devconf.workshop.friendbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FriendBook {

	public static final FriendBook INSTANCE = new FriendBook();
	private static final int NICKNAME = 0;
	private static final int NAME = 1;
	private static final int SURNAME = 2;
	private static final String DELIMETER = ";";

	private Collection<User> users;

	private FriendBook() {
		this.users = new ArrayList<User>();
	}

	public void registerUser(User user) {
		if (findUser(user.getNickname()) != null) {
			throw new FriendBookException("User with nickname '" + user.getNickname() + "' already exists");
		}
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

	public User findUser(String nickname) {
		for (User user : this.users) {
			if (user.getNickname().equals(nickname)) {
				return user;
			}
		}
		return null;
	}

	public void clean() {
		this.users.clear();
	}

	public void load(File file) throws IOException {
		BufferedReader in = null;
		List<String> lines = new ArrayList<String>();
		try {
			in = new BufferedReader(new FileReader(file));
			String line;
			while ((line = in.readLine()) != null) {
				String[] data = line.split(DELIMETER);
				if (data.length < 1) {
					throw new FriendBookException("User data must conatain at least one attribute");
				}
				registerUser(
						new User(getAttribute(data, NICKNAME), getAttribute(data, NAME), getAttribute(data, SURNAME)));
				lines.add(line);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		for (String line : lines) {
			String[] data = line.split(";");
			User user = findUser(getAttribute(data, NICKNAME));
			for (int i = 3; i < data.length; i++) {
				User friend = findUser(getAttribute(data, i));
				user.addFriend(friend);
				friend.confirmFriend(user);
			}
		}
	}

	public void save(File file) throws IOException {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			for (User user : this.users) {
				Collection<User> friends = user.getFriends();
				String[] data = new String[3 + friends.size()];
				setAttribute(data, NICKNAME, user.getNickname());
				setAttribute(data, NAME, user.getName());
				setAttribute(data, SURNAME, user.getSurname());
				int i = 3;
				for (User friend : friends) {
					setAttribute(data, i, friend.getNickname());
				}
				out.write(arrayToString(data, DELIMETER));
				out.newLine();
			}
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private String getAttribute(String[] data, int index) {
		if (index >= data.length) {
			return null;
		}
		String attribute = data[index].trim();
		if (attribute.length() == 0) {
			return null;
		}
		return attribute;
	}

	private void setAttribute(String[] data, int index, String value) {
		if (value == null) {
			data[index] = "";
		}
		data[index] = value.trim();
	}

	public String arrayToString(String[] array, String delimeter) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < array.length - 1; i++) {
			result.append(array[i]).append(delimeter);
		}
		result.append(array[array.length - 1]);
		return result.toString();
	}
}

package cz.devconf.workshop.friendbook;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class FriendBookTest {

	private static final User JOHN_DOE = new User("jdoe", "John", "Doe");
	private static final User PAUL_HAPPY = new User("phappy", "Paul", "Happy");
	private static final User SUSAN = new User("susan", "Susan", "Single");

	@Test
	public void registerUserTest() {
		FriendBook friendBook = new FriendBook();
		Assert.assertTrue(friendBook.getUsers().isEmpty());
		friendBook.registerUser(JOHN_DOE);
		Assert.assertEquals(1, friendBook.getUsers().size());

		User user = friendBook.getUsers().iterator().next();
		Assert.assertEquals(JOHN_DOE.getName(), user.getName());
		Assert.assertEquals(JOHN_DOE.getSurname(), user.getSurname());
		Assert.assertEquals(JOHN_DOE, user);
	}

	@Test
	public void deleteUserTest() {
		FriendBook friendBook = new FriendBook();
		friendBook.registerUser(JOHN_DOE);
		friendBook.removeUser(JOHN_DOE);

		Assert.assertTrue(friendBook.getUsers().isEmpty());
	}

	@Test
	public void findUserTest() {
		FriendBook friendBook = new FriendBook();
		friendBook.registerUser(JOHN_DOE);

		User user = friendBook.findUser("jdoe");
		Assert.assertEquals(JOHN_DOE, user);
	}

	@Test
	public void getUsersTest() {
		Exception expectedException;
		FriendBook friendBook = new FriendBook();
		friendBook.registerUser(JOHN_DOE);

		Collection<User> users = friendBook.getUsers();

		expectedException = null;
		try {
			Assert.assertTrue(users.remove(JOHN_DOE));
		} catch (UnsupportedOperationException e) {
			expectedException = e;
		}
		Assert.assertNotNull(expectedException);

		expectedException = null;
		try {
			Assert.assertTrue(users.add(PAUL_HAPPY));
		} catch (UnsupportedOperationException e) {
			expectedException = e;
		}
		Assert.assertNotNull(expectedException);
	}

	@Test
	public void addFriendTest() {
		FriendBook friendBook = new FriendBook();
		friendBook.registerUser(JOHN_DOE);
		friendBook.registerUser(PAUL_HAPPY);

		friendBook.addFriend(JOHN_DOE, PAUL_HAPPY);

		Collection<Friendship> friendships = friendBook.getFriendships();
		Assert.assertEquals(1, friendships.size());
		Friendship friendship = friendships.iterator().next();
		Assert.assertEquals(JOHN_DOE.getId(), friendship.getUserId1());
		Assert.assertEquals(PAUL_HAPPY.getId(), friendship.getUserId2());
		Assert.assertFalse(friendship.isConfirmed());

	}

	@Test
	public void getFriendsTest() {
		FriendBook friendBook = new FriendBook();
		friendBook.registerUser(JOHN_DOE);
		friendBook.registerUser(PAUL_HAPPY);

		friendBook.addFriend(JOHN_DOE, PAUL_HAPPY);

		Assert.assertTrue(friendBook.getFriends(JOHN_DOE).contains(PAUL_HAPPY));
		Assert.assertTrue(friendBook.getFriends(PAUL_HAPPY).contains(JOHN_DOE));

	}
}

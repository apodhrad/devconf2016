package cz.devconf.workshop.friendbook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cz.devconf.workshop.friendbook.User.FriendshipState;

public class UserTest {

	private FriendBook friendBook = FriendBook.INSTANCE;

	private User johnDoe;
	private User paulHappy;

	@Before
	public void cleanFriendBook() {
		friendBook.clean();

		johnDoe = friendBook.registerUser("jdoe");
		johnDoe.setName("John");
		johnDoe.setSurname("Doe");
		paulHappy = friendBook.registerUser("phappy");
		paulHappy.setName("Paul");
		paulHappy.setSurname("Happy");
	}

	@Test
	public void userTest() {
		Exception expectedException;

		User user = new User("a");
		Assert.assertEquals("a", user.getNickname());
		Assert.assertNull(user.getName());
		Assert.assertNull(user.getSurname());
		user.setName("b");
		Assert.assertEquals("b", user.getName());
		Assert.assertNull(user.getSurname());
		user.setSurname("c");
		Assert.assertEquals("b", user.getName());
		Assert.assertEquals("c", user.getSurname());
		Assert.assertEquals("User [nickname = a, name = b, surname = c]", user.toString());

		expectedException = null;
		try {
			new User(null);
		} catch (IllegalArgumentException iae) {
			expectedException = iae;
		}
		Assert.assertNotNull(expectedException);

		user = new User("a", null, null);
		Assert.assertNull(user.getName());
		Assert.assertNull(user.getSurname());

		user = new User("a", "b", null);
		Assert.assertEquals("b", user.getName());
		Assert.assertNull(user.getSurname());

		user = new User("a", null, "c");
		Assert.assertNull(user.getName());
		Assert.assertEquals("c", user.getSurname());

		user = new User("a", "b", "c");
		Assert.assertEquals("b", user.getName());
		Assert.assertEquals("c", user.getSurname());
		user.setName(null);
		Assert.assertNull(user.getName());
		Assert.assertEquals("c", user.getSurname());
		user.setSurname(null);
		Assert.assertNull(user.getName());
		Assert.assertNull(user.getSurname());
		Assert.assertEquals("User [nickname = a, name = null, surname = null]", user.toString());

		expectedException = null;
		try {
			new User(null, "b", "c");
		} catch (IllegalArgumentException iae) {
			expectedException = iae;
		}
		Assert.assertNotNull(expectedException);

	}

	@Test
	public void addFriendTest() {
		johnDoe.addFriend(paulHappy);

		Assert.assertEquals(1, johnDoe.getProposedFriends().size());
		Assert.assertTrue(johnDoe.getProposedFriends().contains(paulHappy));
		Assert.assertEquals(1, paulHappy.getRequestedFriends().size());
		Assert.assertTrue(paulHappy.getRequestedFriends().contains(johnDoe));

		User adamSmile = new User("asmile");

		Exception expectedException = null;
		try {
			johnDoe.addFriend(adamSmile);
		} catch (FriendBookException fbe) {
			expectedException = fbe;
		}
		Assert.assertNotNull(expectedException);
	}

	@Test
	public void getFriendsTest() {
		User adamSmile = friendBook.registerUser("asmile");

		johnDoe.addFriend(paulHappy);
		johnDoe.addFriend(adamSmile);
		paulHappy.confirmFriend(johnDoe);

		Assert.assertEquals(1, johnDoe.getFriends().size());
		Assert.assertTrue(johnDoe.getFriends().contains(paulHappy));
		Assert.assertEquals(1, paulHappy.getFriends().size());
		Assert.assertTrue(paulHappy.getFriends().contains(johnDoe));

		Assert.assertEquals(1, johnDoe.getProposedFriends().size());
		Assert.assertTrue(johnDoe.getProposedFriends().contains(adamSmile));
	}

	@Test
	public void confirmTest() {
		Exception expectedException = null;
		try {
			paulHappy.confirmFriend(johnDoe);
		} catch (IllegalStateException ise) {
			expectedException = ise;
		}
		Assert.assertNotNull(expectedException);
	}

	@Test
	public void equalsTest() {
		User user = new User("jdoe");
		Assert.assertTrue(johnDoe.equals(user));
		Assert.assertTrue(user.equals(johnDoe));
		Assert.assertFalse(paulHappy.equals(user));
		Assert.assertFalse(user.equals(paulHappy));
		Assert.assertFalse(user.equals(friendBook));
	}

	@Test
	public void friendshipStateTest() {
		Assert.assertEquals(3, FriendshipState.values().length);
		FriendshipState.valueOf("PROPOSED");
		FriendshipState.valueOf("REQUESTED");
		FriendshipState.valueOf("CONFIRMED");
	}
}

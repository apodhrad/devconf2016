package cz.devconf.workshop.friendbook;

import static cz.devconf.workshop.friendbook.util.Users.JOHN_DOE;
import static cz.devconf.workshop.friendbook.util.Users.PAUL_HAPPY;
import static cz.devconf.workshop.friendbook.util.Users.SUSAN;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cz.devconf.workshop.friendbook.User.FriendshipState;

public class UserTest {

	private FriendBook friendBook = FriendBook.INSTANCE;

	@Before
	public void cleanFriendBook() {
		friendBook.clean();
		JOHN_DOE.rejectFriend(PAUL_HAPPY);
		JOHN_DOE.rejectFriend(SUSAN);
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
		friendBook.registerUser(JOHN_DOE);
		friendBook.registerUser(PAUL_HAPPY);

		JOHN_DOE.addFriend(PAUL_HAPPY);

		Assert.assertEquals(1, JOHN_DOE.getProposedFriends().size());
		Assert.assertTrue(JOHN_DOE.getProposedFriends().contains(PAUL_HAPPY));
		Assert.assertEquals(1, PAUL_HAPPY.getRequestedFriends().size());
		Assert.assertTrue(PAUL_HAPPY.getRequestedFriends().contains(JOHN_DOE));
	}

	@Test
	public void getFriendsTest() {
		friendBook.registerUser(JOHN_DOE);
		friendBook.registerUser(PAUL_HAPPY);
		friendBook.registerUser(SUSAN);

		JOHN_DOE.addFriend(PAUL_HAPPY);
		JOHN_DOE.addFriend(SUSAN);
		PAUL_HAPPY.confirmFriend(JOHN_DOE);

		Assert.assertEquals(1, JOHN_DOE.getFriends().size());
		Assert.assertTrue(JOHN_DOE.getFriends().contains(PAUL_HAPPY));
		Assert.assertEquals(1, PAUL_HAPPY.getFriends().size());
		Assert.assertTrue(PAUL_HAPPY.getFriends().contains(JOHN_DOE));

		Assert.assertEquals(1, JOHN_DOE.getProposedFriends().size());
		Assert.assertTrue(JOHN_DOE.getProposedFriends().contains(SUSAN));
	}

	@Test
	public void confirmTest() {
		friendBook.registerUser(JOHN_DOE);
		friendBook.registerUser(PAUL_HAPPY);

		Exception expectedException = null;
		try {
			PAUL_HAPPY.confirmFriend(JOHN_DOE);
		} catch (IllegalStateException ise) {
			expectedException = ise;
		}
		Assert.assertNotNull(expectedException);
	}

	@Test
	public void friendshipStateTest() {
		Assert.assertEquals(3, FriendshipState.values().length);
		FriendshipState.valueOf("PROPOSED");
		FriendshipState.valueOf("REQUESTED");
		FriendshipState.valueOf("CONFIRMED");
	}
}

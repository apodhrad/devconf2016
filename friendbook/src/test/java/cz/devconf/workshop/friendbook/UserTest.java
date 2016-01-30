package cz.devconf.workshop.friendbook;

import static cz.devconf.workshop.friendbook.util.Users.JOHN_DOE;
import static cz.devconf.workshop.friendbook.util.Users.PAUL_HAPPY;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private FriendBook friendBook = FriendBook.INSTANCE;

	@Before
	public void cleanFriendBook() {
		friendBook.clean();
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

		JOHN_DOE.addFriend(PAUL_HAPPY);
		PAUL_HAPPY.confirmFriend(JOHN_DOE);

		Assert.assertEquals(1, JOHN_DOE.getFriends().size());
		Assert.assertTrue(JOHN_DOE.getFriends().contains(PAUL_HAPPY));
		Assert.assertEquals(1, PAUL_HAPPY.getFriends().size());
		Assert.assertTrue(PAUL_HAPPY.getFriends().contains(JOHN_DOE));

	}
}

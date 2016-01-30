package cz.devconf.workshop.friendbook;

import static cz.devconf.workshop.friendbook.util.Users.JOHN_DOE;
import static cz.devconf.workshop.friendbook.util.Users.PAUL_HAPPY;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FriendBookTest {

	private FriendBook friendBook = FriendBook.INSTANCE;

	@Before
	public void cleanFriendBook() {
		friendBook.clean();
	}

	@Test
	public void registerUserTest() {
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
		friendBook.registerUser(JOHN_DOE);
		friendBook.removeUser(JOHN_DOE);

		Assert.assertTrue(friendBook.getUsers().isEmpty());
	}

	@Test
	public void findUserTest() {
		friendBook.registerUser(JOHN_DOE);

		User user = friendBook.findUser("jdoe");
		Assert.assertEquals(JOHN_DOE, user);
	}

	@Test
	public void getUsersTest() {
		Exception expectedException;
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

}

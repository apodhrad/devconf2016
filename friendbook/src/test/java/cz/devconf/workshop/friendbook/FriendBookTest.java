package cz.devconf.workshop.friendbook;

import static cz.devconf.workshop.friendbook.util.Users.ADAM_SMILE;
import static cz.devconf.workshop.friendbook.util.Users.JOHN_DOE;
import static cz.devconf.workshop.friendbook.util.Users.PAUL_HAPPY;
import static cz.devconf.workshop.friendbook.util.Users.SUSAN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

	@Test
	public void loadTest() throws IOException {
		File file = new File("src/test/resources/friendbook.csv");
		friendBook.load(file);
		User user1 = friendBook.findUser(JOHN_DOE.getId());
		Assert.assertNotNull(user1);
		User user2 = friendBook.findUser(PAUL_HAPPY.getId());
		Assert.assertNotNull(user2);
		User user3 = friendBook.findUser(SUSAN.getId());
		Assert.assertNotNull(user3);
		User user4 = friendBook.findUser(ADAM_SMILE.getId());
		Assert.assertNull(user4);

		Assert.assertTrue(user1.hasFriend(user2));
		Assert.assertTrue(user2.hasFriend(user1));
		Assert.assertFalse(user1.hasFriend(user3));
		Assert.assertFalse(user2.hasFriend(user3));
	}

	@Test
	public void saveTest() throws IOException {
		friendBook.registerUser(JOHN_DOE);
		friendBook.registerUser(PAUL_HAPPY);
		friendBook.registerUser(SUSAN);

		JOHN_DOE.addFriend(PAUL_HAPPY);
		PAUL_HAPPY.confirmFriend(JOHN_DOE);

		File file = new File("target/friendbook-tmp.csv");
		friendBook.save(file);

		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			Assert.assertEquals("jdoe;John;Doe;phappy", in.readLine());
			Assert.assertEquals("phappy;Paul;Happy;jdoe", in.readLine());
			Assert.assertEquals("susan;Susan;Single", in.readLine());
			Assert.assertNull(in.readLine());
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}

package cz.devconf.workshop.friendbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FriendBookTest {

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
	public void registerUserTest() {
		User user = friendBook.getUsers().iterator().next();
		Assert.assertEquals(johnDoe.getName(), user.getName());
		Assert.assertEquals(johnDoe.getSurname(), user.getSurname());
		Assert.assertEquals(johnDoe, user);

		Exception expectedException = null;
		try {
			friendBook.registerUser(johnDoe.getNickname());
		} catch (FriendBookException fbe) {
			expectedException = fbe;
		}
		Assert.assertNotNull(expectedException);
	}

	@Test
	public void deleteUserTest() {
		friendBook.removeUser(johnDoe);
		Assert.assertEquals(1, friendBook.getUsers().size());
		friendBook.removeUser(paulHappy);
		Assert.assertTrue(friendBook.getUsers().isEmpty());
	}

	@Test
	public void findUserTest() {
		Assert.assertEquals(johnDoe, friendBook.findUser("jdoe"));
		Assert.assertEquals(paulHappy, friendBook.findUser("phappy"));
	}

	@Test
	public void getUsersTest() {
		Exception expectedException;
		Collection<User> users = friendBook.getUsers();

		expectedException = null;
		try {
			Assert.assertTrue(users.remove(johnDoe));
		} catch (UnsupportedOperationException e) {
			expectedException = e;
		}
		Assert.assertNotNull(expectedException);

		expectedException = null;
		try {
			Assert.assertTrue(users.add(paulHappy));
		} catch (UnsupportedOperationException e) {
			expectedException = e;
		}
		Assert.assertNotNull(expectedException);
	}

	@Test
	public void loadTest() throws IOException {
		File file = new File("src/test/resources/friendbook-ok.csv");
		friendBook.load(file);
		Assert.assertEquals(10, friendBook.getUsers().size());
		User user1 = friendBook.findUser("a1");
		Assert.assertNotNull(user1);
		User user2 = friendBook.findUser("a2");
		Assert.assertNotNull(user2);
		User user3 = friendBook.findUser("a3");
		Assert.assertNotNull(user3);
		User user4 = friendBook.findUser("a4");
		Assert.assertNotNull(user4);
		User user5 = friendBook.findUser("a5");
		Assert.assertNotNull(user5);
		User user6 = friendBook.findUser("a6");
		Assert.assertNotNull(user6);
		User user7 = friendBook.findUser("a7");
		Assert.assertNotNull(user7);
		User user8 = friendBook.findUser("a8");
		Assert.assertNotNull(user8);

		Assert.assertFalse(user1.hasFriend(user1));
		Assert.assertFalse(user1.hasFriend(user2));
		Assert.assertFalse(user1.hasFriend(user3));
		Assert.assertFalse(user1.hasFriend(user4));
		Assert.assertTrue(user1.hasFriend(user5));
		Assert.assertTrue(user1.hasFriend(user6));
		Assert.assertTrue(user1.hasFriend(user7));
		Assert.assertFalse(user1.hasFriend(user8));
		Assert.assertFalse(user2.hasFriend(user1));
		Assert.assertFalse(user2.hasFriend(user2));
		Assert.assertFalse(user2.hasFriend(user3));
		Assert.assertFalse(user2.hasFriend(user4));
		Assert.assertFalse(user2.hasFriend(user5));
		Assert.assertFalse(user2.hasFriend(user6));
		Assert.assertFalse(user2.hasFriend(user7));
		Assert.assertTrue(user2.hasFriend(user8));
	}

	@Test
	public void loadWithErrorTest() throws IOException {
		File file = new File("src/test/resources/friendbook-error.csv");
		Exception expectedException = null;
		try {
			friendBook.load(file);
		} catch (FriendBookException fbe) {
			expectedException = fbe;
		}
		Assert.assertNotNull(expectedException);
	}

	@Test
	public void loadWithExceptionTest() throws IOException {
		File file = new File("src/test/resources/friendbook-xyz.csv");
		Exception expectedException = null;
		try {
			friendBook.load(file);
		} catch (FileNotFoundException fnfe) {
			expectedException = fnfe;
		}
		Assert.assertNotNull(expectedException);
	}

	@Test
	public void saveTest() throws IOException {
		friendBook.registerUser("asmile");

		johnDoe.addFriend(paulHappy);
		paulHappy.confirmFriend(johnDoe);

		File file = new File("target/friendbook-tmp.csv");
		friendBook.save(file);

		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			Assert.assertEquals("jdoe;John;Doe;phappy", in.readLine());
			Assert.assertEquals("phappy;Paul;Happy;jdoe", in.readLine());
			Assert.assertEquals("asmile;;", in.readLine());
			Assert.assertNull(in.readLine());
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}

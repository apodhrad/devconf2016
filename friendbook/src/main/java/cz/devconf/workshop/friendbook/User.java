package cz.devconf.workshop.friendbook;

public class User {

	private String id;
	private String name;
	private String surname;

	public User(String id) {
		this(id, null, null);
	}

	public User(String id, String name, String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "User [id = " + id + ", name=" + name + ", surname=" + surname + "]";
	}
}

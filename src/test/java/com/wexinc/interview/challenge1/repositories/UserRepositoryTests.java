package com.wexinc.interview.challenge1.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.wexinc.interview.challenge1.AppModule;
import com.wexinc.interview.challenge1.models.AccessLevel;
import com.wexinc.interview.challenge1.models.User;

public class UserRepositoryTests {

	private UserRepo repo;
	private static final String TestUserHash = "1234567890";
	private static final String TestUserName = "Test User";

	@Before
	public void setup() {
		repo = Guice.createInjector(new AppModule()).getInstance(UserRepo.class);
	}

	@Test
	public void canCreateUser() {
		User user = repo.createUser(TestUserName, TestUserHash, AccessLevel.User);

		assertNotNull(user);
		assertEquals(TestUserName, user.getName());
		assertEquals(TestUserHash, user.getPassHash());
		assertNotEquals(0, user.getId());
	}

	@Test
	public void canLoadUser() {
		User originalUser = repo.createUser(TestUserName, TestUserHash, AccessLevel.User);

		User user = repo.loadUser(originalUser.getId());

		assertNotNull(user);
		assertEquals(originalUser.getId(), user.getId());
		assertEquals(originalUser.getName(), user.getName());
		assertEquals(originalUser.getPassHash(), user.getPassHash());
		assertEquals(originalUser.getAccess(), user.getAccess());
	}

	@Test
	public void canSaveUser() {
		User originalUser = repo.createUser(TestUserName, TestUserHash, AccessLevel.User);
		User newUser = new User(originalUser.getId(), "New Name", "HASHREPLACE", AccessLevel.Admin);

		repo.saveUser(newUser);

		User user = repo.loadUser(originalUser.getId());

		assertNotNull(user);
		assertEquals(originalUser.getId(), user.getId());
		assertEquals(newUser.getName(), user.getName());
		assertEquals(newUser.getPassHash(), user.getPassHash());
		assertEquals(newUser.getAccess(), user.getAccess());
	}

	@Test
	public void canFindByName() {
		repo.createUser("testName", "1234", AccessLevel.User);
		repo.createUser("targetName", "hashThing", AccessLevel.User);

		final User result = repo.getByName("targetName");

		assertNotNull(result);
		assertNotEquals(0, result.getId());
	}
}

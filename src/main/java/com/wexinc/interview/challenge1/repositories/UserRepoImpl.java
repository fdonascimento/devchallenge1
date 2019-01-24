package com.wexinc.interview.challenge1.repositories;


import java.util.HashMap;

import com.wexinc.interview.challenge1.models.AccessLevel;
import com.wexinc.interview.challenge1.models.User;

public class UserRepoImpl implements UserRepo {

	private final HashMap<Integer, User> userStore = new HashMap<Integer, User>();
	private int userNumber = 0;

	@Override
	public User createUser(String name, String passwordHash, AccessLevel access) {
		int userId;
		User user;
		
		synchronized (userStore) {
			userNumber++;
			userId = userNumber;
			user = new User(userId, name, passwordHash, access);
			userStore.put(userId, user);
		}
		
		return user;
	}

	@Override
	public User loadUser(int userId) {
		return userStore.containsKey(userId) ? userStore.get(userId) : null;
	}
	
	@Override
	public void saveUser(User userToUpdate) {
		if (userStore.containsKey(userToUpdate.getId())) {
			synchronized (userStore) {
				userStore.put(userToUpdate.getId(), userToUpdate);
			}
		}
	}
	
	@Override
	public User getByName(String name) {
		return userStore.values().stream()
			.filter(user -> user.getName().contentEquals(name))
			.findAny().orElse(null);
	}

}

package com.wexinc.interview.challenge1.repositories;

import com.wexinc.interview.challenge1.models.AccessLevel;
import com.wexinc.interview.challenge1.models.User;

public interface UserRepo {
	public User createUser(String name, String passwordHash, AccessLevel access);

	public User loadUser(int userId);
	
	public void saveUser(User userToUpdate);
	
	public User getByName(String name);
}

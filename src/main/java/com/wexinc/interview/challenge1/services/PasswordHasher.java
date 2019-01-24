package com.wexinc.interview.challenge1.services;

public interface PasswordHasher {
	public String hash(String password, String salt);
}

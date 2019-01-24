package com.wexinc.interview.challenge1.services;

import org.junit.Test;

import spark.utils.Assert;

public class PasswordHasherTests {
	@Test
	public void hashesAre64Digits() {
		PasswordHasher hasher = new PasswordHasherImpl();
		int userId = 12;
		String password = "someSimplePassword";
		
		String hash = hasher.hash(password, String.valueOf(userId));
		Assert.isTrue(hash.length() == 64, "Hashes are not the right length");
	}
}

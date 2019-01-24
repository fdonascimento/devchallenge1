package com.wexinc.interview.challenge1.services;

import java.nio.charset.Charset;

import com.google.common.hash.Hashing;

public class PasswordHasherImpl
	implements PasswordHasher {
	
	private static final String saltFormat="%1$s::%2$s::%1$s";
	
	@Override
	public String hash(String password, String salt) {
		return Hashing.sha256()
				.hashString(String.format(saltFormat, salt, password), Charset.defaultCharset())
				.toString();
	}

}

package com.wexinc.interview.challenge1;

import com.google.inject.Singleton;
import com.wexinc.interview.challenge1.repositories.ThreadRepo;
import com.wexinc.interview.challenge1.repositories.ThreadRepoImpl;
import com.wexinc.interview.challenge1.repositories.UserRepo;
import com.wexinc.interview.challenge1.repositories.UserRepoImpl;
import com.wexinc.interview.challenge1.services.AuthManager;
import com.wexinc.interview.challenge1.services.AuthManagerImpl;
import com.wexinc.interview.challenge1.services.PasswordHasher;
import com.wexinc.interview.challenge1.services.PasswordHasherImpl;

public class AppModule extends com.google.inject.AbstractModule {

	@Override
	protected void configure() {
		bind(AuthManager.class).to(AuthManagerImpl.class).in(Singleton.class);
		bind(ThreadRepo.class).to(ThreadRepoImpl.class).in(Singleton.class);
		bind(UserRepo.class).to(UserRepoImpl.class).in(Singleton.class);
		bind(PasswordHasher.class).to(PasswordHasherImpl.class);
	}
}

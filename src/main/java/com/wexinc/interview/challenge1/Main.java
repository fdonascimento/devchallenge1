package com.wexinc.interview.challenge1;

import static spark.Spark.*;
import com.google.inject.Guice;
import com.wexinc.interview.challenge1.controllers.AuthController;
import com.wexinc.interview.challenge1.controllers.ThreadsController;
import com.wexinc.interview.challenge1.models.AccessLevel;
import com.wexinc.interview.challenge1.repositories.UserRepo;
import com.wexinc.interview.challenge1.services.PasswordHasher;
import com.wexinc.interview.challenge1.util.Filters;

import lombok.val;

public class Main {
	public static void main(String[] args) {
		val injector = Guice.createInjector(new AppModule());
		
		initializeAdminUser(injector.getInstance(UserRepo.class), injector.getInstance(PasswordHasher.class));
		
		before("*", Filters.addTrailingSlashes);
		
		injector.getInstance(ThreadsController.class);
		injector.getInstance(AuthController.class);
		
		after("*", Filters.addGzipHeader);
		after("*", Filters.addJsonContentType);
		
		exception(AuthorizationException.class, (e, req, resp) -> {
			resp.status(403);
			resp.body("");
			resp.header("Content-Type", "application/json");
		});
	}
	
	private static void initializeAdminUser(UserRepo repo, PasswordHasher hasher) {
		repo.createUser("admin", hasher.hash("admin", "salt"), AccessLevel.Admin);
	}
}

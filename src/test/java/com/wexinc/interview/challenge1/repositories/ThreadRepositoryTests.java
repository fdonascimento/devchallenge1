package com.wexinc.interview.challenge1.repositories;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.google.inject.Guice;
import com.wexinc.interview.challenge1.AppModule;
import com.wexinc.interview.challenge1.models.*;

public class ThreadRepositoryTests {
	private ThreadRepo repo;
	

	@Before
	public void setup() {
		repo = Guice.createInjector(new AppModule()).getInstance(ThreadRepo.class);
	}
	
	@Test
	public void canGetAll() {
		int expectedSize = repo.getAll().size() + 2;
		repo.createMessage(1, "Test 1");
		repo.createMessage(1, "Test 2");
		
		Collection<MsgThread> threads = repo.getAll();
		assertEquals(expectedSize, threads.size());
	}
	
	@Test
	public void canGetOne() {
		MsgThread thread = repo.createMessage(1, "Test");
		
		thread = repo.get(thread.getId());
		
		assertNotNull(thread);
	}
	
	@Test
	public void canAddMessage() {
		MsgThread thread = repo.createMessage(1, "New message");
		repo.createMessage(thread.getId(), 1, "Another message");
		
		thread = repo.get(thread.getId());

		assertEquals(2, thread.getMessages().size());
	}
	
	@Test
	public void canCreateNewThread() {
		String msgText = "Test message 1";
		
		MsgThread thread = repo.createMessage(1, msgText);
		
		assertNotNull(thread);
		assertEquals(1, thread.getMessages().size());
		assertEquals(msgText, thread.getMessages().get(0).getText());
	}
}

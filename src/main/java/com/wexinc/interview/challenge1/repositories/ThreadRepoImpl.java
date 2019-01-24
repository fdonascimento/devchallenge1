package com.wexinc.interview.challenge1.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.wexinc.interview.challenge1.models.Message;
import com.wexinc.interview.challenge1.models.MsgThread;

public class ThreadRepoImpl implements ThreadRepo {
	
	private final HashMap<Integer, MsgThread> threadStore = new HashMap<Integer, MsgThread>();
	private int threadId = 0;
	private int messageId = 0;
	
	@Override
	public Collection<MsgThread> getAll() {
		return Collections.unmodifiableCollection(threadStore.values());
	}

	@Override
	public MsgThread get(int threadId) {
		return threadStore.get(threadId);
	}

	@Override
	public MsgThread createMessage(int threadId, int userId, String text) {
		MsgThread thread = get(threadId);
		if (thread == null)
			return null;

		synchronized (threadStore) {
			messageId++;
			thread.getMessages().add(new Message(messageId, userId, threadId, text));
		}

		return thread;
	}

	@Override
	public MsgThread createMessage(int userId, String text) {
		synchronized (threadStore) {
			threadId++;
			messageId++;
			MsgThread thread = new MsgThread(threadId, text, new ArrayList<Message>());
			thread.getMessages().add(new Message(messageId, userId, threadId, text));
			threadStore.put(thread.getId(), thread);
			return thread;
		}
	}

}

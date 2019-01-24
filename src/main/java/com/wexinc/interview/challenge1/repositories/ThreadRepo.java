package com.wexinc.interview.challenge1.repositories;

import java.util.Collection;

import com.wexinc.interview.challenge1.models.MsgThread;

public interface ThreadRepo {
	public Collection<MsgThread> getAll();
	public MsgThread get(int threadId);
	public MsgThread createMessage(int userId, String text);
	public MsgThread createMessage(int threadId, int userId, String text);
}

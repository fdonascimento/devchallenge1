package com.wexinc.interview.challenge1.models;

public class Message {
	private int id;
	private int userId;
	private int threadId;
	private String text;

	public Message(int id, int userId, int threadId, String text) {
		super();
		this.id = id;
		this.userId = userId;
		this.threadId = threadId;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public int getThreadId() {
		return threadId;
	}
}

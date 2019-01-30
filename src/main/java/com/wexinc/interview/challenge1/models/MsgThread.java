package com.wexinc.interview.challenge1.models;

import java.util.LinkedList;
import java.util.List;

public class MsgThread {
	private int id;
	private String title;
	private List<Message> messages = new LinkedList<Message>();

	public MsgThread(int id, String title, List<Message> messages) {
		super();
		this.id = id;
		this.title = title;
		this.messages = messages;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public List<Message> getMessages() {
		return messages;
	}

}

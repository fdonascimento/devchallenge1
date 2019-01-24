package com.wexinc.interview.challenge1.models;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MsgThread {
	@Getter private int id;
	@Getter private String title;
	@Getter private List<Message> messages = new LinkedList<Message>();
}

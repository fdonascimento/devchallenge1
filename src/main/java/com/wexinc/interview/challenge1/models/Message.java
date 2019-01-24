package com.wexinc.interview.challenge1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class Message {
	@Getter private int id;
	@Getter private int userId;
	@Getter private int threadId;
	@Getter @Setter @NonNull private String text;
}

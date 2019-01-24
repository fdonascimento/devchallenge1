package com.wexinc.interview.challenge1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class User {
	@Getter private int id;
	@Getter private String name;
	@Getter @Setter @NonNull private String passHash;
	@Getter @Setter private AccessLevel access;
}

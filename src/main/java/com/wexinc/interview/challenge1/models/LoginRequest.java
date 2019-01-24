package com.wexinc.interview.challenge1.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginRequest {
	@NonNull private String userName;
	@NonNull private String password;
}

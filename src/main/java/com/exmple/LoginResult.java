package com.exmple;

import com.exmple.utils.SimpleResult;

public class LoginResult extends SimpleResult {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

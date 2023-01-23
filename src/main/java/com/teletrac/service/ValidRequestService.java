package com.teletrac.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValidRequestService {

	private String accessToken;
	
	public ValidRequestService(@Value("${app.token}") String accessToken) {
		this.accessToken = accessToken;
	}

	public boolean isValidToken(String accessToken) {
		return accessToken.endsWith(this.accessToken);
	}
}

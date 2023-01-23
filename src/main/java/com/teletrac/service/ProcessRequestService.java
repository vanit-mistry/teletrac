package com.teletrac.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.teletrac.domain.RequestAudit;
import com.teletrac.dto.FullPayloadDto;
import com.teletrac.repository.RequestAuditRepository;

import jakarta.ws.rs.NotAuthorizedException;

@Service
public class ProcessRequestService {

	@Autowired
	private ValidRequestService validRequestService;
	
	@Autowired
	private RequestAuditRepository requestAuditRepository;
	
	private Gson gson;
	
	public ProcessRequestService(Gson gson) {
		this.gson = new Gson();
	}

	public void processRequest(String action, Map<String, String> headers, FullPayloadDto rqs) {
		
		boolean isValidRequest = validRequestService.isValidToken(headers.get("authorization"));
		String host = headers.get("host");
		String userName = "user";
		
		saveRequestAudit(userName, host, action, rqs, isValidRequest);
		if (!isValidRequest) {
			throw new NotAuthorizedException("Not authorized");
		}
	}

	private void saveRequestAudit(String userName, String host, String action, FullPayloadDto rqs, boolean isValidRequest) {
		RequestAudit audit = new RequestAudit();
		audit.setAction(action);
		audit.setHost(host);
		audit.setUserName(userName);
		audit.setRequest(gson.toJson(rqs));
		audit.setErrored(isValidRequest);
		requestAuditRepository.save(audit);
	}
	
}

package com.teletrac.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.teletrac.dto.DeviceIdPayloadDto;
import com.teletrac.dto.FullPayloadDto;
import com.teletrac.service.ProcessRequestService;

import jakarta.validation.Valid;
import jakarta.ws.rs.NotAuthorizedException;

@RestController
@RequestMapping("/teletrac")
public class TeletracController {
	
	@Autowired
	private ProcessRequestService processRequestService;

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("Hello!!!", HttpStatus.OK);
	}

	@PostMapping("/nocontent")
	public ResponseEntity<String> nocontent(@RequestHeader Map<String, String> headers, @RequestBody @Valid FullPayloadDto rqs) {	
		processRequestService.processRequest("nocontent", headers, rqs);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/echo")
	public ResponseEntity<FullPayloadDto> echo(@RequestHeader Map<String, String> headers, @RequestBody @Valid FullPayloadDto rqs) {
		processRequestService.processRequest("echo", headers, rqs);
		return new ResponseEntity<FullPayloadDto>(rqs, HttpStatus.OK);
	}
	
	@PostMapping("/device")
	public ResponseEntity<DeviceIdPayloadDto> device(@RequestHeader Map<String, String> headers,  @RequestBody @Valid FullPayloadDto rqs) {
		processRequestService.processRequest("device", headers, rqs);
		DeviceIdPayloadDto rsp = new DeviceIdPayloadDto(rqs.getDeviceId());
		return new ResponseEntity<DeviceIdPayloadDto>(rsp, HttpStatus.OK);
	}
	
	@ExceptionHandler({RuntimeException.class, HttpClientErrorException.class, MethodArgumentNotValidException.class, IllegalArgumentException.class})
	public ResponseEntity<Void> handleException() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotAuthorizedException.class)
	public ResponseEntity<Void> handleNotAuthorizedException() {
		return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
	}
	
}

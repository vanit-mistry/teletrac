package com.teletrac.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.teletrac.domain.RequestAudit;
import com.teletrac.dto.FullPayloadDto;
import com.teletrac.repository.RequestAuditRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TeletracControllerIntegrationTest {

	@Autowired
	RequestAuditRepository requestAuditRepository;
	
	@Autowired
	private TestRestTemplate restTemplate;

	// bind the above RANDOM_PORT
	@LocalServerPort
	private int port;
	
	private static HttpHeaders headers;
	
    @BeforeAll
    static void setup() {
		headers = new HttpHeaders();
		headers.setBearerAuth("testToken");
    }
	
	@Test
	void helloTest() throws MalformedURLException {
		String url = "http://localhost:" + port + "/teletrac/hello";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		
		String body = response.getBody();
		assertThat(true, is(body.contains("Hello!!!")));
		assertThat(HttpStatus.OK, equalTo(response.getStatusCode()));
	}

	@Test
	void noContentTest() throws MalformedURLException, URISyntaxException {		
		RestTemplate restTemplateLcl = new RestTemplate();
		FullPayloadDto dto = new FullPayloadDto("xxx", "123", "datetime", 1, "abc", 2d);
		
		HttpEntity<FullPayloadDto> httpEntity = new HttpEntity<>(dto, headers);
		URI uri = new URI("http://localhost:" + port + "/teletrac/nocontent");
		ResponseEntity<String> response = restTemplateLcl.postForEntity(uri, httpEntity, String.class);
		
		String body = response.getBody();
		assertThat(null, equalTo(body));
		assertThat(HttpStatus.NO_CONTENT, equalTo(response.getStatusCode()));
		
		reportAuditTest("nocontent");
	}
	
	@Test
	void echoTest() throws MalformedURLException, URISyntaxException {		
		RestTemplate restTemplateLcl = new RestTemplate();
		FullPayloadDto dto = new FullPayloadDto("xxx", "123", "datetime", 1, "abc", 2d);
		
		HttpEntity<FullPayloadDto> httpEntity = new HttpEntity<>(dto, headers);
		URI uri = new URI("http://localhost:" + port + "/teletrac/echo");
		ResponseEntity<String> response = restTemplateLcl.postForEntity(uri, httpEntity, String.class);
		
		String body = response.getBody();
		assertThat(true, is(body!=null));
		assertThat(true, is(body.contains("RecordType")));
		assertThat(true, is(body.contains("DeviceId")));
		assertThat(true, is(body.contains("EventDateTime")));
		assertThat(true, is(body.contains("FieldA")));
		assertThat(true, is(body.contains("FieldB")));
		assertThat(true, is(body.contains("FieldC")));
		assertThat(HttpStatus.OK, equalTo(response.getStatusCode()));
		
		reportAuditTest("echo");
	}
	
	@Test
	void deviceIdTest() throws MalformedURLException, URISyntaxException {		
		RestTemplate restTemplateLcl = new RestTemplate();
		FullPayloadDto dto = new FullPayloadDto("xxx", "123", "datetime", 1, "abc", 2d);
		
		HttpEntity<FullPayloadDto> httpEntity = new HttpEntity<>(dto, headers);
		URI uri = new URI("http://localhost:" + port + "/teletrac/device");
		ResponseEntity<String> response = restTemplateLcl.postForEntity(uri, httpEntity, String.class);
		
		String body = response.getBody();
		assertThat(true, is(body!=null));
		assertThat(false, is(body.contains("RecordType")));
		assertThat(true, is(body.contains("DeviceId")));
		assertThat(false, is(body.contains("EventDateTime")));
		assertThat(false, is(body.contains("FielA")));
		assertThat(false, is(body.contains("FieldB")));
		assertThat(false, is(body.contains("FieldC")));
		assertThat(HttpStatus.OK, equalTo(response.getStatusCode()));
		
		reportAuditTest("device");
	}	
	
	private void reportAuditTest(String action) {
		Optional<RequestAudit> audit = requestAuditRepository.findByAction(action);
		assertThat(true, is(audit.isPresent()));
		assertThat(action, equalTo(audit.get().getAction()));
		requestAuditRepository.delete(audit.get());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"nocontent", "echo", "device"})
	void invalidTokenTest(String action) throws MalformedURLException, URISyntaxException {
		RestTemplate restTemplateLcl = new RestTemplate();
		HttpHeaders headersLcl = new HttpHeaders();
		headersLcl.setBearerAuth("invalid");
		FullPayloadDto dto = new FullPayloadDto("xxx", "123", "datetime", 1, "abc", 2d);
		
		HttpEntity<FullPayloadDto> httpEntity = new HttpEntity<>(dto, headersLcl);
		URI uri = new URI("http://localhost:" + port + "/teletrac/" + action);
		ResponseEntity<String> response = restTemplateLcl.postForEntity(uri, httpEntity, String.class);
		
		String body = response.getBody();
		assertThat(null, equalTo(body));
		assertThat(HttpStatus.NON_AUTHORITATIVE_INFORMATION, equalTo(response.getStatusCode()));
		requestAuditRepository.deleteAll();
	}

	/* unable to test bad request */
	@Test
	@Disabled
	void badRequestTest() throws MalformedURLException, URISyntaxException {
		RestTemplate restTemplateLcl = new RestTemplate();
		FullPayloadDto dto = new FullPayloadDto("xxx", "123", "2014-05-12T05:09:48Z", 1, "abc", 2d);
		
		HttpEntity<FullPayloadDto> httpEntity = new HttpEntity<>(dto, headers);
		URI uri = new URI("http://localhost:" + port + "/teletrac/invalid");
		ResponseEntity<String> response = restTemplateLcl.postForEntity(uri, httpEntity, String.class);
		
		String body = response.getBody();
		assertThat(null, equalTo(body));
		assertThat(HttpStatus.BAD_REQUEST, equalTo(response.getStatusCode()));
	}
}

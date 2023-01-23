package com.teletrac.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ValidRequestServiceTest {

    @Autowired
    ValidRequestService validRequestService = new ValidRequestService("testToken");
    
	@Test
	void validTokenTest() {
		boolean valid = validRequestService.isValidToken("testToken");
		assertThat(true, is(valid));
	}
	
	@Test
	void invalidTokenTest() {
		boolean valid = validRequestService.isValidToken("invalid");
		assertThat(false, is(valid));
	}
}

package com.teletrac.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.teletrac.domain.RequestAudit;

/* Test does not work, dependency issue */ 

@DataJpaTest
@Disabled
public class RequestAuditRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;

	private RequestAuditRepository requestAuditRepository;

	@Test
	void saveReportAuditTest() {
		
		// given
		RequestAudit audit = new RequestAudit();
		audit.setAction("nocontent");
		audit.setErrored(false);
		audit.setUserName("username");
		audit.setRequest("{}");
		entityManager.persist(audit);
		entityManager.flush();
		
		// when
		Optional<RequestAudit> requestAudit = requestAuditRepository.findById(1);

		// then
		assertThat("nocontent", equalTo(requestAudit.get().getAction()));
	}
}

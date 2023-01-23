package com.teletrac.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teletrac.domain.RequestAudit;

@Repository
public interface RequestAuditRepository extends JpaRepository<RequestAudit, Integer> {

	Optional<RequestAudit> findByAction(String action);
	
}

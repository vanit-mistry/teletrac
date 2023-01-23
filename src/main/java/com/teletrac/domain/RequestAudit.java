package com.teletrac.domain;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "REQUEST_AUDIT")
@Data
public class RequestAudit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String host;
	private String action;
	private String userName;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;
	
	private boolean errored = false;
	private String request;

	public RequestAudit() {
	}

	public RequestAudit(Integer id, String host, String action, String userName, Timestamp processDate, String request, boolean errored) {
		this.id = id;
		this.host = host;
		this.action = action;
		this.userName = userName;
		this.processDate = processDate;
		this.request = request;
		this.errored = errored;
	}
}

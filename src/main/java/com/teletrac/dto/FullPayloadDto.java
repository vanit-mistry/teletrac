package com.teletrac.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"recordType", "deviceId","eventDateTime", "fieldA", "fieldB", "fieldC" })
public class FullPayloadDto extends DeviceIdPayloadDto {

	@NotEmpty
	@JsonProperty(value = "RecordType")
	private String recordType;
	
	@NotEmpty
	@JsonProperty(value ="EventDateTime")
	private String eventDateTime;
	
	@Min(value = 0)
	@JsonProperty(value ="FieldA")
	private int fieldA;
	
	@NotEmpty
	@JsonProperty(value ="FieldB")
	private String fieldB;
	
	@Min(value = 0)
	@JsonProperty(value ="FieldC")
	private double fieldC;
	
	public FullPayloadDto(String recordType, String deviceId, String eventDateTime, int fieldA, String fieldB, double fieldC) {
		super(deviceId);
		this.recordType = recordType;
		this.eventDateTime = eventDateTime;
		this.fieldA = fieldA;
		this.fieldB = fieldB;
		this.fieldC = fieldC;
	}
	
}

package com.teletrac.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeviceIdPayloadDto {
	@NotEmpty
	@JsonProperty(value ="DeviceId")
	private String deviceId;
	
	public DeviceIdPayloadDto(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}

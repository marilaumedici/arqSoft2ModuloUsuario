package com.arqsoft.medici.infrastructure.rest.dto;

public class ErrorResponse {
	
	public ErrorResponse(String timestamp, String status, String error, String message) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}
	
	public ErrorResponse() {
	}
	
	private String timestamp;
	private String status;
	private String error;
	private String message;
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	

}

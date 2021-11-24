package com.guilhermeoscp.apisistemaescolar.error;

import javax.annotation.Generated;

public class ErrorDetails {

	private String title;
	private int status;
	private String detail;
	private long timestamp;
	private String developerMessage;
		
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getDeveloperMessage() {
		return developerMessage;
	}
	
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	
	@Generated("SparkTools")
	public static final class Builder {
		private String title;
		private int status;
		private String detail;
		private long timestamp;
		private String developerMessage;

		private Builder() {
		}
		
		@Generated("SparkTools")
		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder Title(String title) {
			this.title = title;
			return this;
		}

		public Builder Status(int status) {
			this.status = status;
			return this;
		}

		public Builder Detail(String detail) {
			this.detail = detail;
			return this;
		}

		public Builder Timestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder DeveloperMessage(String developerMessage) {
			this.developerMessage = developerMessage;
			return this;
		}

		public ErrorDetails builder() {
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setTitle(title); 
			errorDetails.setStatus(status);
			errorDetails.setDetail(detail); 
			errorDetails.setTimestamp(timestamp); 
			errorDetails.setDeveloperMessage(developerMessage);
			return errorDetails;
		}
	}	
}

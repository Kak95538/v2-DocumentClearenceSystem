package com.example.notification.entity;

public class NotificationRequest {

    private String to;
    private String subject;
    private String message;
    private String type; // EMAIL / SMS / IN_APP

    public NotificationRequest() {}

    public NotificationRequest(String to, String subject, String message, String type) {
        this.to = to;
        this.subject = subject;
        this.message = message;
        this.type = type;
    }

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    // getters and setters
    
    
    
}
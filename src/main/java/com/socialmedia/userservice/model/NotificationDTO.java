package com.socialmedia.userservice.model;

public class NotificationDTO {

	private String event;
	private int Userid;
	private String Username = "";

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getUserid() {
		return Userid;
	}

	public void setUserid(int userid) {
		Userid = userid;
	}

	public NotificationDTO() {
		super();
	}

	public NotificationDTO(String event, int userid) {
		super();
		this.event = event;
		Userid = userid;
	}

	public NotificationDTO(String event, String username) {
		super();
		this.event = event;
		this.Username = username;
	}
}

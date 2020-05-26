package com.socialmedia.userservice.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

@Table(name = "user")
@Embeddable
@Entity
public class User {
	@Id
	@GeneratedValue
	private int userId;

	@Column(unique = true)
	@NotEmpty
	@Email
	private String username;

	@NotNull
	@NotEmpty
	private String password;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User() {
		super();

	}

	public User(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password) {
		super();
		this.username = username;
		this.password = password;
	}

}

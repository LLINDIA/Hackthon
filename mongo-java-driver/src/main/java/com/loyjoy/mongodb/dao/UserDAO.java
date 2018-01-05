package com.loyjoy.mongodb.dao;

public class UserDAO {

	private String id;
	private String first_name;
	private String last_name;
	private String email_id;

	public UserDAO(String id, String first_name, String last_name, String email_id) {
		this.id = id;
		this.email_id = email_id;
		this.first_name = first_name;
		this.last_name = last_name;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

}

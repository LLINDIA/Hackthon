package com.loyjoy.mongodb.dao;

public interface User {

	public void create(UserDAO usr);

	public UserDAO readById(String id);

	public void update(UserDAO p);

}

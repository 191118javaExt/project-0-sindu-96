package com.revature.repositories;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {
	public User getUser(String username, int password );
	public boolean addUser(User u);
	public User findById(int id);
	public List<User> findAll();	
}
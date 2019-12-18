package com.revature.services;

import java.util.List;

import com.revature.models.User;
import com.revature.repositories.UserDAOImpl;

public class UserServices {

	public static UserDAOImpl ud = new UserDAOImpl();
	
	public static User getUser(int id) {
		return ud.findById(id);	
	}
	
	public static User getUser(String username, int password) {
		return ud.getUser(username, password);
	}
	
	public static List<User> getAllUsers() {
		return ud.findAll();
	}
	
	public static boolean addUser(User u) {
		return ud.addUser(u);
	}
}

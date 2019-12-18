package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.MyLogger;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAOImpl implements UserDAO {
		
	@Override
	public User getUser(String username, int password) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from users where username = ? and pass = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, username);
			stmt.setInt(2, password);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				return new User(
						rs.getInt("U_id"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("username"),
						rs.getInt("pass"));
			}
		} catch(SQLException ex) {
			System.out.println(ex);
			MyLogger.logger.warn("Unable to retrieve user by given information", ex);
			return null;
		}
		
		return null;
	}

	@Override
	public boolean addUser(User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "INSERT into users (firstName, lastName, username, pass) values (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, u.getFirstName());
			stmt.setString(2, u.getLastName());
			stmt.setString(3, u.getUsername());
			stmt.setInt(4, u.getPassword());
						
			stmt.execute();
			
		} catch(SQLException ex) {
			MyLogger.logger.warn("Unable to add user", ex);
			System.out.println(ex);
			return false;
		}
		
		return true;
	}

	@Override
	public User findById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "select * from users where U_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return new User(
						rs.getInt("U_id"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("username"),
						rs.getInt("pass"));
			}
		} catch(SQLException ex) {
			MyLogger.logger.warn("Unable to find user", ex);
			return null;
		}
		
		return null;
	}

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<User>();
	
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "select * from users";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				list.add(new User(
						rs.getInt("U_id"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("username"),
						rs.getInt("pass")));
			}
		} catch(SQLException ex) {
			MyLogger.logger.warn("Unable to retrieve all users", ex);
			return null;
		}
		return list;
	}

}

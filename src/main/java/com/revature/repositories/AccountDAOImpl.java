package com.revature.repositories;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.MyLogger;
import com.revature.models.Account;
import com.revature.util.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public Account getAccount(int a_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from accounts where A_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, a_id);
		
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				return new Account(
						rs.getInt("A_id"),
						rs.getInt("U_id"),
						rs.getDouble("balance"),
						rs.getInt("status"));
			}
		} catch(SQLException ex) {
			System.out.println(ex);
			MyLogger.logger.warn("Unable to get account", ex);
			return null;
		}
		return null;
	}

	@Override
	public List<Account> findByU_id(int u_id) {
		List<Account> list = new ArrayList<Account>();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select * from accounts where U_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, u_id);
			
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				list.add(new Account(
						rs.getInt("A_id"),
						rs.getInt("U_id"),
						rs.getDouble("balance"),
						rs.getInt("status")));
			}
		} catch(SQLException ex) {
			System.out.println(ex);
			MyLogger.logger.warn("Unable to retrieve user accounts", ex);
			return null;
		}
		return list;
	}

	@Override
	public boolean addAccount(int u_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "insert into accounts (U_id) values (?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, u_id);
			
			stmt.execute();
		
		} catch(SQLException ex) {
			MyLogger.logger.warn("Unable to add account", ex);
			System.out.println(ex);
			return false;
		}
		
		return true;
	}

	@Override
	public List<Account> findUnapproved() {
		List<Account> list = new ArrayList<Account>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "select * from accounts where status = 0";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				list.add(new Account(
						rs.getInt("A_id"),
						rs.getInt("U_id"),
						rs.getDouble("balance"),
						rs.getInt("status")));
			}
		} catch(SQLException ex) {
//			logger.warn("Unable to retrieve all users", ex);
			return null;
		}
		return list;
	}

	@Override
	public boolean approve(int a_id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "update accounts set status = 1 where A_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, a_id);
			
			if(!stmt.execute()) {
				return false;
			}
			
		} catch(SQLException ex) {
//			logger.warn("Unable to retrieve all users", ex);
			System.out.println(ex);
			return false;
		}
		
		return true;
	}

	@Override
	public boolean withdraw(int a_id, double amount) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "CALL withdraw(?, ?)";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setDouble(1, amount);
			stmt.setInt(2, a_id);
			
			stmt.execute();
			return true;
			
		} catch(SQLException ex) {
			MyLogger.logger.warn("Unable to withdraw given amount", ex);
			System.out.println(ex);
			return false;
		}
	}

	@Override
	public boolean deposit(int a_id, double amount) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "CALL deposit(?, ?)";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setDouble(1, amount);
			stmt.setInt(2, a_id);
			
			stmt.execute();
			return true;
			
		} catch(SQLException ex) {
			MyLogger.logger.warn("Unable to deposit", ex);
			System.out.println(ex);
			return false;
		}
	}

}

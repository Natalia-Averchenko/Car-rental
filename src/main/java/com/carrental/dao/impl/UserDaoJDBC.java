package com.carrental.dao.impl;

import com.carrental.dao.UserDao;
import com.carrental.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC implements UserDao {
    private final Connection con;

    public UserDaoJDBC(Connection con) {
        this.con = con;
    }

    public User create(User newUser) throws SQLException {
        String sql = "INSERT INTO users (first_name,last_name,middle_name,date_of_birth,passport_number,date_of_issue) VALUES (?,?,?,?,?,?) returning user_id";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, newUser.getFirstName());
        ps.setString(2, newUser.getLastName());
        ps.setString(3, newUser.getMiddleName());
        ps.setDate(4, newUser.getDateOfBirth());
        ps.setLong(5, newUser.getPassportNumber());
        ps.setDate(6, newUser.getDateOfIssue());
        ResultSet rs = ps.executeQuery();
        rs.next();
        newUser.setUserId(rs.getInt("user_id"));
        rs.close();
        ps.close();
        return newUser;
    }

    public User read(Integer id) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("first_name"), rs.getString("last_name"), rs.getString("middle_name"), rs.getDate("date_of_birth"), rs.getLong("passport_number"), rs.getDate("date_of_issue"));
        user.setUserId(rs.getInt("user_id"));
        rs.close();
        ps.close();
        return user;
    }

    public void update(User user, Integer id) throws SQLException {
        String sql = "UPDATE users SET first_name=?, last_name=?, middle_name=?, date_of_birth=?, passport_number=?, date_of_issue=? WHERE user_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getMiddleName());
        ps.setDate(4, user.getDateOfBirth());
        ps.setLong(5, user.getPassportNumber());
        ps.setDate(6, user.getDateOfIssue());
        ps.setInt(7, id);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public List<User> getUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<User> allUsers = new ArrayList<>();
        while (rs.next()) {
            User user = new User(rs.getString("first_name"), rs.getString("last_name"), rs.getString("middle_name"), rs.getDate("date_of_birth"), rs.getLong("passport_number"), rs.getDate("date_of_issue"));
            user.setUserId(rs.getInt("user_id"));
            allUsers.add(user);
        }
        rs.close();
        ps.close();
        return allUsers;

    }

    public int checkUser(User user) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE first_name=? AND last_name=? AND middle_name=? AND date_of_birth=? AND passport_number=? AND date_of_issue=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getMiddleName());
        ps.setDate(4, user.getDateOfBirth());
        ps.setLong(5, user.getPassportNumber());
        ps.setDate(6, user.getDateOfIssue());
        ResultSet rs = ps.executeQuery();
        int id;
        if (rs.next()) {
            id = rs.getInt("user_id");
        } else id = -1;
        rs.close();
        ps.close();
        return id;
    }
}

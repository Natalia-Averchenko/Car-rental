package com.carrental.dao;

import com.carrental.model.User;

import java.sql.SQLException;
import java.util.List;


public interface UserDao extends Crud <User> {
    List<User> getUsers() throws SQLException;
    int checkUser(User user) throws SQLException;
}

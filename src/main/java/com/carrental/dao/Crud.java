package com.carrental.dao;

import java.sql.SQLException;

public interface Crud<T> {
    T create(T obj) throws SQLException;
    T read(Integer id) throws SQLException;
    void update(T obj,Integer id) throws SQLException;
    void delete(Integer id) throws SQLException;
}

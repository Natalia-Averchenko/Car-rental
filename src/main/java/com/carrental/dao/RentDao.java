package com.carrental.dao;

import com.carrental.model.Rent;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface RentDao extends Crud <Rent,Integer> {
    List<Rent> getRents() throws SQLException;
    Set<Integer> getCarsIDForRent(Timestamp fromDate, Timestamp toDate) throws SQLException;
}

package com.carrental.dao.impl;

import com.carrental.dao.RentDao;
import com.carrental.model.Rent;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RentDaoJDBC implements RentDao {

    private final Connection con;

    public RentDaoJDBC(Connection con) {
        this.con = con;
    }

    public Rent create(Rent newRent) throws SQLException {
        String sql = "INSERT INTO rent (car_id,user_id,from_date,to_date) VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, newRent.getCarId());
        ps.setInt(2, newRent.getUserId());
        ps.setTimestamp(3,newRent.getFromDate());
        ps.setTimestamp(4,newRent.getToDate());
        ps.executeUpdate();
        ps.close();
        return newRent;
    }

    public Rent read(Integer id) throws SQLException {
        String sql = "SELECT * FROM rent WHERE rent_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Rent rent = new Rent(rs.getInt("car_id"), rs.getInt("user_id"), rs.getTimestamp("from_date"), rs.getTimestamp("to_date"));
        rs.close();
        ps.close();
        return rent;
    }

    public void update(Rent rent, Integer id) throws SQLException {
        String sql = "UPDATE rent SET car_id=?, user_id=?, from_date=?, to_date=? WHERE rent_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, rent.getCarId());
        ps.setInt(2, rent.getUserId());
        ps.setTimestamp(3, rent.getFromDate());
        ps.setTimestamp(4, rent.getToDate());
        ps.setInt(5, id);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM rent WHERE rent_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public List<Rent> getRents() throws SQLException {
        String sql = "SELECT * FROM rent";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Rent> allRents = new ArrayList<>();
        while (rs.next()) {
            Rent rent = new Rent(rs.getInt("car_id"), rs.getInt("user_id"), rs.getTimestamp("from_date"), rs.getTimestamp("to_date"));
            allRents.add(rent);
        }
        rs.close();
        ps.close();
        return allRents;

    }

    public Set<Integer> getCarsIDForRent(Timestamp fromDate, Timestamp toDate) throws SQLException{
        String sql = "SELECT car_id FROM (cars LEFT JOIN (SELECT car_id,rent.user_id FROM (cars LEFT JOIN rent USING (car_id)) WHERE ( ? between from_date and to_date) OR ( ? between from_date and to_date)) AS notavailable USING (car_id)) WHERE user_id IS NULL";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setTimestamp(1,fromDate);
        ps.setTimestamp(2,toDate);
        ResultSet rs = ps.executeQuery();
        Set<Integer> carsIDForRent = new HashSet<>();
        while(rs.next()){
            carsIDForRent.add(rs.getInt("car_id"));
        }
        rs.close();
        ps.close();
        return carsIDForRent;
    }
}

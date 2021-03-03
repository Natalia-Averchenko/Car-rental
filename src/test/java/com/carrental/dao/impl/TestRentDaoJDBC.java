package com.carrental.dao.impl;

import com.carrental.dao.RentDao;
import com.carrental.model.Rent;
import com.carrental.service.Configuration;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TestRentDaoJDBC {
    Connection con;
    RentDao rentDao;

    @BeforeTest
    public void init() throws ClassNotFoundException, SQLException {
        Configuration conf = new Configuration();
        conf.setProperties();
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost/car_rental", conf.getUsername(), conf.getPassword());
        rentDao = new RentDaoJDBC(con);
    }

    @Test(groups = {"Add_new_rent_DAO"})
    public void testCreateRent() throws SQLException {
        String sql = "select exists(select 1 from rent where car_id = 10 AND user_id = 1 AND from_date = '2021-06-02 12:00:00' AND to_date ='2021-06-27 12:00:00')";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        rs1.next();
        boolean existBeforeAdd = rs1.getBoolean(1);
        Rent newRent = new Rent(10, 1, Timestamp.valueOf("2021-06-02 12:00:00"), Timestamp.valueOf("2021-06-27 12:00:00"));
        rentDao.create(newRent);
        ResultSet rs2 = st.executeQuery(sql);
        rs2.next();
        boolean existAfterAdd = rs2.getBoolean(1);
        rs1.close();
        rs2.close();
        st.close();
        Assert.assertFalse(existBeforeAdd);
        Assert.assertTrue(existAfterAdd);
    }

    @AfterGroups("Add_new_rent_DAO")
    public void deleteRentAfterAdd() throws SQLException {
        String sql = "DELETE FROM rent WHERE car_id = 10 AND user_id = 1 AND from_date = '2021-06-02 12:00:00' AND to_date ='2021-06-27 12:00:00'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testReadRent() throws SQLException {
        Rent rentRead = rentDao.read(1);
        Rent rentTest = new Rent(1, 1, Timestamp.valueOf("2021-01-14 00:00:00"), Timestamp.valueOf("2021-01-18 00:00:00"));
        Assert.assertEquals(rentTest, rentRead);
    }

    @Test(groups = {"Update_rent_DAO"})
    public void testUpdateRent() throws SQLException {
        String sql = "select exists(select 1 from rent where car_id = 8 AND user_id = 5 AND from_date = '2021-04-27 00:00:00' AND to_date ='2021-05-03 12:00:00')";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        rs1.next();
        boolean existBeforeUpdate = rs1.getBoolean(1);
        Rent updRent = new Rent(8, 5, Timestamp.valueOf("2021-04-27 00:00:00"), Timestamp.valueOf("2021-05-03 12:00:00"));
        rentDao.update(updRent, 10);
        ResultSet rs2 = st.executeQuery(sql);
        rs2.next();
        boolean existAfterUpdate = rs2.getBoolean(1);
        rs1.close();
        rs2.close();
        st.close();
        Assert.assertFalse(existBeforeUpdate);
        Assert.assertTrue(existAfterUpdate);
    }

    @AfterGroups("Update_rent_DAO")
    public void updateRentAfterUpdate() throws SQLException {
        String sql = "UPDATE rent SET from_date = '2021-04-27 00:00:00', to_date ='2021-04-29 00:00:00' WHERE rent_id = 10";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Test(groups = {"Delete_rent_DAO"})
    public void testDeleteRent() throws SQLException {
        String sql = "select exists(select 1 from rent WHERE car_id = 8 AND user_id = 5 AND from_date = '2021-04-27 00:00:00' AND to_date ='2021-04-29 00:00:00' )";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        rs1.next();
        boolean existBeforeDelete = rs1.getBoolean(1);
        rentDao.delete(10);
        ResultSet rs2 = st.executeQuery(sql);
        rs2.next();
        boolean existAfterDelete = rs2.getBoolean(1);
        rs1.close();
        rs2.close();
        st.close();
        Assert.assertTrue(existBeforeDelete);
        Assert.assertFalse(existAfterDelete);
    }

    @AfterGroups("Delete_rent_DAO")
    public void insertRentAfterDelete() throws SQLException {
        String sql = "INSERT INTO rent VALUES (10, 8, 5, '2021-04-27', '2021-04-29')";
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        st.close();
    }

    @Test
    public void testGetCarsIDForRent() throws SQLException {
        Timestamp fromDate = Timestamp.valueOf("2021-03-07 00:00:00");
        Timestamp toDate = Timestamp.valueOf("2021-05-21 12:00:00");
        Set<Integer> carsIDForRent = rentDao.getCarsIDForRent(fromDate, toDate);
        Set<Integer> carsIdForRentTest = new HashSet<>();
        carsIdForRentTest.add(1);
        carsIdForRentTest.add(2);
        carsIdForRentTest.add(5);
        carsIdForRentTest.add(6);
        carsIdForRentTest.add(7);
        carsIdForRentTest.add(9);
        carsIdForRentTest.add(10);
        carsIdForRentTest.add(11);
        Assert.assertEquals(carsIdForRentTest, carsIDForRent);
    }

    @Test
    public void testGetRents() throws SQLException {
        Rent rent1 = new Rent(1, 1, Timestamp.valueOf("2021-01-14 00:00:00"), Timestamp.valueOf("2021-01-18 00:00:00"));
        Rent rent2 = new Rent(6, 3, Timestamp.valueOf("2021-02-10 00:00:00"),Timestamp.valueOf("2021-02-23 00:00:00"));
        Rent rent3 = new Rent(4, 5, Timestamp.valueOf("2021-05-18 00:00:00"),Timestamp.valueOf( "2021-06-23 00:00:00"));
        Rent rent4 = new Rent(7, 1, Timestamp.valueOf("2020-10-13 00:00:00"),Timestamp.valueOf( "2020-10-14 00:00:00"));
        Rent rent5 = new Rent(9, 2, Timestamp.valueOf("2021-01-18 00:00:00"),Timestamp.valueOf( "2021-01-23 00:00:00"));
        Rent rent6 = new Rent(2, 7, Timestamp.valueOf("2020-06-05 00:00:00"),Timestamp.valueOf( "2020-07-01 00:00:00"));
        Rent rent7 = new Rent(5, 9, Timestamp.valueOf("2020-11-02 00:00:00"),Timestamp.valueOf( "2020-11-12 00:00:00"));
        Rent rent8 = new Rent(4, 10, Timestamp.valueOf("2020-07-24 00:00:00"),Timestamp.valueOf( "2020-08-03 00:00:00"));
        Rent rent9 = new Rent(3, 6, Timestamp.valueOf("2021-03-08 00:00:00"),Timestamp.valueOf( "2021-03-20 00:00:00"));
        Rent rent10 = new Rent(8, 5, Timestamp.valueOf("2021-04-27 00:00:00"),Timestamp.valueOf( "2021-04-29 00:00:00"));
        Set<Rent> setOfRentForTest = new HashSet<>();
        setOfRentForTest.add(rent1);
        setOfRentForTest.add(rent2);
        setOfRentForTest.add(rent3);
        setOfRentForTest.add(rent4);
        setOfRentForTest.add(rent5);
        setOfRentForTest.add(rent6);
        setOfRentForTest.add(rent7);
        setOfRentForTest.add(rent8);
        setOfRentForTest.add(rent9);
        setOfRentForTest.add(rent10);
        String input = "";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        List<Rent> listOfRent = rentDao.getRents();
        Set<Rent> setOfRent = new HashSet<>(listOfRent);
        Assert.assertEquals(setOfRentForTest, setOfRent);
    }

    @AfterTest
    public void close() throws SQLException {
        con.close();
    }

}

package com.carrental.service;

import com.carrental.dao.CarDao;
import com.carrental.dao.RentDao;
import com.carrental.dao.UserDao;
import com.carrental.dao.impl.CarDaoJDBC;
import com.carrental.dao.impl.RentDaoJDBC;
import com.carrental.dao.impl.UserDaoJDBC;
import com.carrental.model.Rent;
import com.carrental.model.User;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.Scanner;

import static com.carrental.service.Renter.reservation;

public class TestRenter {
    Connection con;
    CarDao carDao;
    UserDao userDao;
    RentDao rentDao;

    @BeforeTest
    public void init() throws ClassNotFoundException, SQLException {
        Configuration conf = new Configuration();
        conf.setProperties();
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost/car_rental", conf.getUsername(), conf.getPassword());
        carDao = new CarDaoJDBC(con);
        userDao = new UserDaoJDBC(con);
        rentDao = new RentDaoJDBC(con);
    }

    @Test
    public void testRentFromDate() {
        String input = "2021-01-01 13\n2019-01-01 13\n2022-01-01 13";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        Timestamp correctAnswer = Timestamp.valueOf("2022-01-01 13:00:00");
        Timestamp result = Renter.rentFromDate(scan);
        Assert.assertEquals(correctAnswer, result);
    }

    @Test
    public void testRentToDate() {
        Timestamp fromDate = Timestamp.valueOf("2021-03-01 12:00:00");
        String input = "2021-01-01 13\n2019-01-01 13\n2021-03-04 13\n2021-02-01 15";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        Timestamp correctAnswer = Timestamp.valueOf("2021-03-04 13:00:00");
        Timestamp result = Renter.rentToDate(scan, fromDate);
        Assert.assertEquals(correctAnswer, result);
    }

    @Test
    public void testGetPassportDetails() {
        String input = "Alexey\nEgorov\nValerievich\n1983-04-06\n4013453897\n2003-08-05";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        User newUser = Renter.getPassportDetails(scan);

        //User user = new User("Alexey", "Egorov", "Valerievich", Date.valueOf("1983-04-06"), 4013453897L, Date.valueOf("2003-08-05"));
        //Assert.assertEquals(user,newUser);

        Assert.assertEquals("Alexey", newUser.getFirstName());
        Assert.assertEquals("Egorov", newUser.getLastName());
        Assert.assertEquals("Valerievich", newUser.getMiddleName());
        Assert.assertEquals(Date.valueOf("1983-04-06"), newUser.getDateOfBirth());
        Assert.assertEquals(4013453897L, newUser.getPassportNumber());
        Assert.assertEquals(Date.valueOf("2003-08-05"), newUser.getDateOfIssue());
    }

    @Test(groups = {"Reservation_for_new_user"})
    public void testReservationNewUser() throws SQLException {
        User user = new User("Artem", "Vasiliev","Olegovich", Date.valueOf("1993-01-12"), 4019678145L,Date.valueOf("2018-05-06"));
        int carId = 10;
        Timestamp fromDate = Timestamp.valueOf("2021-05-10 12:00:00");
        Timestamp toDate = Timestamp.valueOf("2021-05-23 12:00:00");
        Rent rent = reservation(user,userDao,rentDao,carId,fromDate,toDate);
        int findId = userDao.checkUser(user);
        Rent rentForTest = new Rent(10,findId,fromDate,toDate);
        Assert.assertEquals(rentForTest,rent);
    }

    @AfterGroups("Reservation_for_new_user")
    public void deleteUserAndRentAfterReservation() throws SQLException {
        User user = new User("Artem", "Vasiliev","Olegovich", Date.valueOf("1993-01-12"), 4019678145L,Date.valueOf("2018-05-06"));
        int findId = userDao.checkUser(user);
        String sql1 = "DELETE FROM rent WHERE car_id = 10 AND user_id = ? AND from_date ='2021-05-10 12:00:00' AND to_date = '2021-05-23 12:00:00'";
        String sql2 = "DELETE FROM users WHERE first_name = 'Artem' AND last_name = 'Vasiliev' AND middle_name = 'Olegovich' AND date_of_birth = '1993-01-12' AND passport_number = 4019678145 AND date_of_issue = '2018-05-06'";
        PreparedStatement ps1 = con.prepareStatement(sql1);
        ps1.setInt(1,findId);
        PreparedStatement ps2 = con.prepareStatement(sql2);
        ps1.executeUpdate();
        ps2.executeUpdate();
        ps1.close();
        ps2.close();
    }

    @Test(groups = {"Reservation_for_an_existing_user"})
    public void testReservationExistingUser() throws SQLException {
        User user = new User("Natalia", "Averchenko","Aleksandrovna", Date.valueOf("1994-08-20"),4017951601L,Date.valueOf("2013-03-24"));
        int carId = 10;
        Timestamp fromDate = Timestamp.valueOf("2021-05-10 12:00:00");
        Timestamp toDate = Timestamp.valueOf("2021-05-23 12:00:00");
        Rent rent = reservation(user,userDao,rentDao,carId,fromDate,toDate);
        Rent rentForTest = new Rent(10,1,fromDate,toDate);
        Assert.assertEquals(rentForTest,rent);
    }

    @AfterGroups("Reservation_for_an_existing_user")
    public void deleteRentAfterReservation() throws SQLException {
        String sql1 = "DELETE FROM rent WHERE car_id = 10 AND user_id = 1 AND from_date ='2021-05-10 12:00:00' AND to_date = '2021-05-23 12:00:00'";
        PreparedStatement ps1 = con.prepareStatement(sql1);
        ps1.executeUpdate();
        ps1.close();
    }

    @AfterTest
    public void close() throws SQLException {
        con.close();
    }

}

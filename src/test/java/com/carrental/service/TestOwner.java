package com.carrental.service;

import com.carrental.dao.CarDao;
import com.carrental.dao.UserDao;
import com.carrental.dao.impl.CarDaoJDBC;
import com.carrental.dao.impl.UserDaoJDBC;
import com.carrental.model.Car;
import com.carrental.model.User;
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

import static com.carrental.service.Owner.*;

public class TestOwner {

    Connection con;
    CarDao carDao;
    UserDao userDao;

    @BeforeTest
    public void init() throws ClassNotFoundException, SQLException {
        Configuration conf = new Configuration();
        conf.setProperties();
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost/car_rental", conf.getUsername(), conf.getPassword());
        carDao = new CarDaoJDBC(con);
        userDao = new UserDaoJDBC(con);
    }

    @Test
    public void testGetCarInfo() {
        String input = "Renault\nSandero\n18\nmanual\nfalse";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        Car car = getCarInfo(scan);
        Assert.assertEquals("Renault", car.getMakeOfCar());
        Assert.assertEquals("Sandero", car.getModel());
        Assert.assertEquals(18, car.getPricePerDay());
        Assert.assertEquals("manual", car.getTransmission());
        Assert.assertFalse(car.getNavigator());
    }

    @Test
    public void testWorkWithCarTableCase1() throws SQLException {
        Car car1 = new Car("Ford", "Focus", 22, "manual", false);
        car1.setCarId(1);
        Car car2 = new Car("Hyundai", "Solaris", 24, "manual", false);
        car2.setCarId(2);
        Car car3 = new Car("Kia", "Sportage", 35, "automatic", true);
        car3.setCarId(3);
        Car car4 = new Car("Chevrolet", "Lacetti", 28, "manual", false);
        car4.setCarId(4);
        Car car5 = new Car("Hyundai", "Creta", 39, "automatic", true);
        car5.setCarId(5);
        Car car6 = new Car("Audi", "A4", 67, "automatic", true);
        car6.setCarId(6);
        Car car7 = new Car("Volkswagen", "Caravelle", 110, "automatic", true);
        car7.setCarId(7);
        Car car8 = new Car("Citroen", "Spacetourer", 120, "automatic", true);
        car8.setCarId(8);
        Car car9 = new Car("Chevrolet", "Camaro", 124, "automatic", false);
        car9.setCarId(9);
        Car car10 = new Car("Porsche", "Boxter", 173, "automatic", true);
        car10.setCarId(10);
        Car car11 = new Car("Ford", "Mustang", 150, "automatic", true);
        car11.setCarId(11);
        Set<Car> setOfCarForTest = new HashSet<>();
        setOfCarForTest.add(car1);
        setOfCarForTest.add(car2);
        setOfCarForTest.add(car3);
        setOfCarForTest.add(car4);
        setOfCarForTest.add(car5);
        setOfCarForTest.add(car6);
        setOfCarForTest.add(car7);
        setOfCarForTest.add(car8);
        setOfCarForTest.add(car9);
        setOfCarForTest.add(car10);
        setOfCarForTest.add(car11);
        String input = "";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        List<Car> listOfCar = workWithCarTable(scan, carDao, 1);
        Set<Car> setOfCar = new HashSet<>(listOfCar);
        Assert.assertEquals(setOfCarForTest, setOfCar);
    }

    @Test
    public void testWorkWithCarTableCase2() throws SQLException {
        String input = "Ford\n";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        List<Car> listFromMethod = workWithCarTable(scan, carDao, 2);
        Set<Car> setFromMethod = new HashSet<>(listFromMethod);
        Set<Car> setOfCarForTest = new HashSet<>();
        Car car1 = new Car("Ford", "Focus", 22, "manual", false);
        car1.setCarId(1);
        Car car2 = new Car("Ford", "Mustang", 150, "automatic", true);
        car2.setCarId(11);
        setOfCarForTest.add(car1);
        setOfCarForTest.add(car2);
        Assert.assertEquals(setOfCarForTest,setFromMethod);
    }

    @Test(groups = {"Add_new_car"})
    public void testWorkWithCarTableCase3() throws SQLException {
        Car newCar = new Car("Audi", "TT", 78, "automatic", true);
        Statement st = con.createStatement();
        String query = "SELECT COUNT(*) FROM CARS";
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int countBeforeAdd = rs.getInt(1);
        carDao.create(newCar);
        rs = st.executeQuery(query);
        rs.next();
        int countAfterAdd = rs.getInt(1);
        Assert.assertEquals(countBeforeAdd + 1, countAfterAdd);
        rs.close();
        st.close();
    }

    @AfterGroups("Add_new_car")
    public void deleteCarFromTableAfterAdd() throws SQLException {
        String sql = "DELETE FROM cars WHERE make_of_car = 'Audi' AND model = 'TT' AND price_per_day = 78 AND transmission = 'automatic' AND navigator = true";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Test(groups = {"Change_car"})
    public void testWorkWithCarTableCase4() throws SQLException {
        String input = "1\nFord\nFocus\n35\nmanual\nfalse";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        workWithCarTable(scan, carDao, 4);
        String sql = "SELECT * FROM cars WHERE car_id = 1";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int pricePerDay = rs.getInt("price_per_day");
        Assert.assertEquals(35, pricePerDay);
        rs.close();
        ps.close();
    }

    @AfterGroups("Change_car")
    public void changeCarAfterUpdate() throws SQLException {
        String sql = "UPDATE cars SET price_per_day = 22 WHERE car_id = 1";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testWorkWithUserTableCase1() throws SQLException {
        User user1 = new User("Natalia", "Averchenko", "Aleksandrovna", Date.valueOf("1994-08-20"), 4017951601L, Date.valueOf("2013-03-24"));
        user1.setUserId(1);
        User user2 = new User("Igor", "Ivanov", "Yanovich", Date.valueOf("1994-06-09"), 4008736295L, Date.valueOf("2012-06-26"));
        user2.setUserId(2);
        User user3 = new User("Ekaterina", "Semushina", "Yurievna", Date.valueOf("1994-04-30"), 4016326936L, Date.valueOf("2016-05-03"));
        user3.setUserId(3);
        User user4 = new User("Maksim", "Anshukov", "Olegovich", Date.valueOf("1993-09-06"), 4011892783L, Date.valueOf("2012-10-15"));
        user4.setUserId(4);
        User user5 = new User("Konstantin", "Porshnev", "Vladimirovich", Date.valueOf("1981-03-25"), 4017326735L, Date.valueOf("2000-01-23"));
        user5.setUserId(5);
        User user6 = new User("Ilia", "Panteleev", "Sergeevich", Date.valueOf("1983-05-17"), 4009894782L, Date.valueOf("2015-06-17"));
        user6.setUserId(6);
        User user7 = new User("Ekaterina", "Levandovskaya", "Olegovna", Date.valueOf("1986-09-30"), 4007278564L, Date.valueOf("2006-02-14"));
        user7.setUserId(7);
        User user8 = new User("Victor", "Minin", "Konstantinovich", Date.valueOf("1993-02-06"), 4012923758L, Date.valueOf("2020-03-06"));
        user8.setUserId(8);
        User user9 = new User("Julia", "Eskova", "Igorevna", Date.valueOf("1994-12-03"), 4014983501L, Date.valueOf("2014-10-15"));
        user9.setUserId(9);
        User user10 = new User("Julia", "Revyakina", "Valentinovna", Date.valueOf("1996-10-13"), 4013895672L, Date.valueOf("2019-12-02"));
        user10.setUserId(10);
        Set<User> setOfUserForTest = new HashSet<>();
        setOfUserForTest.add(user1);
        setOfUserForTest.add(user2);
        setOfUserForTest.add(user3);
        setOfUserForTest.add(user4);
        setOfUserForTest.add(user5);
        setOfUserForTest.add(user6);
        setOfUserForTest.add(user7);
        setOfUserForTest.add(user8);
        setOfUserForTest.add(user9);
        setOfUserForTest.add(user10);
        String input = "";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        List<User> listOfUser = workWithUserTable(scan, userDao, 1);
        Set<User> setOfUser = new HashSet<>(listOfUser);
        Assert.assertEquals(setOfUserForTest, setOfUser);
    }

    @Test(groups = {"Change_user"})
    public void testWorkWithUserTableCase2() throws SQLException {
        String input = "1\nIvan\nIvanov\nIvanovich\n1983-04-06\n4013453897\n2003-08-05";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        workWithUserTable(scan, userDao, 2);
        String sql = "SELECT * FROM users WHERE user_id = 1";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String firstname = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String middleName = rs.getString("middle_name");
        Date dateOfBirth = rs.getDate("date_of_birth");
        long passportNumber = rs.getLong("passport_number");
        Date dateOfIssue = rs.getDate("date_of_issue");
        Assert.assertEquals("Ivan", firstname);
        Assert.assertEquals("Ivanov", lastName);
        Assert.assertEquals("Ivanovich", middleName);
        Assert.assertEquals(Date.valueOf("1983-04-06"), dateOfBirth);
        Assert.assertEquals(4013453897L, passportNumber);
        Assert.assertEquals(Date.valueOf("2003-08-05"), dateOfIssue);
        rs.close();
        ps.close();
    }

    @AfterGroups("Change_user")
    public void changeUserAfterUpdate() throws SQLException {
        String sql = "UPDATE users SET first_name = 'Natalia', last_name = 'Averchenko', middle_name = 'Aleksandrovna', date_of_birth = '1994-08-20', passport_number = 4017951601, date_of_issue = '2013-03-24' WHERE user_id = 1";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testStatistics() throws SQLException {
        Car theMostProfitableCar = statistics(con, 1);
        Car theMostProfitableCarTest = new Car("Chevrolet", "Lacetti", 28, "manual", false);
        theMostProfitableCarTest.setCarId(4);
        Assert.assertEquals(theMostProfitableCarTest, theMostProfitableCar);
    }

    @AfterTest
    public void close() throws SQLException {
        con.close();
    }

}

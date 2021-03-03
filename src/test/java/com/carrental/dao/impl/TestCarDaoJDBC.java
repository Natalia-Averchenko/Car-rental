package com.carrental.dao.impl;

import com.carrental.dao.CarDao;
import com.carrental.model.Car;
import com.carrental.service.Configuration;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.*;

public class TestCarDaoJDBC {

    Connection con;
    CarDao carDao;

    @BeforeTest
    public void init() throws ClassNotFoundException, SQLException {
        Configuration conf = new Configuration();
        conf.setProperties();
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost/car_rental", conf.getUsername(), conf.getPassword());
        carDao = new CarDaoJDBC(con);
    }

    @Test(groups = {"Add_new_car_DAO"})
     public void testCreateCar() throws SQLException {
        String sql = "select exists(select 1 from cars where make_of_car='Audi' AND model='TT' AND price_per_day=78 AND transmission='automatic' AND navigator=true)";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        rs1.next();
        boolean existBeforeAdd = rs1.getBoolean(1);
        Car newCar = new Car("Audi", "TT", 78, "automatic", true);
        carDao.create(newCar);
        ResultSet rs2 = st.executeQuery(sql);
        rs2.next();
        boolean existAfterAdd = rs2.getBoolean(1);
        rs1.close();
        rs2.close();
        st.close();
        Assert.assertFalse(existBeforeAdd);
        Assert.assertTrue(existAfterAdd);
    }

    @AfterGroups("Add_new_car_DAO")
    public void deleteCarAfterAdd() throws SQLException {
        String sql = "DELETE FROM cars WHERE make_of_car = 'Audi' AND model = 'TT' AND price_per_day = 78 AND transmission = 'automatic' AND navigator = true";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testReadCar() throws SQLException {
        Car carRead = carDao.read(1);
        Car carTest = new Car("Ford", "Focus", 22, "manual", false);
        carTest.setCarId(1);
        Assert.assertEquals(carTest,carRead);
    }

    @Test(groups = {"Update_car_DAO"})
    public void testUpdateCar() throws SQLException {
        String sql = "select exists(select 1 from cars where make_of_car='Ford' AND model='Focus' AND price_per_day=35 AND transmission='automatic' AND navigator=true)";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        rs1.next();
        boolean existBeforeUpdate = rs1.getBoolean(1);
        Car updCar = new Car("Ford", "Focus", 35, "automatic", true);
        carDao.update(updCar,1);
        ResultSet rs2 = st.executeQuery(sql);
        rs2.next();
        boolean existAfterUpdate = rs2.getBoolean(1);
        rs1.close();
        rs2.close();
        st.close();
        Assert.assertFalse(existBeforeUpdate);
        Assert.assertTrue(existAfterUpdate);
    }

    @AfterGroups("Update_car_DAO")
    public void updateCarAfterUpdate() throws SQLException {
        String sql = "UPDATE cars SET price_per_day = 22, transmission='manual', navigator=false WHERE car_id = 1";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Test(groups={"Delete_car_DAO"})
    public void testDeleteCar() throws SQLException {
        String sql = "select exists(select 1 from cars where make_of_car='Ford' AND model='Mustang' AND price_per_day=150 AND transmission='automatic' AND navigator=true)";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        rs1.next();
        boolean existBeforeDelete = rs1.getBoolean(1);
        carDao.delete(11);
        ResultSet rs2 = st.executeQuery(sql);
        rs2.next();
        boolean existAfterDelete = rs2.getBoolean(1);
        rs1.close();
        rs2.close();
        st.close();
        Assert.assertTrue(existBeforeDelete);
        Assert.assertFalse(existAfterDelete);
    }

    @AfterGroups("Delete_car_DAO")
    public void insertCarAfterDelete() throws SQLException {
        String sql = "INSERT INTO cars VALUES (11, 'Ford', 'Mustang', 150, 'automatic', true)";
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        st.close();
    }

    @Test
    public void testGetTheMostProfitableCar() throws SQLException {
        int testCarId = 4;
        int carId = carDao.getTheMostProfitableCar();
        Assert.assertEquals(testCarId,carId);
    }

    @AfterTest
    public void close() throws SQLException {
        con.close();
    }
}

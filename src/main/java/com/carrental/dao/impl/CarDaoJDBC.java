package com.carrental.dao.impl;

import com.carrental.dao.CarDao;
import com.carrental.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDaoJDBC implements CarDao {

    private final Connection con;

    public CarDaoJDBC(Connection con) {
        this.con = con;
    }

    public Car create(Car newCar) throws SQLException {
        String sql = "INSERT INTO cars (make_of_car,model,price_per_day,transmission,navigator) VALUES (?,?,?,?,?) returning car_id";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, newCar.getMakeOfCar());
        ps.setString(2, newCar.getModel());
        ps.setInt(3, newCar.getPricePerDay());
        ps.setString(4, newCar.getTransmission());
        ps.setBoolean(5, newCar.getNavigator());
        ResultSet rs = ps.executeQuery();
        rs.next();
        newCar.setCarId(rs.getInt("car_id"));
        rs.close();
        ps.close();
        return newCar;
    }

    public Car read(Integer id) throws SQLException {
        String sql = "SELECT * FROM cars WHERE car_Id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Car car = new Car(rs.getString("make_of_car"), rs.getString("model"), rs.getInt("price_per_day"), rs.getString("transmission"), rs.getBoolean("navigator"));
        car.setCarId(rs.getInt("car_id"));
        rs.close();
        ps.close();
        return car;
    }

    public void update(Car car, Integer id) throws SQLException {
        String sql = "UPDATE cars SET make_of_car=?, model=?, price_per_day=?, transmission=?, navigator=? WHERE car_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, car.getMakeOfCar());
        ps.setString(2, car.getModel());
        ps.setInt(3, car.getPricePerDay());
        ps.setString(4, car.getTransmission());
        ps.setBoolean(5, car.getNavigator());
        ps.setInt(6, id);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM cars WHERE car_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public List<Car> getCars() throws SQLException {
        String sql = "SELECT * FROM cars";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Car> allCars = new ArrayList<>();
        while (rs.next()) {
            Car car = new Car(rs.getString("make_of_car"), rs.getString("model"), rs.getInt("price_per_day"), rs.getString("transmission"), rs.getBoolean("navigator"));
            car.setCarId(rs.getInt("car_id"));
            allCars.add(car);
        }
        rs.close();
        ps.close();
        return allCars;

    }

    public List<Car> getCarsByMakeOfCar(String makeOfCar) throws SQLException {
        String sql = "SELECT * FROM cars WHERE make_of_car = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, makeOfCar);
        ResultSet rs = ps.executeQuery();
        List<Car> allCars = new ArrayList<>();
        while (rs.next()) {
            Car car = new Car(rs.getString("make_of_car"), rs.getString("model"), rs.getInt("price_per_day"), rs.getString("transmission"), rs.getBoolean("navigator"));
            car.setCarId(rs.getInt("car_id"));
            allCars.add(car);
        }
        rs.close();
        ps.close();
        return allCars;
    }

}

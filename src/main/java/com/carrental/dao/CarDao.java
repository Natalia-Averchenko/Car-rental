package com.carrental.dao;

import com.carrental.model.Car;

import java.sql.SQLException;
import java.util.List;

public interface CarDao extends Crud<Car> {

    List<Car> getCars() throws SQLException;
    List<Car> getCarsByMakeOfCar(String makeOfCar) throws SQLException;
    int getTheMostProfitableCar() throws SQLException;

}

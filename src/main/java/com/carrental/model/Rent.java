package com.carrental.model;

import java.sql.Timestamp;

public class Rent {
    //private int rentId;
    private int carId;
    private int userId;
    private Timestamp fromDate;
    private Timestamp toDate;

    public Rent(int carId, int userId, Timestamp fromDate, Timestamp toDate) {
        this.carId = carId;
        this.userId = userId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }
    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public int getCarId() {
        return this.carId;
    }

    public int getUserId() {
        return this.userId;
    }

    public Timestamp getFromDate() {
        return this.fromDate;
    }

    public Timestamp getToDate() {
        return this.toDate;
    }

    @Override
    public String toString(){
        return "Car ID: " + this.getCarId() + "\t\tUser ID: " + this.getUserId() +"\t\tFrom Date: " +this.getFromDate() +"\t\tTo Date: " + this.getToDate();
    }
}

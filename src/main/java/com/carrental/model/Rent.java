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
    public String toString() {
        return "Car ID: " + this.getCarId() + "\t\tUser ID: " + this.getUserId() + "\t\tFrom Date: " + this.getFromDate() + "\t\tTo Date: " + this.getToDate();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Rent rent = (Rent) obj;
        return (carId == rent.carId && userId == rent.userId && fromDate != null && fromDate.equals(rent.fromDate) && toDate != null && toDate.equals(rent.toDate));
    }

    @Override
    public int hashCode() {
        int result = carId + userId;
        result += (fromDate == null) ? 0 : fromDate.hashCode();
        result += (toDate == null) ? 0 : toDate.hashCode();
        return (result % 100);
    }

}

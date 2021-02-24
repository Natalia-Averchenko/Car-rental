package com.carrental.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Scanner;

public class TestRenter {
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
        String input = "Alexey\nEgorov\nValerievich\n1983-04-6\n4013453897\n2003-08-05";
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


}

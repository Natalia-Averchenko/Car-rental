package com.carrental.service;

import com.carrental.model.Car;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Scanner;

import static com.carrental.service.Owner.getCarInfo;

public class TestOwner {

    @Test
    public void testGetCarInfo() {
        String input = "Renault\nSandero\n18\nmanual\nfalse";
        Scanner scan = new Scanner(input).useDelimiter("\n");
        Car car = getCarInfo(scan);
        Assert.assertEquals("Renault", car.getMakeOfCar());
        Assert.assertEquals("Sandero", car.getModel());
        Assert.assertEquals(18, car.getPricePerDay());
        Assert.assertEquals("manual", car.getTransmission());
        Assert.assertEquals(false, car.getNavigator());
    }
}

package com.carrental.model;

import com.carrental.dao.impl.CarDaoJDBC;
import com.carrental.dao.impl.RentDaoJDBC;
import com.carrental.dao.impl.UserDaoJDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.Set;

public class Renter {

    public static void rentProcess(Connection con, Scanner scan) throws SQLException {
        Timestamp fromDate;
        Timestamp toDate;
        Timestamp now;
        String minsec = ":00:00";

        do {
            System.out.println("Please enter car pick up date and time (format YYYY-MM-DD HH)");
            fromDate = Timestamp.valueOf(scan.nextLine() + minsec);
            now = new Timestamp(System.currentTimeMillis());

            if (fromDate.before(now))
                System.out.println("Entered date is in the past. Please choose another date");
        }
        while (fromDate.before(now));

        do {
            System.out.println("Please enter date and time of returning the car (format YYYY-MM-DD HH)");
            toDate = Timestamp.valueOf(scan.nextLine() + minsec);
            if (toDate.before(fromDate))
                System.out.println("Input data incorrect (before pick up date). Please choose another date");
        }
        while (toDate.before(fromDate));


        RentDaoJDBC rentDao = new RentDaoJDBC(con);
        Set<Integer> carsIDForRent = rentDao.getCarsIDForRent(fromDate, toDate);
        if (carsIDForRent.size() < 1)
            System.out.println("Unfortunately we don't have cars for the selected dates");
        else {
            CarDaoJDBC cardao = new CarDaoJDBC(con);
            System.out.println("We can offer you following cars:");
            for (Integer c : carsIDForRent)
                System.out.println(cardao.read(c));
            System.out.println("Would you like to rent one of this car? Y/N");
            String rentAnswer = String.valueOf(scan.nextLine());
            if (rentAnswer.equalsIgnoreCase("Y")) {
                System.out.println("Please enter ID of the desired car");
                int carId = Integer.parseInt(scan.nextLine());

                if (!carsIDForRent.contains(carId))
                    System.out.println("Provided ID incorrect - we can't offer you this car");
                else {
                    System.out.print("To complete the booking we need your passport details \nPlease enter your first name: ");
                    String firstName = String.valueOf(scan.nextLine());
                    System.out.print("Please enter your last name: ");
                    String lastName = String.valueOf(scan.nextLine());
                    System.out.print("Please enter your middle name: ");
                    String middleName = String.valueOf(scan.nextLine());
                    System.out.print("Please enter your date of birth (format YYYY-MM-DD): ");
                    Date dateOfBirth = Date.valueOf(scan.nextLine());
                    System.out.print("Please enter your passport number (format SSSSNNNNNN): ");
                    long passportNumber = Long.parseLong(scan.nextLine());
                    System.out.print("Please enter your passport date of issue (format YYYY-MM-DD): ");
                    Date dateOfIssue = Date.valueOf(scan.nextLine());
                    User user = new User(firstName, lastName, middleName, dateOfBirth, passportNumber, dateOfIssue);
                    UserDaoJDBC userDao = new UserDaoJDBC(con);
                    int userId = userDao.checkUser(user);
                    if (userId == -1) {
                        User newUser = userDao.create(user);
                        userId = newUser.getUserId();
                    }
                    Rent rent = new Rent(carId, userId, fromDate, toDate);
                    rentDao.create(rent);
                    System.out.println("\nYour rent is successfully finished. Thank you!");
                }
            } else System.out.println("Thank you for visiting!");
        }


    }

}

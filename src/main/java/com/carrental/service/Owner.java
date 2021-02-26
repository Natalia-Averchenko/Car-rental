package com.carrental.service;

import com.carrental.dao.CarDao;
import com.carrental.dao.UserDao;
import com.carrental.dao.impl.CarDaoJDBC;
import com.carrental.dao.impl.UserDaoJDBC;
import com.carrental.model.Car;
import com.carrental.model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Owner {

    static Car getCarInfo(Scanner scan) {
        System.out.print("Please enter make of car: ");
        String makeOfCar = String.valueOf(scan.nextLine());
        System.out.print("Please enter model of car: ");
        String modelOfCar = String.valueOf(scan.nextLine());
        System.out.print("Please enter price per day: ");
        int pricePerDay = Integer.parseInt(scan.nextLine());
        System.out.print("Please enter transmission (manual/automatic): ");
        String transmission = String.valueOf(scan.nextLine());
        System.out.print("Please enter availability of the navigator (true/false): ");
        boolean navigator = Boolean.parseBoolean(scan.nextLine());
        return new Car(makeOfCar, modelOfCar, pricePerDay, transmission, navigator);
    }

    static void workWithCarTable(Scanner scan, CarDao carDao, int variants) throws SQLException {
        switch (variants) {
            case 1:
                List<Car> allCars = carDao.getCars();
                for (Car c : allCars)
                    System.out.println(c);
                break;
            case 2:
                System.out.print("Please enter make of car: ");
                String searchByMakeOfCar = String.valueOf(scan.nextLine());
                List<Car> carsByMakeOfCar = carDao.getCarsByMakeOfCar(searchByMakeOfCar);
                if (carsByMakeOfCar.size() < 1)
                    System.out.println("We don't have such cars");
                else {
                    for (Car c : carsByMakeOfCar)
                        System.out.println(c);
                }
                break;
            case 3:
                Car newCar = getCarInfo(scan);
                newCar = carDao.create(newCar);
                System.out.println("\nCar successfully added");
                System.out.println(newCar);
                break;
            case 4:
                System.out.println("Please enter car ID for update");
                int carIdForUpdate = Integer.parseInt(scan.nextLine());
                Car updCar = getCarInfo(scan);
                carDao.update(updCar, carIdForUpdate);
                System.out.println("Car successfully updated");
                break;
            default:
                System.out.println("Incorrect input");
                break;
        }
    }

    static void workWithUserTable(Scanner scan, UserDao userDao, int userActivity) throws SQLException {
        switch (userActivity) {
            case 1:
                List<User> allUser = userDao.getUsers();
                for (User u : allUser)
                    System.out.println(u);
                break;
            case 2:
                System.out.println("Please enter user ID for update");
                int userIdForUpdate = Integer.parseInt(scan.nextLine());
                System.out.print("Enter first name: ");
                String firstNameUpd = String.valueOf(scan.nextLine());
                System.out.print("Enter last name: ");
                String lastNameUpd = String.valueOf(scan.nextLine());
                System.out.print("Enter middle name: ");
                String middleNameUpd = String.valueOf(scan.nextLine());
                System.out.print("Enter date of birth (format YYYY-MM-DD): ");
                Date dateOfBirthUpd = Date.valueOf(scan.nextLine());
                System.out.print("Enter passport number (format SSSSNNNNNN): ");
                long passportNumberUpd = Long.parseLong(scan.nextLine());
                System.out.print("Enter passport date of issue (format YYYY-MM-DD): ");
                Date dateOfIssueUpd = Date.valueOf(scan.nextLine());
                User updUser = new User(firstNameUpd, lastNameUpd, middleNameUpd, dateOfBirthUpd, passportNumberUpd, dateOfIssueUpd);
                userDao.update(updUser, userIdForUpdate);
                System.out.println("User successfully updated");
                break;
            default:
                System.out.println("Incorrect input");
                break;
        }
    }

    static void statistics(Connection con, int statisticOpt) throws SQLException {
        if (statisticOpt == 1) {
            CarDao carDao = new CarDaoJDBC(con);
            System.out.println("The most profitable car is: ");
            System.out.println(carDao.read(carDao.getTheMostProfitableCar()));
        } else {
            System.out.println("Incorrect input");
        }
    }

    public static void ownerProcess(Connection con, Scanner scan) throws SQLException {
        int ans;
        do {
            System.out.println("\nWould you like to work with\n1.Car table\n2.User table\n3.Statistics\n4.Exit");
            ans = Integer.parseInt(scan.nextLine());
            switch (ans) {
                case 1:
                    System.out.println("Would you like to\n1.Show all cars\n2.Show cars by make of car\n3.Add new car\n4.Update info about car");
                    CarDao carDao = new CarDaoJDBC(con);
                    int carActivity = Integer.parseInt(scan.nextLine());
                    workWithCarTable(scan, carDao, carActivity);
                    break;
                case 2:
                    System.out.println("Would you like to\n1.Show all users\n2.Update user");
                    UserDao userDao = new UserDaoJDBC(con);
                    int userActivity = Integer.parseInt(scan.nextLine());
                    workWithUserTable(scan, userDao, userActivity);
                    break;
                case 3:
                    System.out.println("Would you like to\n1.Find the most profitable car");
                    int statisticOpt = Integer.parseInt(scan.nextLine());
                    statistics(con, statisticOpt);
                    break;
                case 4:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Incorrect input");
                    break;
            }
        }
        while (ans != 4);
    }

}

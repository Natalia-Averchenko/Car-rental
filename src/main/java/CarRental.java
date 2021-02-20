import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class CarRental {

    public static void main(String[] args) {

        Configuration conf = new Configuration();
        conf.setProperties();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException при загрузке класса драйвера БД");
        }

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost/car_rental", conf.getUsername(), conf.getPassword());
             Scanner scan = new Scanner(System.in)) {
            System.out.println("You are welcome in our car rental");
            System.out.println("Please choose access \n1. Renter \n2. Owner");
            int ans = Integer.parseInt(scan.nextLine());
            switch (ans) {
                case 1:
                    System.out.println("Please enter car pick up date and time (format YYYY-MM-DD HH)");
                    String minsec = ":00:00";
                    Timestamp fromDate = Timestamp.valueOf(scan.nextLine() + minsec);
                    Timestamp now = new Timestamp(System.currentTimeMillis());

                    if (fromDate.before(now))
                        System.out.println("Entered date is in the past.");
                    else {
                        System.out.println("Please enter date and time of returning the car (format YYYY-MM-DD HH)");
                        Timestamp toDate = Timestamp.valueOf(scan.nextLine() + minsec);

                        if (toDate.before(fromDate))
                            System.out.println("Input data incorrect (before pick up date).");
                        else {
                            RentDaoJDBC rentDao = new RentDaoJDBC(con);
                            List<Integer> carsIDForRent = rentDao.getCarsIDForRent(fromDate, toDate);
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
                                } else System.out.println("Thank you for visiting!");
                            }
                        }
                    }
                    break;

                case 2:
                    System.out.println("Sorry, this part of the service is under development.");
                    break;

                default:
                    System.out.println("Incorrect input");
                    break;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException");
        }

    }
}

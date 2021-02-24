import com.carrental.model.Owner;
import com.carrental.model.Renter;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class CarRental {

    private static final Logger logger = Logger.getLogger(CarRental.class.getName());

    public static void main(String[] args) {

        Configuration conf = new Configuration();
        conf.setProperties(logger);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException, driver for database not found", e);
            throw new RuntimeException(e);
        }

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost/car_rental", conf.getUsername(), conf.getPassword());
             Scanner scan = new Scanner(System.in)) {
            System.out.println("You are welcome in our car rental");
            System.out.println("Please choose access \n1. Renter \n2. Owner");
            int ans = Integer.parseInt(scan.nextLine());
            switch (ans) {
                case 1:
                    Renter.rentProcess(con, scan);
                    break;
                case 2:
                    Owner.ownerProcess(con, scan);
                    break;
                default:
                    System.out.println("Incorrect input");
                    break;
            }

        } /*catch (SQLException e) {
            logger.error("SQLException",e);
        } */ catch (Exception e) {
            logger.error("Exception", e);
        }

    }
}

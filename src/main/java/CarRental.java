import com.carrental.model.Owner;
import com.carrental.model.Renter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
                    Renter.rentProcess(con,scan);
                    break;

                case 2:
                    Owner.ownerProcess(con,scan);
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

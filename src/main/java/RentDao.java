import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface RentDao extends Crud <Rent,Integer> {
    List<Rent> getRents() throws SQLException;
    List<Integer> getCarsIDForRent(Timestamp fromDate, Timestamp toDate) throws SQLException;
}

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends Crud <User,Integer> {
    List<User> getUsers() throws SQLException;
    int checkUser(User user) throws SQLException;
}

import java.sql.SQLException;

public interface Crud<T,K> {
    T create(T obj) throws SQLException;
    T read(K id) throws SQLException;
    void update(T obj,K id) throws SQLException;
    void delete(K id) throws SQLException;
}

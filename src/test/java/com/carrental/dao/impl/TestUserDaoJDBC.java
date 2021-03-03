package com.carrental.dao.impl;

import com.carrental.dao.UserDao;
import com.carrental.model.User;
import com.carrental.service.Configuration;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.*;

public class TestUserDaoJDBC {

    Connection con;
    UserDao userDao;

    @BeforeTest
    public void init() throws ClassNotFoundException, SQLException {
        Configuration conf = new Configuration();
        conf.setProperties();
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost/car_rental", conf.getUsername(), conf.getPassword());
        userDao = new UserDaoJDBC(con);
    }

    @Test(groups = {"Add_new_user_DAO"})
    public void testCreateUser() throws SQLException {
        String sql = "select exists(select 1 from users where first_name = 'Artem' AND last_name = 'Vasiliev' AND middle_name = 'Olegovich' AND date_of_birth = '1993-01-12' AND passport_number = 4019678145 AND date_of_issue = '2018-05-06')";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        rs1.next();
        boolean existBeforeAdd = rs1.getBoolean(1);
        User newUser = new User("Artem", "Vasiliev","Olegovich", Date.valueOf("1993-01-12"), 4019678145L,Date.valueOf("2018-05-06"));
        userDao.create(newUser);
        ResultSet rs2 = st.executeQuery(sql);
        rs2.next();
        boolean existAfterAdd = rs2.getBoolean(1);
        rs1.close();
        rs2.close();
        st.close();
        Assert.assertFalse(existBeforeAdd);
        Assert.assertTrue(existAfterAdd);
    }

    @AfterGroups("Add_new_user_DAO")
    public void deleteUserAfterAdd() throws SQLException {
        String sql = "DELETE FROM users WHERE first_name = 'Artem' AND last_name = 'Vasiliev' AND middle_name = 'Olegovich' AND date_of_birth = '1993-01-12' AND passport_number = 4019678145 AND date_of_issue = '2018-05-06'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Test
    public void testReadUser() throws SQLException {
        User userRead = userDao.read(1);
        User userTest = new User("Natalia", "Averchenko","Aleksandrovna", Date.valueOf("1994-08-20"),4017951601L,Date.valueOf("2013-03-24"));
        userTest.setUserId(1);
        Assert.assertEquals(userTest,userRead);
    }

    @Test(groups = {"Update_user_DAO"})
    public void testUpdateUser() throws SQLException {
        String sql = "select exists(select 1 from users where first_name = 'Artem' AND last_name = 'Vasiliev' AND middle_name = 'Olegovich' AND date_of_birth = '1993-01-12' AND passport_number = 4019678145 AND date_of_issue = '2018-05-06')";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        rs1.next();
        boolean existBeforeUpdate = rs1.getBoolean(1);
        User updUser = new User("Artem", "Vasiliev","Olegovich", Date.valueOf("1993-01-12"), 4019678145L,Date.valueOf("2018-05-06"));
        userDao.update(updUser,1);
        ResultSet rs2 = st.executeQuery(sql);
        rs2.next();
        boolean existAfterUpdate = rs2.getBoolean(1);
        rs1.close();
        rs2.close();
        st.close();
        Assert.assertFalse(existBeforeUpdate);
        Assert.assertTrue(existAfterUpdate);
    }

    @AfterGroups("Update_user_DAO")
    public void updateUserAfterUpdate() throws SQLException {
        String sql = "UPDATE users SET first_name = 'Natalia', last_name = 'Averchenko', middle_name = 'Aleksandrovna', date_of_birth = '1994-08-20', passport_number = 4017951601, date_of_issue = '2013-03-24' WHERE user_id = 1";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Test(groups={"Delete_user_DAO"})
    public void testDeleteUser() throws SQLException {
        String sql = "select exists(select 1 from users WHERE first_name = 'Maksim' AND last_name = 'Anshukov' AND middle_name = 'Olegovich' AND date_of_birth = '1993-09-06' AND passport_number = 4011892783 AND date_of_issue = '2012-10-15')";
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql);
        rs1.next();
        boolean existBeforeDelete = rs1.getBoolean(1);
        userDao.delete(4);
        ResultSet rs2 = st.executeQuery(sql);
        rs2.next();
        boolean existAfterDelete = rs2.getBoolean(1);
        rs1.close();
        rs2.close();
        st.close();
        Assert.assertTrue(existBeforeDelete);
        Assert.assertFalse(existAfterDelete);
    }

    @AfterGroups("Delete_user_DAO")
    public void insertUserAfterDelete() throws SQLException {
        String sql = "INSERT INTO users VALUES (4, 'Maksim', 'Anshukov', 'Olegovich', '1993-09-06', 4011892783, '2012-10-15')";
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        st.close();
    }

    @Test
    public void testCheckNotExistingUser() throws SQLException {
        User user = new User("Artem", "Vasiliev","Olegovich", Date.valueOf("1993-01-12"), 4019678145L,Date.valueOf("2018-05-06"));
        int id = userDao.checkUser(user);
        Assert.assertEquals(-1,id);
    }

    @Test
    public void testCheckExistingUser() throws SQLException {
        User user = new User("Maksim", "Anshukov","Olegovich", Date.valueOf("1993-09-06"), 4011892783L,Date.valueOf("2012-10-15"));
        int id = userDao.checkUser(user);
        Assert.assertEquals(4,id);
    }

    @AfterTest
    public void close() throws SQLException {
        con.close();
    }

}

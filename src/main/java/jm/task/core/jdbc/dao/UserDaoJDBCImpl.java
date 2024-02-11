package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private final Connection connection = Util.getConnection();

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users (
                      id INT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(45) NOT NULL,
                      lastName VARCHAR(45) NOT NULL,
                      age INT NOT NULL,
                      PRIMARY KEY (id))
                    ENGINE = InnoDB
                    DEFAULT CHARACTER SET = utf8;""");

            System.out.println("CREATE TABLE... OK!");

        } catch (SQLException e) {
            throw new RuntimeException("CREATE TABLE... FAIL!", e);

        }

    }

    public void dropUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("DROP TABLE... OK!");
        } catch (SQLException e) {
            throw new RuntimeException("DROP TABLE... FAIL!", e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        String regUser = "INSERT INTO users (name, lastName, age) values (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(regUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Registration user... OK!");

        } catch (SQLException e) {
            throw new RuntimeException("Registration user... FAIL!", e);
        }

    }


    public void removeUserById(long id) {
        String removeID = "DELETE FROM users where id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(removeID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("DELETE user... OK!");
        } catch (SQLException e) {
            throw new RuntimeException("DELETE user... FAIL!", e);
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getAll = "SELECT * FROM users";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getAll)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
                System.out.println("GETTING user... OK!");
            }


        } catch (SQLException e) {
            throw new RuntimeException("GETTING user... FAIL!", e);
        }
        return userList;
    }

    public void cleanUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
            System.out.println("CLEANING TABLE... OK");

        } catch (SQLException e) {
            throw new RuntimeException("CLEANING TABLE... FAIL!", e);
        }

    }
}

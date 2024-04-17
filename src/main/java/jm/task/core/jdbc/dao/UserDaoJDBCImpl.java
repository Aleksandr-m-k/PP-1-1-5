package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    // создание таблицы
    public void createUsersTable() {
        final String sqlCommandCreate = "CREATE TABLE `users` ( id int NOT NULL AUTO_INCREMENT," +
                " name varchar(45) NOT NULL, lastName varchar(45) DEFAULT NULL, age int NOT NULL, PRIMARY KEY (id))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommandCreate);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблица уже была создана");
        }
    }

    // удаление таблицы
    public void dropUsersTable() {
        final String sqlCommandDrop = "drop table Users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommandDrop);
            System.out.println("таблица удалена");
        } catch (SQLException e) {
            System.out.println("удалить не удалось");
        }
    }

    // добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        final String sqlCommandSave = "insert into users (name, lastName, age) values (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommandSave)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("введено неверное значение");
        }
    }

    // удаление User из таблицы по Id
    public void removeUserById(long id) {
        final String sqlCommandRemove = "DELETE from Users where ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommandRemove)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("удалить не удалось");
        }
    }

    // получение всех User из таблицы
    public List<User> getAllUsers() {
        final String sqlCommandGetUsers = "select * from users";
        List<User> userArrayList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlCommandGetUsers);
            while (resultSet.next()) {
                User users = new User();
                users.setId(resultSet.getLong("id"));
                users.setName(resultSet.getString("name"));
                users.setLastName(resultSet.getString("lastName"));
                users.setAge(resultSet.getByte("age"));
                userArrayList.add(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }

    // очистка содержания таблицы
    public void cleanUsersTable() {
        final String sqlCommandClean = "DELETE from Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommandClean)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("удалить не удалось");
        }
    }
}
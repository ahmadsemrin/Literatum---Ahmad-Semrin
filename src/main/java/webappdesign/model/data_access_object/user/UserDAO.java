package webappdesign.model.data_access_object.user;

import webappdesign.model.User;
import webappdesign.util.ErrorHandlerUtil;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class UserDAO implements IUserDAO {
    private List<User> users;

    private Connection connection;
    private Statement statement;

    private static IUserDAO _instance;
    private static final Object MUTEX = new Object();

    private UserDAO() {
        prepareUsers();

        establishConnectionWithDB();

        createDB();

        createTable();

        getAllUsersFromDB();
    }

    private void prepareUsers() {
        users = new ArrayList<>();
    }

    private void establishConnectionWithDB() {
        try {
            prepareMySQLDBDriver();

            createConnection();

            createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void prepareMySQLDBDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    private void createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306",
                "root","123");
    }

    private void createStatement() throws SQLException {
        statement = connection.createStatement();
    }

    private void createDB() {
        String sqlQuery = "CREATE DATABASE IF NOT EXISTS Literatum";
        try {
            executeSQLQuery(sqlQuery);

            System.out.println("Database \"Literatum\" has created successfully.");
        } catch (SQLException ex) {
            System.out.println("Database \"Literatum\" is already created.");
        }
    }

    private void executeSQLQuery(String sqlQuery) throws SQLException {
        synchronized (MUTEX) {
            statement.executeUpdate(sqlQuery);
        }
    }

    private void createTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS Literatum.User (" +
                "email VARCHAR(50) PRIMARY KEY," +
                "password VARCHAR(50) NOT NULL," +
                "role CHAR(5) NOT NULL)";
        try {
            executeSQLQuery(sqlQuery);

            System.out.println("Table \"User\" has created successfully.");
        } catch (SQLException ex) {
            System.out.println("Table \"User\" is already created.");
        }
    }

    private void getAllUsersFromDB() {
        String sqlQuery = "SELECT * FROM Literatum.User";

        ResultSet resultSet = null;
        try {
            resultSet = getResultSetFormSQLQuery(sqlQuery);

            addUsersFromResultSet(resultSet);
        } catch (SQLException ex) {
            System.out.println("Can't get the users.");
        } finally {
            closeResultSet(resultSet);
        }
    }

    private ResultSet getResultSetFormSQLQuery(String sqlQuery) throws SQLException {
        synchronized (MUTEX) {
            return statement.executeQuery(sqlQuery);
        }
    }

    private void addUsersFromResultSet(ResultSet resultSet) throws SQLException {
        while (isThereMoreColumns(resultSet)) {
            User user = getUserFromResultSet(resultSet);

            addUserToUsersList(user);
        }
    }

    private boolean isThereMoreColumns(ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        String email = getEmailFromColumn(resultSet);
        String password = getPasswordFromColumn(resultSet);
        String role = getRoleFromColumn(resultSet);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        return user;
    }

    private String getEmailFromColumn(ResultSet resultSet) throws SQLException {
        return resultSet.getString("email");
    }

    private String getPasswordFromColumn(ResultSet resultSet) throws SQLException {
        return resultSet.getString("password");
    }

    private String getRoleFromColumn(ResultSet resultSet) throws SQLException {
        return resultSet.getString("role");
    }

    private void addUserToUsersList(User user) {
        users.add(user);
    }

    private void closeResultSet(ResultSet resultSet) {
        try {
            if (isResultSetNotNull(resultSet)) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isResultSetNotNull(ResultSet resultSet) {
        return resultSet != null;
    }

    public static IUserDAO getInstance() {
        createInstance();

        return _instance;
    }

    private static void createInstance() {
        if (isInstanceNull()) {
            synchronized (MUTEX) {
                if (isInstanceNull()) {
                    _instance = new UserDAO();
                }
            }
        }
    }

    private static boolean isInstanceNull() {
        return _instance == null;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findByEmail(String email) {
        return searchForUserFromEmail(email);
    }

    private User searchForUserFromEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void insertUser(User user) {
        String sqlQuery = "INSERT INTO Literatum.User VALUES (?, ?, ?)";

        prepareStatementForSQLQuery(sqlQuery, user);

        addUserToUsersList(user);
    }

    private void prepareStatementForSQLQuery(String sqlQuery, User user) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());

            synchronized (MUTEX) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            displayFailMessageToUser();
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    private void displayFailMessageToUser() {
        ErrorHandlerUtil.setFoundEmailError();
    }

    private void closePreparedStatement(PreparedStatement preparedStatement) {
        try {
            if (isPreparedStatementNotNull(preparedStatement)) {
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isPreparedStatementNotNull(PreparedStatement preparedStatement) {
        return preparedStatement != null;
    }
}

package webappdesign.action;

import webappdesign.model.User;

import java.sql.*;

public class LoginAction {
    public void login(User user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Literatum","root","123");

            String query = "INSERT INTO User(username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.execute();

            connection.close();
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package webappdesign.action;

import webappdesign.model.User;

import java.sql.*;

public class LoginAction {
    public void login(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Literatum","root","123");

            //String query = "INSERT INTO User(email, password) VALUES (?, ?)";
            String query = "SELECT (email, password) FROM User WHERE email = " + user.getEmail();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            User user2 = null;
            while (resultSet.next()) {
                user2 = new User();

                user2.setEmail(resultSet.getString("email"));
                user2.setPassword(resultSet.getString("password"));
            }

            if (user.getEmail().equals(user2.getEmail())) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }

            connection.close();
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

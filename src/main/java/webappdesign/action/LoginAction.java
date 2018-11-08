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
            String query = "SELECT * FROM User WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user2 = new User();

                user2.setEmail(resultSet.getString("email"));
                user2.setPassword(resultSet.getString("password"));


                if (user.getEmail().equals(user2.getEmail())) {
                    System.out.println("yes");
                    System.out.println(user.getEmail());
                    System.out.println(user2.getEmail());
                } else {
                    System.out.println("no");
                }
            } else {
                System.out.println("This user is not existed! Try to register to Atypon.");
            }

            connection.close();
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

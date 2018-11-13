package webappdesign.action;

import webappdesign.model.User;
import webappdesign.model.data_access_object.IUserDAO;
import webappdesign.model.data_access_object.UserDAO;

import java.sql.*;

public class LoginAction {
    private static IUserDAO userDAO;

    public User login(User user) {
        userDAO = UserDAO.getInstance();
        User foundUser = userDAO.findByEmail(user.getEmail());
        if (areUsersEqual(foundUser, user)) {
            return foundUser;
        }

        return null;
    }

    private boolean areUsersEqual(User user1, User user2) {
        return user1.getEmail().equals(user2.getEmail()) && user1.getPassword().equals(user2.getPassword());
    }
}

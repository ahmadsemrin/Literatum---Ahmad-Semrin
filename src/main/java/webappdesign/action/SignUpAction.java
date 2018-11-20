package webappdesign.action;

import webappdesign.model.User;
import webappdesign.model.data_access_object.user.*;

public class SignUpAction {
    private static IUserDAO userDAO;

    public void signUp(User user) {
        userDAO = UserDAO.getInstance();

        userDAO.insertUser(user);
    }
}

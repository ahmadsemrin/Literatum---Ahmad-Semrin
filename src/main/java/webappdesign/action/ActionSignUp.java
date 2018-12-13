package webappdesign.action;

import webappdesign.model.User;
import webappdesign.model.data_access_object.user.*;

public class ActionSignUp implements IAction {
    @Override
    public Object doAction(Object object) {
        IUserDAO userDAO = UserDAO.getInstance();

        User user = (User) object;
        userDAO.insertUser(user);

        return null;
    }
}

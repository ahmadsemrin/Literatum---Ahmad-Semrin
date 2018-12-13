package webappdesign.action;

import webappdesign.model.User;
import webappdesign.model.data_access_object.user.*;

public class ActionLogin implements IAction {
    @Override
    public Object doAction(Object object) {
        IUserDAO userDAO = UserDAO.getInstance();

        User user = (User) object;

        User foundUser = userDAO.findByEmail(user.getEmail());
        if (foundUser != null) {
            if (areUsersEqual(foundUser, user)) {
                return foundUser;
            }
        }

        return null;
    }

    private boolean areUsersEqual(User user1, User user2) {
        return user1.getEmail().equals(user2.getEmail()) && user1.getPassword().equals(user2.getPassword());
    }
}

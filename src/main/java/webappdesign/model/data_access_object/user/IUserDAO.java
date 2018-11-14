package webappdesign.model.data_access_object.user;

import webappdesign.model.User;

import java.util.List;

public interface IUserDAO {
    List<User> findAll();
    User findByEmail(String email);
    void insertUser(User user);
}

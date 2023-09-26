package gs.service.user;

import java.util.List;

import gs.model.user.User;

public interface UserService {
    List<Object[]> getUser();

    void addUser(List<User> student) throws Exception;
}

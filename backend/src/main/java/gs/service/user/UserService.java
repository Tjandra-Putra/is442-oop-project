package gs.service.user;

import java.util.List;

import gs.common.InputModel;
import gs.common.RequestModel;
import gs.model.user.User;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    List<InputModel> getUser();

    List<InputModel> getUserById(String id);

    void addUser(List<User> user) throws Exception;

    void addUser2(HttpServletResponse response, RequestModel requestModel) throws Exception;
}

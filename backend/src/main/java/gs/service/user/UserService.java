package gs.service.user;

import java.util.List;

import gs.common.ApiModel;
import gs.common.RequestModel;
import gs.inputModel.UserInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    List<UserInputModel> getUser();

    List<UserInputModel> getUserById(String id);

    ApiModel addUser(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel) throws Exception;
}

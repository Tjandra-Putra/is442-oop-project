package gs.service.user;

import java.util.List;

import gs.common.ApiModel;
import gs.common.RequestModel;
import gs.entity.user.User;
import gs.inputModel.userInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    List<userInputModel> getUser();

    List<userInputModel> getUserById(String id);

    ApiModel addUser(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel) throws Exception;
}

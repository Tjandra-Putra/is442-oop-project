package gs.service.user.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import gs.common.ApiModel;
import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.inputModel.userInputModel;
import gs.model.user.User;
import gs.repository.UserRepo;
import gs.service.user.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService{
    
    @Resource
    protected UserRepo userRepo;

    public List<userInputModel> getUser(){
        List<Object[]> userQueryList = userRepo.getUser();
        List<userInputModel> userList = new ArrayList<>();
        
        for (Object[] data : userQueryList){
            userInputModel inputModel = new userInputModel();

            inputModel.setId((Integer) (data[0]));
            inputModel.setEmail(String.valueOf(data[1]));
            inputModel.setUsername(String.valueOf(data[3]));
            
            userList.add(inputModel);
        }
        return userList;
    }

    public List<userInputModel> getUserById(String id){
        List<Object[]> userQueryList = userRepo.getUserById(id);
        List<userInputModel> userList = new ArrayList<>();
        
        for (Object[] data : userQueryList){
            userInputModel inputModel = new userInputModel();

            inputModel.setId((Integer) (data[0]));
            inputModel.setEmail(String.valueOf(data[1]));
            inputModel.setUsername(String.valueOf(data[3]));
            
            userList.add(inputModel);
        }
        return userList;
    }
    
    public ApiModel addUser(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel) throws DataAccessException{
        // PAYLOAD
        // {
        //     "data": [
        //         {
        //             "fieldName": "email",
        //             "value": "ryan.water@gmail.com"
        //         },
        //         {
        //             "fieldName": "username",
        //             "value": "ryan.water"
        //         }
        //     ]
        // }
        // if (requestModel.getData().size() != 3) {
        //     apiModel.setMessage("Invalid number of fieldNames");
        // }  

        // else {
            for (DataRequestModel fe : requestModel.getData()){    
                User newUser = new User();

                if (fe.getFieldName().equalsIgnoreCase("email")){
                    newUser.setEmail(fe.getValue());
                }

                if (fe.getFieldName().equalsIgnoreCase("username")){
                    newUser.setUsername(fe.getValue());
                }

                if (fe.getFieldName().equalsIgnoreCase("password")){
                    newUser.setUsername(fe.getValue());
                }

                try {
                    userRepo.save(newUser);
                    // get ID

                    userInputModel inputModel = new userInputModel();

                    inputModel.setId(newUser.getUserId());
                    inputModel.setEmail(newUser.getEmail());
                    inputModel.setUsername(newUser.getUsername());

                    apiModel.setMessage(String.valueOf(newUser.getUserId()));
                    apiModel.setData(inputModel);
                }
                
                catch (DataAccessException ex) {
                // Log the exception for debugging
                // Optionally, rethrow as a custom exception
                    apiModel.setMessage(ex.getMessage());
                }
                
            }
        // }

        System.out.println("==========STATUS==================");
        System.out.println(response.getStatus());
        apiModel.setStatus(String.valueOf(response.getStatus()));

        System.out.println("==========ERROR MESSAGE==================");
        // System.out.println(response.get);

        return apiModel;
    }

}
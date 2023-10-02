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

    public void addUser(List<User> user) throws Exception{
        // PAYLOAD
        // [
        //     {
        //         "email": "ryan.water@gmail.com",
        //         "username": "Bob",
        //         "password": "1234568"
        //     },
        //     {
        //         "email": "yanyan.water@gmail.com",
        //         "username": "LOL",
        //         "password": "asdfghjkl"
        //     }
        // ]
        List<User> userList = user;

        for (User data : userList){
            userRepo.save(data);
        }
    }
    
    public ApiModel addUser2(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel) throws DataAccessException{
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

        for (DataRequestModel fe : requestModel.getData()){
            User newUser = new User();

            if (fe.getFieldName().equalsIgnoreCase("email")){
                System.out.println(fe.getValue());
                newUser.setEmail(fe.getValue());
            }

            if (fe.getFieldName().equalsIgnoreCase("username")){
                System.out.println(fe.getValue());
                newUser.setUsername(fe.getValue());
            }

            try {
                userRepo.save(newUser);
                // get ID
                newUser.getUserId();
                apiModel.setMessage(String.valueOf(newUser.getUserId()));
                throw new DataAccessException("Help"){};

            }
            
            catch (DataAccessException ex) {
            // Log the exception for debugging
            // Optionally, rethrow as a custom exception
                apiModel.setMessage(ex.getMessage());
            }
             
        }
        System.out.println("==========STATUS==================");
        System.out.println(apiModel.setStatus(String.valueOf(response.getStatus())));

        System.out.println("==========ERROR MESSAGE==================");
        // System.out.println(response.get);

        return apiModel;
    }
}
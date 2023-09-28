package gs.service.user.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import gs.common.FilterRequestModel;
import gs.common.InputModel;
import gs.common.RequestModel;
import gs.model.user.User;
import gs.repository.UserRepo;
import gs.service.user.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService{
    
    @Resource
    protected UserRepo userRepo;

    public List<InputModel> getUser(){
        List<Object[]> userQueryList = userRepo.getUser();
        List<InputModel> userList = new ArrayList<>();
        
        for (Object[] data : userQueryList){
            InputModel inputModel = new InputModel();
            Map<String, String> rowList = new HashMap<>();

            rowList.put("id", String.valueOf(data[0]));
            rowList.put("email", String.valueOf(data[1]));
            rowList.put("password", String.valueOf(data[2]));
            rowList.put("username", String.valueOf(data[3]));

            inputModel.setData(rowList);
            userList.add(inputModel);
        }
        return userList;
    }

    public List<InputModel> getUserById(String id){
        List<Object[]> userQueryList = userRepo.getUserById(id);
        List<InputModel> userList = new ArrayList<>();
        
        for (Object[] data : userQueryList){
            InputModel inputModel = new InputModel();
            Map<String, String> rowList = new HashMap<>();

            rowList.put("id", String.valueOf(data[0]));
            rowList.put("email", String.valueOf(data[1]));
            rowList.put("password", String.valueOf(data[2]));
            rowList.put("username", String.valueOf(data[3]));

            inputModel.setData(rowList);
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
    
    public void addUser2(HttpServletResponse response, RequestModel requestModel) throws Exception{
        // PAYLOAD
        // {
        //     "filters": [
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
        for (FilterRequestModel fe : requestModel.getFilters()){
            System.out.println("=========HERHE=========");
            if (fe.getFieldName().equalsIgnoreCase("email")){
                System.out.println(fe.getValue());
            }

            if (fe.getFieldName().equalsIgnoreCase("username")){
                System.out.println(fe.getValue());
            }

        }
    }
}

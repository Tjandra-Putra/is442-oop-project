package gs.service.user.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import gs.model.user.User;
import gs.repository.UserRepo;
import gs.service.user.UserService;
import jakarta.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{
    
    @Resource
    protected UserRepo userRepo;

    public List<Object[]> getUser(){
        List<Object[]> UserList = userRepo.getStudent();

        for (Object[] data : UserList){
            System.out.println(data);
        }
        return UserList;
    }

    public void addUser(List<User> student) throws Exception{
        List<User> studentList = student;

        for (User data : studentList){
            userRepo.save(data);
        }
    }
}
